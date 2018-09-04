package com.softgrid.flawAssessmentLite.config;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import java.util.Properties;

/**
 * @author Vincent Geng
 *
 * Created on 7 May 2018
 */
public class AppConfig {

	private static Logger logger = Logger.getLogger(AppConfig.class);
	private static AppConfig appConfig = null;
	private Properties props = null;

	private AppConfig() {
		try {
			props = new Properties();
			props.load(this.getClass().getResourceAsStream("/application.properties"));
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
	}

	public static AppConfig getInstance() {
		if (appConfig == null) {
			appConfig = new AppConfig();
		}

		return appConfig;
	}

	public String getDBDriver() {
		return props.getProperty("database.driver");
	}

	public String getConnectionURL() {
		return props.getProperty("database.url");
	}
}
