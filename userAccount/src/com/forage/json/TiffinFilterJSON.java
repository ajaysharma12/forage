package com.forage.json;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.forage.bean.TiffinFilterBean;

public class TiffinFilterJSON {
	/**
	 * Method to construct JSON string for ReviewBean
	 * 
	 * @param menu
	 * @return
	 * @throws IOException 
	 */
	public static String construct(TiffinFilterBean tiffin) {
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tiffin);
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
	public static String constructMap(Map<String, TiffinFilterBean> tiffinMap) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tiffinMap);
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
		public static String constructList(List<TiffinFilterBean> tiffinList) {
			ObjectMapper mapper = new ObjectMapper();
			String json = null;
			try {
				json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tiffinList);
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
		public static String constructStatus(String action, String status, TiffinFilterBean tiffin) {
			
			ObjectMapper mapper = new ObjectMapper();
			String json = null;
			
			Map jsonMap = new LinkedHashMap();
			jsonMap.put("action", action);
			jsonMap.put("status", status);
			jsonMap.put("message", tiffin);
			
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
	    public static String constructMapStatus(String action, String status, Map<String, TiffinFilterBean> tiffinMap) {
	    	ObjectMapper mapper = new ObjectMapper();
			String json = null;
			
			Map jsonMap = new LinkedHashMap();
			jsonMap.put("action", action);
			jsonMap.put("status", status);
			jsonMap.put("message", tiffinMap);
			
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
	    public static String constructListStatus(String action, String status, List<TiffinFilterBean> tiffinList) {
	        ObjectMapper mapper = new ObjectMapper();
	        String json = null;
	        
	        Map jsonMap = new LinkedHashMap();
	        jsonMap.put("action", action);
	        jsonMap.put("status", status);
	        jsonMap.put("message", tiffinList);
	        
	        try {        	
	        	json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonMap);   	
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	        	e.printStackTrace();
	        }
	        return json;
	    }	
}
