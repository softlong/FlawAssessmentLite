package com.softgrid.flawAssessmentLite.handler;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.softgrid.flawAssessmentLite.config.AppConfig;
import com.softgrid.flawAssessmentLite.constant.CreateTableStatement;

/**
 * @author Vincent Geng
 *
 * Created on 7 May 2018
 */
public class DatabaseHandler {
	
	private static Logger logger = Logger.getLogger(DatabaseHandler.class);
	private static DatabaseHandler handler = null;
    
    private static Connection conn = null;
    private static Statement stmt = null;
    
    private DatabaseHandler(){
        createConnection();
        createTable(CreateTableStatement.GB30582B);
        createTable(CreateTableStatement.GB30582C);
    }
    
    public static DatabaseHandler getInstance(){
        if(handler == null){
            handler = new DatabaseHandler();
        }
        return handler;
    }
    
    void createConnection(){
        try {
            Class.forName(AppConfig.getDBDriver()).newInstance();
            conn = DriverManager.getConnection(AppConfig.getConnectionURL());
            logger.info("Database connection created.");
        } catch (Exception e) {
        	logger.error(ExceptionUtils.getStackTrace(e));
        }
    }
    
    void createTable(CreateTableStatement createTableStatement){
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet table = dbm.getTables(null, null, createTableStatement.getTableName().toUpperCase(), null);
            
            if(table.next()){
            	logger.info("Table " + createTableStatement.getTableName() + " already exist. Good to Go.");
            }else{
                //create table
                stmt.execute(createTableStatement.getStatement());
            }
                
        } catch (Exception e) {
        	logger.error(ExceptionUtils.getStackTrace(e));
        }
    }
    
    public static ResultSet fetchDataByExecQuery(String query){
        ResultSet result;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException e) {
        	logger.error("Fetch data Error.\nException at Execute query: ");
        	logger.error(ExceptionUtils.getStackTrace(e));
            return null;
        }finally{
        }
        return result;
    }
    
    public static boolean execAction(String query){
        try {
            stmt = conn.createStatement();
            stmt.execute(query);
            return true;
        } catch (SQLException e) {
        	logger.error(ExceptionUtils.getStackTrace(e));
        	return false;
        }finally{
        }
    }
    
    public void closeConnection(){
        try {
            conn.close();
            logger.info("Database connection closed.");
        } catch (Exception e) {
        	logger.error("Unable to Close DB Connection.");
        	logger.error(ExceptionUtils.getStackTrace(e));
        }
    }
    
}
