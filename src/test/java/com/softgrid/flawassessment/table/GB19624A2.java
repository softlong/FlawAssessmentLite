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
public class GB19624A2 {
	
	private static Logger logger = Logger.getRootLogger();
	private DatabaseHandler databaseHandler;

	@Before
	public void setUp() {
		databaseHandler =  DatabaseHandler.getInstance();
	}
	
	//It's not a proper test, just print out all records in GB19624A2 table.
	@Test
	public void test() {
		
		String GB19624A2 = "SELECT * FROM GB19624A2";
	    ResultSet GB19624A2results = DatabaseHandler.fetchDataByExecQuery(GB19624A2);
	    try {
		    while(GB19624A2results.next()){
	            Integer id = GB19624A2results.getInt("id");
	            String tfpipename = GB19624A2results.getString("tfpipename");
	            String tflocation = GB19624A2results.getString("tflocation");
	            String tfB = GB19624A2results.getString("tfB");
	            String tfRi = GB19624A2results.getString("tfRi");
	            String tfRin = GB19624A2results.getString("tfRin");
	            String tfLw = GB19624A2results.getString("tfLw");
	            String comboBox1 = GB19624A2results.getString("comboBox1");
	            String tfsigmas = GB19624A2results.getString("tfsigmas");
	            String tfsigmab = GB19624A2results.getString("tfsigmab");
	            String tfdeltac = GB19624A2results.getString("tfdeltac");
	            String tfE1 = GB19624A2results.getString("tfE1");
	            String tfnu = GB19624A2results.getString("tfnu");
	            String tfJic = GB19624A2results.getString("tfJic");
	            String group1 = GB19624A2results.getString("group1");
	            String group2 = GB19624A2results.getString("group2");
	            String tfa = GB19624A2results.getString("tfa");
	            String tfc = GB19624A2results.getString("tfc");
	            String tfS = GB19624A2results.getString("tfS");
	            String tfS0 = GB19624A2results.getString("tfS0");
	            String tfBmin = GB19624A2results.getString("tfBmin");
	            String tfe2 = GB19624A2results.getString("tfe2");
	            Boolean stress1 = GB19624A2results.getBoolean("stress1");
	            String tfP1 = GB19624A2results.getString("tfP1");
	            String tfP2 = GB19624A2results.getString("tfP2");
	            Boolean stress2 = GB19624A2results.getBoolean("stress2");
	            String comboBox2 = GB19624A2results.getString("comboBox2");
	            String tfQ1 = GB19624A2results.getString("tfQ1");
	            String tfQ2 = GB19624A2results.getString("tfQ2");
	            String tfn = GB19624A2results.getString("tfn");
	            String comboBox3 = GB19624A2results.getString("comboBox3");
	            String comboBox4 = GB19624A2results.getString("comboBox4");
	            String comboBox5 = GB19624A2results.getString("comboBox5");
	            String comboBox6 = GB19624A2results.getString("comboBox6");
	            String comboBox7 = GB19624A2results.getString("comboBox7");
	            String comboBox8 = GB19624A2results.getString("comboBox8");
	            String defectType = GB19624A2results.getString("defectType");
	            String createdDate = GB19624A2results.getString("createdDate");
	            String result = GB19624A2results.getString("result");
	            logger.info("\nTable GB30582A1:\n"+
	            		"id: "+id +"\n"+
	            		"tfpipename: "+tfpipename+"\n"+
	            		"tflocation: "+tflocation+"\n"+
						"tfB: "+tfB+"\n"+
						"tfRi: "+tfRi+"\n"+
						"tfRin: "+tfRin+"\n"+
						"tfLw: "+tfLw+"\n"+
						"comboBox1: "+comboBox1+"\n"+
						"tfsigmas: "+tfsigmas+"\n"+
						"tfsigmab: "+tfsigmab+"\n"+
						"tfdeltac: "+tfdeltac+"\n"+
						"tfE: "+tfE1+"\n"+
						"tfnu: "+tfnu+"\n"+
						"tfJic: "+tfJic+"\n"+
						"group1: "+group1+"\n"+
						"group2: "+group2+"\n"+
						"tfa: "+tfa+"\n"+
						"tfc: "+tfc+"\n"+
						"tfS: "+tfS+"\n"+
						"tfS0: "+tfS0+"\n"+
						"tfBmin: "+tfBmin+"\n"+
						"tfe: "+tfe2+"\n"+
						"stress1: "+stress1+"\n"+
						"tfP1: "+tfP1+"\n"+
						"tfP2: "+tfP2+"\n"+
						"stress2: "+stress2+"\n"+
						"comboBox2: "+comboBox2+"\n"+
						"tfQ1: "+tfQ1+"\n"+
						"tfQ2: "+tfQ2+"\n"+
						"tfn: "+tfn+"\n"+
						"comboBox3: "+comboBox3+"\n"+
						"comboBox4: "+comboBox4+"\n"+
						"comboBox5: "+comboBox5+"\n"+
						"comboBox6: "+comboBox6+"\n"+
						"comboBox7: "+comboBox7+"\n"+
						"comboBox8: "+comboBox8+"\n"+
						"defectType: "+defectType+"\n"+
	            		"createdDate: "+createdDate +"\n"+
	            		"result: "+result +"\n");
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
