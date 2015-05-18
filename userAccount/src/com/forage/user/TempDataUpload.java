package com.forage.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.forage.action.LookupAction;
import com.forage.bean.AddressBean;
import com.forage.bean.LookupValueBean;
import com.forage.bean.VendorBean;
import com.forage.dao.AddressDAO;
import com.forage.dao.VendorDAO;


public class TempDataUpload {

	private TempBean callJSONWebService(int i){
		TempBean tempVend = null;
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			String URL = "http://staging.unifysystems.in/tiffin/index.php?act=T15&userid=168&providerid=" + i;
			System.out.println("URL accessed: " + URL);
			HttpGet getRequest = new HttpGet(URL);
			getRequest.addHeader("accept", "application/json");

			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			String jsonString = org.apache.commons.io.IOUtils.toString(br);
			br.close();
			System.out.println("JSON message: " + jsonString );

//			String jsonString = "{\"success\":\"true\",\"data\":{\"id\":\"368\",\"name\":\"Zayka tiffin services\",\"email_id\":\"\",\"contact_no_1\":\"+919711759753\",\"contact_no_2\":\"+918800563839\",\"contact_no_3\":\"\",\"address\":\"282, gali fatak karol, near chawri bazar metro station, delhi\",\"contact_name\":\"\",\"locality\":\"Chawari Bazar\",\"zone\":\"Central Delhi\",\"city\":\"Delhi\",\"longitude\":\"77.2263241\",\"latitude\":\"28.6494074\",\"delivery_radius\":\"Katwarai Sarai\",\"menu\":\"Thali\",\"menu_type\":\"Veg\",\"cuisine\":\"North Indian\",\"cost\":\"80\",\"dinner_cost\":\"-\",\"breakfast_cost\":\"-\",\"lunch_cost\":\"80\",\"monthly_cost\":\"2400\",\"monthly_cost_breakfast\":\"-\",\"monthly_cost_lunch\":\"2400\",\"monthly_cost_dinner\":\"-\",\"meals_type\":\"Lunch\",\"timining\":\"12:00:PM\",\"timining_to\":\"9:00:PM\",\"remarks\":\" \",\"verified\":\"1\",\"favorite\":\"0\"}}";
			
			JSONObject obj = new JSONObject(jsonString);
			System.out.println("success : " + obj.getString("success"));
//			System.out.println("data : " + obj.getString("data"));
			
			if("true".equals(obj.getString("success"))){
				JSONObject jObject = new JSONObject(obj.getString("data"));
				
			    ObjectMapper mapper = new ObjectMapper();
			    tempVend =  mapper.readValue(jObject.toString(), TempBean.class);
		        System.out.print(tempVend.getName() + "   " + tempVend.getContact_name());	
			}

			httpClient.getConnectionManager().shutdown();

		} catch (JSONException e) {

			e.printStackTrace();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
		return tempVend;
	}
	
	private void loadData(TempBean tempVend){
		AddressBean addr = createAddress(tempVend);
		VendorBean vendor = initiateVendor(tempVend);
		vendor.setAddress1(addr.getAddressId());
		vendor.setAddrBean1(addr);
		
		VendorDAO vendorDAO = new VendorDAO();
		vendorDAO.insert(vendor);
		
	}
	
	private VendorBean initiateVendor(TempBean tempVend){
		VendorBean vendor = new VendorBean();
		
		
		vendor.setName(tempVend.getName());
		vendor.setEmail(tempVend.getEmail_id());
		vendor.setContactPerson(tempVend.getContact_name());
		vendor.setPhoneNumber(tempVend.getContact_no_1().replaceAll("[\\s+\\-()]", ""));
		vendor.setPhoneNumber2(tempVend.getContact_no_2().replaceAll("[\\s+\\-()]", ""));
		vendor.setPhoneNumber3(tempVend.getContact_no_3().replaceAll("[\\s+\\-()]", ""));
		vendor.setLastGPSLatitude(tempVend.getLatitude());
		vendor.setLastGPSLongitude(tempVend.getLongitude());
		
		LookupAction lookupAction = new LookupAction();
		
		String menuCode = null;
		if(tempVend.getMenu_type() != null){  // check to see the if menu type is registered in the Lookup table	
			menuCode = lookupAction.getLookupCode("FOOD_TYPE", null, tempVend.getMenu_type());
			if(menuCode == null){ // insert new FOOD_TYPE lookup
				lookupAction.createLookupCode("FOOD_TYPE", tempVend.getMenu_type(), BigDecimal.ONE);
				menuCode = lookupAction.getLookupCode("FOOD_TYPE", null, tempVend.getMenu_type());
			}
			vendor.setMenuType(menuCode);
		}
		
		String cuisineCode = null;
		if(tempVend.getCuisine() != null){  // check to see the if cuisine type is registered in the Lookup table	
			cuisineCode = lookupAction.getLookupCode("CUISINE_TYPE", null, tempVend.getCuisine());
			if(cuisineCode == null){ // insert new CUISINE_TYPE lookup code
				lookupAction.createLookupCode("CUISINE_TYPE", tempVend.getCuisine(), BigDecimal.ONE);
				cuisineCode = lookupAction.getLookupCode("CUISINE_TYPE", null, tempVend.getCuisine());
			}
			vendor.setCuisine(cuisineCode);
		}
		
		if(tempVend.getCost() != null)
			{
//			System.out.println(tempVend.getCost().split("[0-9]+")[0]);
			vendor.setMinPriceMeal(new BigDecimal(tempVend.getCost().split("-")[0]));}
		else
			vendor.setMinPriceMeal(BigDecimal.ZERO);
		
		if(tempVend.getBreakfast_cost() != null && !"-".equals(tempVend.getBreakfast_cost())){
			vendor.setBreakfastTime("7:00 AM - 9:00 AM");
		}
		if (tempVend.getLunch_cost() != null && !"-".equals(tempVend.getLunch_cost())) {
			vendor.setLunchTime("12:00 Noon - 3:00 PM");
		}
		if (tempVend.getDinner_cost() != null && !"-".equals(tempVend.getDinner_cost())) {
			vendor.setDinnerTime("6:00 PM - 9:00 PM");
		}
		vendor.setApproveFlag("Y");
		vendor.setActiveFlag("Y");
		
		return vendor;
	}
	
	private AddressBean createAddress(TempBean tempVend){
		AddressBean addr = new AddressBean();
		String[] tokens = tempVend.getAddress().split(",");
		for (int i = 0; i < tokens.length; i++) {
			switch(i){
				case 0 :  addr.setLine1(tokens[i]); break;
				case 1 :  addr.setLine2(tokens[i]); break;
				case 2 :  addr.setLine3(tokens[i]); break;
				case 3 :  addr.setLine4(tokens[i]); break;
				case 4 :  addr.setLine5(tokens[i]); break;
				case 5 :  addr.setLine6(tokens[i]); break;
				case 6 :  addr.setLine7(tokens[i]); break;
				case 7 :  addr.setLine8(tokens[i]); break;
				case 8 :  addr.setLine9(tokens[i]); break;
				default : break;
			}
		}
		
		LookupAction lookupAction = new LookupAction();
		LookupValueBean lookupValue = null;
		String cityCode = null;
		if(tempVend.getCity() != null){  // check to see the if city is registered in the Lookup table	
			cityCode = lookupAction.getLookupCode("CITY", null, tempVend.getCity());
			if(cityCode == null){ // insert new CITY lookup
				lookupAction.createLookupCode("CITY", tempVend.getCity(), BigDecimal.ONE);
				cityCode = lookupAction.getLookupCode("CITY", null, tempVend.getCity());
			}
			addr.setCity(cityCode);
		}
		
		String localityCode = null;		
		if(cityCode != null && tempVend.getLocality() != null){
			localityCode = lookupAction.getLookupCode("LOCALITY", cityCode, tempVend.getLocality());
			if(localityCode == null){ // insert new locality
				String lookupValueString = lookupAction.createLookupCode("LOCALITY", tempVend.getLocality(), BigDecimal.ONE);
				
				
				ObjectMapper mapper = new ObjectMapper();
				JSONObject jObject;
				try {
					jObject = new JSONObject(lookupValueString);
					lookupValue =  mapper.readValue(jObject.toString(), LookupValueBean.class);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				lookupAction.tagLookupCode("LOCALITY", lookupValue.getLookupCode(), cityCode);				
				localityCode = lookupAction.getLookupCode("LOCALITY", cityCode, tempVend.getLocality());
				
				if(tempVend.getZone() != null && !tempVend.getZone().equals(tempVend.getLocality()) && lookupValue.getAttribute1() == null){
					lookupAction.updateLocalityZone("LOCALITY", localityCode, tempVend.getZone());
				}
			}
			
			addr.setLocality(localityCode);
		}
		
		
		addr.setLongitude(tempVend.getLongitude());
		addr.setLatitude(tempVend.getLatitude());
		
		addr.setCreatedBy(BigDecimal.ONE);
		addr.setLastUpdatedBy(BigDecimal.ONE);
		
		AddressDAO addrDAO = new AddressDAO();
		addrDAO.insertNewAddress(addr);
		System.out.println("");
		return addr;
	}
	
	
	public static void main(String[] args) {
		TempDataUpload upload = new TempDataUpload();
		for(int i = 633; i < 2122 ; i++ ){
			if (i == 368) continue;
			TempBean tempVend =upload.callJSONWebService(i);
			if(tempVend != null){
				upload.loadData(tempVend);
			}
			System.out.println("#"+i+"    done###########################******************");
		}
	}
	
}
