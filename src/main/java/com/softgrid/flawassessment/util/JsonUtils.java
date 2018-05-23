package com.softgrid.flawassessment.util;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Vincent Geng
 *
 * Created on 20 Apr 2018
 */
public class JsonUtils {
	public static JSONObject parseJsonObjectFromFile(String filePath){
		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			obj = parser.parse(new FileReader(filePath));
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
        JSONObject jsonObject = (JSONObject) obj;
		return jsonObject;
	}
}
