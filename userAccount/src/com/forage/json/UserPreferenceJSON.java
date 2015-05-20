package com.forage.json;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.forage.bean.UserPreferenceBean;

public class UserPreferenceJSON {
	/**
	 * Method to construct JSON string for CustomerBean
	 * 
	 * @param preference
	 * @return
	 * @throws IOException 
	 */
	public static String construct(UserPreferenceBean preference) {
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(preference);
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
	 * Method to construct JSON string for List of preferences
	 * 
	 * @param tag
	 * @param status
	 * @return
	 */
	public static String constructList(List<UserPreferenceBean> preferenceList) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(preferenceList);
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
	public static String constructMap(Map<String, UserPreferenceBean> preferenceMap) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(preferenceMap);
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
	 * @param action
	 * @param status
	 * @param customer
	 * @return
	 * @throws IOException 
	 */
	public static String constructStatus(String action, String status, UserPreferenceBean preference) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		
		Map jsonMap = new LinkedHashMap();
		jsonMap.put("action", action);
		jsonMap.put("status", status);
		jsonMap.put("message", preference);
		
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
    public static String constructMapStatus(String action, String status, Map<String, UserPreferenceBean> preferenceMap) {
    	ObjectMapper mapper = new ObjectMapper();
		String json = null;
		
		Map jsonMap = new LinkedHashMap();
		jsonMap.put("action", action);
		jsonMap.put("status", status);
		jsonMap.put("message", preferenceMap);
		
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
    public static String constructListStatus(String action, String status, List<UserPreferenceBean> preferenceList) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        
        Map jsonMap = new LinkedHashMap();
        jsonMap.put("action", action);
        jsonMap.put("status", status);
        jsonMap.put("message", preferenceList);
        
        try {        	
        	json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonMap);   	
        } catch (Exception e) {
            // TODO Auto-generated catch block
        	e.printStackTrace();
        }
        return json;
    }
}
