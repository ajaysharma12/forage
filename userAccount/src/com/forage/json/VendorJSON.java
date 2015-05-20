package com.forage.json;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.forage.bean.VendorBean;

public class VendorJSON {

	/**
	 * Method to construct JSON string for CustomerBean
	 * 
	 * @param vendor
	 * @return
	 * @throws IOException 
	 */
	public static String construct(VendorBean vendor) {
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(vendor);
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
	 * Method to construct JSON string for List of vendors
	 * 
	 * @param tag
	 * @param status
	 * @return
	 */
	public static String constructList(List<VendorBean> vendorList) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(vendorList);
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
	public static String constructMap(Map<String, VendorBean> vendorMap) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(vendorMap);
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
	public static String constructStatus(String action, String status, VendorBean vendor) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		
		Map jsonMap = new LinkedHashMap();
		jsonMap.put("action", action);
		jsonMap.put("status", status);
		jsonMap.put("message", vendor);
		
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
    public static String constructMapStatus(String action, String status, Map<String, VendorBean> vendorMap) {
    	ObjectMapper mapper = new ObjectMapper();
		String json = null;
		
		Map jsonMap = new LinkedHashMap();
		jsonMap.put("action", action);
		jsonMap.put("status", status);
		jsonMap.put("message", vendorMap);
		
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
    public static String constructListStatus(String action, String status, List<VendorBean> vendorList) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        
        Map jsonMap = new LinkedHashMap();
        jsonMap.put("action", action);
        jsonMap.put("status", status);
        jsonMap.put("message", vendorList);
        
        try {        	
        	json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonMap);   	
        } catch (Exception e) {
            // TODO Auto-generated catch block
        	e.printStackTrace();
        }
        return json;
    }
	
}
