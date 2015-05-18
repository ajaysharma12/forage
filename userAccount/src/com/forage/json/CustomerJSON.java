package com.forage.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONObject;

import com.forage.bean.CustomerBean;
import com.forage.dao.CustomerDAO;

public class CustomerJSON {

	/**
	 * Method to construct JSON string for CustomerBean
	 * 
	 * @param customer
	 * @param status
	 * @return
	 * @throws IOException 
	 */
	public static String construct(CustomerBean customer) {
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(customer);
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
	 * Method to construct JSON string for List of customer
	 * 
	 * @param tag
	 * @param status
	 * @return
	 */
	public static String constructList(List<CustomerBean> reviewList) {
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
	 * Method to construct JSON string for Map of PhoneNumbers and Customers
	 * 
	 * @param tag
	 * @param status
	 * @return
	 */
	public static String constructMap(Map<String, CustomerBean> customerMap) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(customerMap);
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
	public static String constructStatus(String action, String status, CustomerBean customer) {
		
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		
		Map jsonMap = new LinkedHashMap();
		jsonMap.put("action", action);
		jsonMap.put("status", status);
		jsonMap.put("customer", customer);
		
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
    public static String constructMapStatus(String action, String status, Map<String, CustomerBean> customerMap) {
    	ObjectMapper mapper = new ObjectMapper();
		String json = null;
		
		Map jsonMap = new LinkedHashMap();
		jsonMap.put("action", action);
		jsonMap.put("status", status);
		jsonMap.put("customer", customerMap);
		
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
    public static String constructListStatus(String action, String status, List<CustomerBean> customerList) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        
        Map jsonMap = new LinkedHashMap();
        jsonMap.put("action", action);
        jsonMap.put("status", status);
        jsonMap.put("customers", customerList);
        
        try {        	
        	json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonMap);   	
        } catch (Exception e) {
            // TODO Auto-generated catch block
        	e.printStackTrace();
        }
        return json;
    }
    
    
	/**
     * Method to construct Java Status, Action and customer Map from json String
     * 
     * @param tag
     * @param status
     * @return
     */    
    private static void readJSONBackToObjects(String jsonString){
    	try {
			JSONObject obj = new JSONObject(jsonString);
			System.out.println("action : " + obj.getString("action"));
			System.out.println("status : " + obj.getString("status"));
			System.out.println("customers : " + obj.getString("customers"));
			
			HashMap<String, CustomerBean> map = new HashMap<String, CustomerBean>();
			JSONObject jObject = new JSONObject(obj.getString("customers"));
			Iterator<?> keys = jObject.keys();
			
			while( keys.hasNext() ){
	            String key = (String)keys.next();
	            ObjectMapper mapper = new ObjectMapper();
	            JSONObject innerobj = jObject.getJSONObject(key);
	            System.out.println(innerobj.toString()); 	            
	            CustomerBean value =  mapper.readValue(jObject.getJSONObject(key).toString(), CustomerBean.class);
	            map.put(key, value);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    /**
     * Method to construct Java Status, Action and Map from json String
     * 
     * @param tag
     * @param status
     * @return
     */    
    private static void readJSONBackToOneObject(String jsonString){
    	try {
			JSONObject obj = new JSONObject(jsonString);
			System.out.println("action : " + obj.getString("action"));
			System.out.println("status : " + obj.getString("status"));
			System.out.println("customer : " + obj.getString("customer"));
			
			JSONObject jObject = new JSONObject(obj.getString("customer"));
			
		    ObjectMapper mapper = new ObjectMapper();
		    CustomerBean customer =  mapper.readValue(jObject.toString(), CustomerBean.class);
	        System.out.print(customer.getFirstName() + customer.getLastName());
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
	
	
	public static void main(String args[]) {
		CustomerDAO cust = new CustomerDAO();
		Map<String, CustomerBean> custMap = new HashMap<String, CustomerBean>();
		CustomerBean customer = cust.getCustomer("9915088334");

		custMap.put("9915088334", customer);
		CustomerBean customer1 = cust.getCustomer("9915088333");
		custMap.put("9915088333", customer1);
		CustomerBean customer2 = cust.getCustomer("991508833");
		custMap.put("9915088332", customer2);
		

		
		String jsonCustString = constructStatus("update", "pass", customer);
		System.out.println(jsonCustString);
		readJSONBackToOneObject(jsonCustString);
		
//		String str = constructMapStatus("update", "pass", custMap);
//		System.out.println(str);
//
//		readJSONBackToObjects(str);
		
//		String jsonMapString = constructCustomerMapJSON(custMap);
//		System.out.println(jsonMapString);
		
//		String jsonString = constructCustomerJSON(customer);
//		System.out.println(jsonString);
	
		
		// CustomerBean customer = cust.getCustomer("9915088332");
		//
		// CustomerBean customer = new CustomerBean();
		// customer.setPhoneNumber("9915088332");
		// cust.updateLoginDate(customer);
	}

}
