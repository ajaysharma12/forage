package com.forage.json;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.forage.bean.LookupTypeBean;
import com.forage.bean.LookupValueBean;

public class LookupJSON {

	/**
	 * Method to construct JSON string for CustomerBean
	 * 
	 * @param menu
	 * @return
	 * @throws IOException 
	 */
	public static String construct(LookupTypeBean lookupType) {
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(lookupType);
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
	 * Method to construct JSON string for CustomerBean
	 * 
	 * @param menu
	 * @return
	 * @throws IOException 
	 */
	public static String construct(LookupValueBean lookupValue) {
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(lookupValue);
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
	public static String constructMap(Map<String, LookupTypeBean> lookupTypeMap) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(lookupTypeMap);
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
	public static String constructStatus(String action, String status, LookupTypeBean lookupType) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		
		Map jsonMap = new LinkedHashMap();
		jsonMap.put("action", action);
		jsonMap.put("status", status);
		jsonMap.put("message", lookupType);
		
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
    public static String constructListStatus(String action, String status, List<LookupTypeBean> lookupTypeList) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        
        Map jsonMap = new LinkedHashMap();
        jsonMap.put("action", action);
        jsonMap.put("status", status);
        jsonMap.put("message", lookupTypeList);
        
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
    public static String constructMapStatus(String action, String status, Map<String, LookupTypeBean> lookupTypeMap) {
    	ObjectMapper mapper = new ObjectMapper();
		String json = null;
		
		Map jsonMap = new LinkedHashMap();
		jsonMap.put("action", action);
		jsonMap.put("status", status);
		jsonMap.put("message", lookupTypeMap);
		
        try {
        	json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonMap);        	
        } catch (Exception e) {
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
	public static String constructList(List<LookupTypeBean> lookupTypeList) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(lookupTypeList);
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
}
