package com.softgrid.flawassessment.table;

import java.sql.ResultSet;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.softgrid.flawassessment.handler.DatabaseHandler;

/**
 * @author Vincent Geng
 *
 * Created on 11 May 2018
 */
public class GB30582A1 {
	
	private static Logger logger = Logger.getRootLogger();
	private DatabaseHandler databaseHandler;

	@Before
	public void setUp() {
		databaseHandler =  DatabaseHandler.getInstance();
	}
	
	//It's not a proper test, just print out all records in GB30582A1 table.
	@Test
	public void test() {
		
	    String GB30582A1 = "SELECT * FROM GB30582A1";
	    ResultSet GB30582A1results = DatabaseHandler.fetchDataByExecQuery(GB30582A1);
	    try {
			while (GB30582A1results.next()) {
				Integer id = GB30582A1results.getInt("id");
				String tfpipename = GB30582A1results.getString("tfpipename");
				String tflocation = GB30582A1results.getString("tflocation");
				String tft = GB30582A1results.getString("tft");
				String tfD = GB30582A1results.getString("tfD");
				String tfP0 = GB30582A1results.getString("tfP0");
				String comboBox1 = GB30582A1results.getString("comboBox1");
				String tfRt05 = GB30582A1results.getString("tfRt05");
				String tfRm = GB30582A1results.getString("tfRm");
				String comboBox2 = GB30582A1results.getString("comboBox2");
				String comboBox3 = GB30582A1results.getString("comboBox3");
				String defectType = GB30582A1results.getString("defectType");
				String defectLength = GB30582A1results.getString("defectLength");
				String defectDepth = GB30582A1results.getString("defectDepth");
				String defectCombinationLength = GB30582A1results.getString("defectCombinationLength");
				String defectCombinationMaxDepth = GB30582A1results.getString("defectCombinationMaxDepth");
				String lengthCollection = GB30582A1results.getString("lengthCollection");
				String depthCollection = GB30582A1results.getString("depthCollection");
				String lbPF = GB30582A1results.getString("lbPF");
				String lbP = GB30582A1results.getString("lbP");
				String createdDate = GB30582A1results.getString("createdDate");
				String result = GB30582A1results.getString("result");
				logger.info("\nTable GB30582A1:\n" +
						"id: " + id + "\n" + 
						"tfpipename: " + tfpipename + "\n"+ 
						"tflocation: " + tflocation + "\n" + 
						"tft: " + tft + "\n" + 
						"tfD: " + tfD + "\n" + 
						"tfP0: " + tfP0 + "\n" + 
						"comboBox1: " + comboBox1 + "\n" + 
						"tfRt05: " + tfRt05 + "\n" + 
						"tfRm: " + tfRm + "\n" + 
						"comboBox2: " + comboBox2 + "\n" + 
						"comboBox3: " + comboBox3 + "\n" + 
						"defectType: " + defectType + "\n" + 
						"defectLength: " + defectLength + "\n" + 
						"defectDepth: " + defectDepth + "\n" + 
						"defectCombinationLength: " + defectCombinationLength + "\n" + 
						"defectCombinationMaxDepth: " + defectCombinationMaxDepth + "\n" + 
						"lengthCollection: " + lengthCollection + "\n" + 
						"depthCollection: " + depthCollection + "\n" + 
						"lbPF: " + lbPF + "\n" + 
						"lbP: " + lbP + "\n" + 
						"createdDate: " + createdDate + "\n" + 
						"result: " + result + "\n");
			} 
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() {
		databaseHandler.closeConnection();
	}

}
