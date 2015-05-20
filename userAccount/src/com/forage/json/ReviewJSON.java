package com.forage.json;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.forage.bean.ReviewBean;


public class ReviewJSON {
	/**
	 * Method to construct JSON string for ReviewBean
	 * 
	 * @param menu
	 * @return
	 * @throws IOException 
	 */
	public static String construct(ReviewBean review) {
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(review);
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
		return jsonString;
	}
	
	
	/**
	 * Method to construct JSON string for Map of PhoneNumbers and Customers
	 * 
	 * @param tag
	 * @param status
	 * @return
	 */
	public static String constructMap(Map<String, ReviewBean> reviewMap) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(reviewMap);
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
		public static String constructList(List<ReviewBean> reviewList) {
			ObjectMapper mapper = new ObjectMapper();
			String json = null;
			try {
				json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(reviewList);
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
		 * Method to construct JSON string for CustomerBean along with actionName and Status
		 * 
		 * @param customer
		 * @param status
		 * @return
		 * @throws IOException 
		 */
		public static String constructStatus(String action, String status, ReviewBean review) {
			
			ObjectMapper mapper = new ObjectMapper();
			String json = null;
			
			Map jsonMap = new LinkedHashMap();
			jsonMap.put("action", action);
			jsonMap.put("status", status);
			jsonMap.put("message", review);
			
			try {
				json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonMap);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return json;
		}
		
		
		/**
	     * Method to construct JSON for Map of PhoneNumbers and Customers along with actionName and Status
	     * 
	     * @param tag
	     * @param status
	     * @return
	     */
	    public static String constructMapStatus(String action, String status, Map<String, ReviewBean> reviewMap) {
	    	ObjectMapper mapper = new ObjectMapper();
			String json = null;
			
			Map jsonMap = new LinkedHashMap();
			jsonMap.put("action", action);
			jsonMap.put("status", status);
			jsonMap.put("message", reviewMap);
			
	        try {
	        	json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonMap);        	
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	        	e.printStackTrace();
	        }
	        return json;
	    }
		
		
	    
		/**
	     * Method to construct JSON for Map of PhoneNumbers and Customers along with actionName and Status
	     * 
	     * @param tag
	     * @param status
	     * @return
	     */
	    public static String constructListStatus(String action, String status, List<ReviewBean> reviewList) {
	        ObjectMapper mapper = new ObjectMapper();
	        String json = null;
	        
	        Map jsonMap = new LinkedHashMap();
	        jsonMap.put("action", action);
	        jsonMap.put("status", status);
	        jsonMap.put("message", reviewList);
	        
	        try {        	
	        	json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonMap);   	
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	        	e.printStackTrace();
	        }
	        return json;
	    }	
}
