package com.softgrid.flawAssessmentLite.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Vincent Geng
 *
 * Created on 7 May 2018
 */
public class AppConfig {
	
	private static Properties props;
	
	private static Properties loadApplicationProperties(){
		if(props != null){
			return props;
		}else{
			try {
				Properties properties = new Properties();
				properties.load(new FileInputStream("src/main/resources/application.properties"));
				props = properties;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return props;
		}
	}
	
	public static String getDBDriver(){
		Properties props = loadApplicationProperties();
		return props.getProperty("database.driver");
	}
	
	public static String getConnectionURL(){
		Properties props = loadApplicationProperties();
		return props.getProperty("database.url");
	}
}
