package com.forage.json;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.forage.bean.ImageBean;

public class ImageJSON {

	/**
	 * Method to construct JSON string for ImageBean
	 * 
	 * @param image
	 * @return
	 * @throws IOException
	 */
	public static String construct(ImageBean image) {

		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(image);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * Method to construct JSON string for Map of PhoneNumbers and Customers
	 * 
	 * @param tag
	 * @param status
	 * @return
	 */
	public static String constructMap(Map<String, ImageBean> imageMap) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
					imageMap);
			return json;
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * Method to construct JSON string for List of reviews
	 * 
	 * @param tag
	 * @param status
	 * @return
	 */
	public static String constructList(List<ImageBean> imageList) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
					imageList);
			return json;
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * Method to construct JSON string for CustomerBean along with actionName
	 * and Status
	 * 
	 * @param customer
	 * @param status
	 * @return
	 * @throws IOException
	 */
	public static String constructStatus(String action, String status,
			ImageBean image) {

		ObjectMapper mapper = new ObjectMapper();
		String json = null;

		Map jsonMap = new LinkedHashMap();
		jsonMap.put("action", action);
		jsonMap.put("status", status);
		jsonMap.put("image", image);

		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
					jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * Method to construct JSON for Map of PhoneNumbers and Customers along with
	 * actionName and Status
	 * 
	 * @param tag
	 * @param status
	 * @return
	 */
	public static String constructMapStatus(String action, String status,
			Map<String, ImageBean> imageMap) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;

		Map jsonMap = new LinkedHashMap();
		jsonMap.put("action", action);
		jsonMap.put("status", status);
		jsonMap.put("image", imageMap);

		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
					jsonMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * Method to construct JSON for Map of PhoneNumbers and Customers along with
	 * actionName and Status
	 * 
	 * @param tag
	 * @param status
	 * @return
	 */
	public static String constructListStatus(String action, String status,
			List<ImageBean> imageList) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;

		Map jsonMap = new LinkedHashMap();
		jsonMap.put("action", action);
		jsonMap.put("status", status);
		jsonMap.put("images", imageList);

		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
					jsonMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
