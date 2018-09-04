package com.softgrid.flawAssessmentLite.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Vincent Geng
 *
 * Created on 20 Apr 2018
 */
public class JsonUtils {
	private static Logger logger = Logger.getLogger(JsonUtils.class);

	public static JSONObject parseJsonObjectFromFile(String filePath) {
		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			InputStream in = JsonUtils.class.getResourceAsStream(filePath);
			obj = parser.parse(new InputStreamReader(in));
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		return (JSONObject) obj;
	}

	public static ArrayList<Double> turnJsonKeyToArrayList(JSONObject jsonObject) {
		ArrayList<Double> array = new ArrayList<>();
		Set set = jsonObject.keySet();
		for (Iterator it = set.iterator(); it.hasNext(); ) {
			array.add(Double.parseDouble((String) it.next()));
		}
		array.sort(new NumberUtils.SortBySize());
		return array;
	}

	/**
	 * Added on 2018-07-24 by Chongyu
	 *
	 * @param jsonObject
	 * @return
	 */
	public static ArrayList<String> turnJsonKeyToStringArrayList(JSONObject jsonObject) {
		ArrayList<String> al = new ArrayList<>();
		Set set = jsonObject.keySet();

		for (Object obj : set) {
			al.add((String) obj);
		}

		return al;
	}

	/**
	 * Added on 2018-07-24 by Chongyu
	 *
	 * @param jsonObject
	 * @param key1
	 * @return
	 */
	public static double getValueByKeyFrom1DJsonObject(JSONObject jsonObject, String key1) {
		try {
			ArrayList<String> al = turnJsonKeyToStringArrayList(jsonObject);
			String key = NumberUtils.findClosestValueFromStringAL(al, Double.parseDouble(key1));
			String value = (String) (jsonObject.get(key));

			return Double.parseDouble(value);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			return 0;
		}
	}

	/**
	 * Added on 2018-07-24 by Chongyu
	 *
	 * @param jsonObject
	 * @param key1
	 * @param key2
	 * @return
	 */
	public static double getValueByKeysFrom2DJsonObject(JSONObject jsonObject, String key1, String key2) {
		try {
			ArrayList<String> al_level1 = turnJsonKeyToStringArrayList(jsonObject);
			String key_level1 = NumberUtils.findClosestValueFromStringAL(al_level1, Double.parseDouble(key1));
			JSONObject jsonObject_level1 = (JSONObject) jsonObject.get(key_level1);

			ArrayList<String> al_level2 = turnJsonKeyToStringArrayList(jsonObject_level1);
			String key_level2 = NumberUtils.findClosestValueFromStringAL(al_level2, Double.parseDouble(key2));
			String value = (String) (jsonObject_level1.get(key_level2));

			return Double.parseDouble(value);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			return 0;
		}
	}
}
