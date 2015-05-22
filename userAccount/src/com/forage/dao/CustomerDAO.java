package com.forage.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.forage.bean.CustomerBean;
import com.forage.bean.FavoriteBean;
import com.forage.bean.UserPreferenceBean;
import com.forage.exception.AlreadyExistException;
import com.forage.exception.BadRequestException;
import com.forage.exception.NotFoundException;
import com.forage.user.DBConnection;
import com.forage.user.Utility;

public class CustomerDAO {

	/**
	 * Method to insert new customer to DB Method assumes that the required
	 * information has been passed. Check for the necessary/required parameters
	 * insert and assign a Id to the object and returns back.
	 *
	 * @param customer
	 *            bean
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public void insertNewCustomerNameNumber(CustomerBean customer) {
		
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;

		try {
			dbConn = DBConnection.getConnection();
			CustomerBean customerCheck = getCustomer(customer.getPhoneNumber());
			if (customerCheck== null || customerCheck.getCustomerId() == null || !customerCheck.getCreatedBy().equals(BigDecimal.ONE)) {
				String query = "insert into customers (first_name, last_name, phone_number, created_by, last_updated_by) values( ?, ?, ?, ?, ?)";
				preparedStmt = dbConn.prepareStatement(query);
				preparedStmt.setString(1, customer.getFirstName());
				preparedStmt.setString(2, customer.getLastName());
				preparedStmt.setString(3, customer.getPhoneNumber());
				preparedStmt.setBigDecimal(4, BigDecimal.ONE);
				preparedStmt.setBigDecimal(5, BigDecimal.ONE);
				preparedStmt.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}		
		customer = getCustomer(customer.getPhoneNumber());
		customer.setPreference(createUserPreference(customer));
	}
	
	private UserPreferenceBean createUserPreference(CustomerBean customer){
		UserPreferenceDAO userPreferenceDAO = new UserPreferenceDAO();
		UserPreferenceBean preference = new UserPreferenceBean();
		preference.setUserId(customer.getCustomerId());
		preference.setSearchRadius(new BigDecimal(5));
		preference.setCreatedBy(customer.getCustomerId());
		preference.setLastUpdatedBy(customer.getCustomerId());
		userPreferenceDAO.insert(preference);
		return preference;
	}

	public CustomerBean getCustomer(String phoneNumber) {
		CustomerBean customer = new CustomerBean();
		Connection dbConn  = null;
		Statement stmt  = null;
		ResultSet rs  = null;

		try {
			dbConn = DBConnection.getConnection();
			stmt = dbConn.createStatement();
			String query = "SELECT * FROM customers WHERE phone_number = '"
					+ phoneNumber + "'";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				customer.setCustomerId(rs.getBigDecimal("customer_id"));
				customer.setFirstName(rs.getString("first_name"));
				customer.setLastName(rs.getString("last_name"));
				customer.setGender(rs.getString("gender"));
				customer.setPhoneNumber(rs.getString("phone_number"));
				customer.setLastGPSLatitude(rs.getDouble("last_gps_latitude"));
				customer.setLastGPSLongitude(rs.getDouble("last_gps_longitude"));
				customer.setPassword(rs.getString("password"));
				customer.setEmail(rs.getString("email"));
				customer.setFacebookUniqueId(rs.getString("facebook_unique_id"));
				customer.setTwitterUniqueId(rs.getString("twitter_unique_id"));
				customer.setGoogleUniqueId(rs.getString("google_unique_id"));
				customer.setAddress(rs.getBigDecimal("address"));
				customer.setAddress2(rs.getBigDecimal("address2"));
				customer.setAddress3(rs.getBigDecimal("address3"));
				customer.setShippingAddress(rs
						.getBigDecimal("shipping_address"));
				
				
				customer.setActiveFlag(rs.getString("active_flag"));
				customer.setApproveFlag(rs.getString("approve_flag"));
				customer.setCreatedBy(rs.getBigDecimal("created_by"));
				customer.setCreatedDate(rs.getDate("creation_date"));
				customer.setLastUpdatedBy(rs.getBigDecimal("last_updated_by"));
				customer.setLastUpdateDate(rs.getDate("last_update_date"));
				customer.setLastLoginDate(rs.getDate("last_login_date"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
	        if (stmt  != null) try { stmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}

		// Address DAO starts
		
		AddressDAO addrDAO = new AddressDAO();
		if(customer.getAddress() != null){
			customer.setAddrBean1(addrDAO.getAddress(customer.getAddress()));
		}
		if(customer.getAddress2() != null){
			customer.setAddrBean2(addrDAO.getAddress(customer.getAddress2()));
		}
		if(customer.getAddress3() != null){
			customer.setAddrBean3(addrDAO.getAddress(customer.getAddress3()));
		}
		if(customer.getShippingAddress() != null){
			customer.setShipAddrBean(addrDAO.getAddress(customer.getShippingAddress()));
		}
		
		// Address DAO Ends
		
		// Customer Preference Starts
		UserPreferenceDAO userPreferenceDAO = new UserPreferenceDAO(); 
		UserPreferenceBean preference = userPreferenceDAO.getUserPreference(customer.getCustomerId());
		customer.setPreference(preference);
		// Customer Preference Ends
		
		// Favorite List Starts
		FavoriteDAO favoriteDAO = new FavoriteDAO();
		List<FavoriteBean> favoriteList = favoriteDAO.getFavoriteList(customer.getCustomerId());
		customer.setFavoriteVendors(favoriteList);
		// Favorite List ends
		
		return customer;
	}
	
	public CustomerBean getCustomer(BigDecimal customerId) {
		CustomerBean customer = null;
		String customerPhoneNumber = null;
		
		Connection dbConn  = null;
		Statement stmt  = null;
		ResultSet rs  = null;

		try {
			dbConn = DBConnection.getConnection();
			stmt = dbConn.createStatement();
			String query = "SELECT * FROM customers WHERE customer_id = '"
					+ customerId + "'";
			rs = stmt.executeQuery(query);
			while(rs.next()){
				customerPhoneNumber = rs.getString("phone_number");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
	        if (stmt  != null) try { stmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}
		if(customerPhoneNumber != null)
			customer = getCustomer(customerPhoneNumber);
		else
			throw new NotFoundException("ReviewAction.getCustomer", "Customer not provided.");
		
		return customer;
	}

	public void updateLoginDate(CustomerBean customer) {
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;

		try {
			dbConn = DBConnection.getConnection();

			String updateQuery = "update customers set last_login_date = ? where phone_number = ?";
			preparedStmt = dbConn
					.prepareStatement(updateQuery);

			java.util.Date currentDate = new java.util.Date();

			preparedStmt.setDate(1, new Date(currentDate.getTime()));
			preparedStmt.setString(2, customer.getPhoneNumber());
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}
	}
	
	
	public void updateEmail(CustomerBean customer) {
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;

		try {
			dbConn = DBConnection.getConnection();

			String updateQuery = "update customers set email = ? where phone_number = ?";
			preparedStmt = dbConn .prepareStatement(updateQuery);
			preparedStmt.setString(1, customer.getEmail());
			preparedStmt.setString(2, customer.getPhoneNumber());
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}
		
		this.updateLastModified(customer, customer.getCustomerId());
		
	}
	
	
	public void updateGender(CustomerBean customer) {
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		
		try {
			dbConn = DBConnection.getConnection();
			String updateQuery = "update customers set gender = ? where phone_number = ?";
			preparedStmt = dbConn .prepareStatement(updateQuery);
			preparedStmt.setString(1, customer.getGender());
			preparedStmt.setString(2, customer.getPhoneNumber());
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}
		this.updateLastModified(customer, customer.getCustomerId());
	}
	
	
	public void updateName(CustomerBean customer) {
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		
		try {
			dbConn = DBConnection.getConnection();

			String updateQuery = "update customers set first_name = ?, last_name = ? where phone_number = ?";
			preparedStmt = dbConn .prepareStatement(updateQuery);
			preparedStmt.setString(1, customer.getFirstName());
			preparedStmt.setString(2, customer.getLastName());
			preparedStmt.setString(3, customer.getPhoneNumber());
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}
		
		this.updateLastModified(customer, customer.getCustomerId());
		
	}
	
	
	public void updateAddress(CustomerBean customer) {
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		
		try {
			dbConn = DBConnection.getConnection();

			String updateQuery = "update customers set address = ? where phone_number = ?";
			preparedStmt = dbConn .prepareStatement(updateQuery);
			preparedStmt.setBigDecimal(1, customer.getAddress());
			preparedStmt.setString(2, customer.getPhoneNumber());
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}
		
		this.updateLastModified(customer, customer.getCustomerId());

	}
	
	public boolean updateSummary(BigDecimal customerId, String summary) {
		int updateStatus = 0;
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		
		try {
			dbConn = DBConnection.getConnection();
			String updateQuery = "update customers set summary = ? where customer_id = ?";
			preparedStmt = dbConn .prepareStatement(updateQuery);
			preparedStmt.setString(1, summary);
			preparedStmt.setBigDecimal(2, customerId);
			updateStatus = preparedStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}
		
		return updateStatus > 0 ? true : false;

	}
	
	
	public void updateShippingAddress(CustomerBean customer) {
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		
		try {
			dbConn = DBConnection.getConnection();

			String updateQuery = "update customers set address = ? where phone_number = ?";
			preparedStmt = dbConn .prepareStatement(updateQuery);
			preparedStmt.setBigDecimal(1, customer.getShippingAddress());
			preparedStmt.setString(2, customer.getPhoneNumber());
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}
		
		this.updateLastModified(customer, customer.getCustomerId());

	}
	
	
	public void updateGPSLocation(CustomerBean customer) {
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		
		try {
			dbConn = DBConnection.getConnection();

			String updateQuery = "update customers set last_gps_latitude = ?, last_gps_longitude = ? where phone_number = ?";
			preparedStmt = dbConn .prepareStatement(updateQuery);
			preparedStmt.setDouble(1, customer.getLastGPSLatitude());
			preparedStmt.setDouble(2, customer.getLastGPSLongitude());
			preparedStmt.setString(3, customer.getPhoneNumber());
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}
		
		this.updateLastModified(customer, customer.getCustomerId());

	}
	
	public void updateFacebookId(CustomerBean customer) {
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		
		try {
			dbConn = DBConnection.getConnection();

			String updateQuery = "update customers set facebook_unique_id = ? where phone_number = ?";
			preparedStmt = dbConn .prepareStatement(updateQuery);
			preparedStmt.setString(1, customer.getFacebookUniqueId());
			preparedStmt.setString(2, customer.getPhoneNumber());
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}
		
		this.updateLastModified(customer, customer.getCustomerId());

	}
	
	public void updateTwitterId(CustomerBean customer) {
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		
		try {
			dbConn = DBConnection.getConnection();

			String updateQuery = "update customers set twitter_unique_id = ? where phone_number = ?";
			preparedStmt = dbConn .prepareStatement(updateQuery);
			preparedStmt.setString(1, customer.getTwitterUniqueId());
			preparedStmt.setString(2, customer.getPhoneNumber());
			preparedStmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}

		this.updateLastModified(customer, customer.getCustomerId());
		
	}
	
	
	public void updateGoogleId(CustomerBean customer) {
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		
		try {
			dbConn = DBConnection.getConnection();

			String updateQuery = "update customers set google_unique_id = ? where phone_number = ?";
		    preparedStmt = dbConn .prepareStatement(updateQuery);
			preparedStmt.setString(1, customer.getGoogleUniqueId());
			preparedStmt.setString(2, customer.getPhoneNumber());
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}
		this.updateLastModified(customer, customer.getCustomerId());

	}
	
	
	public void updatePhoneNumber(CustomerBean customer, String phoneNumberParam) throws AlreadyExistException {
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		
		try {
			dbConn = DBConnection.getConnection();
			CustomerBean customerBean = this.getCustomer(phoneNumberParam);
	
			if( customerBean.getFirstName() != null ){
				throw new AlreadyExistException("updatePhoneNumber", "Phone is registered with Customer Id : " + customerBean.getCustomerId());
			}
			String updateQuery = "update customers set phone_number = ? where customer_id = ?";
			preparedStmt = dbConn .prepareStatement(updateQuery);
			preparedStmt.setString(1, customer.getPhoneNumber());
			preparedStmt.setBigDecimal(2, customer.getCustomerId());
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		customer.setPhoneNumber(phoneNumberParam);
		this.updateLastModified(customer, customer.getCustomerId());
		
	}
	
	
	public void updateLastModified(CustomerBean customer, BigDecimal updatedBy) {
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		
		try {
			dbConn = DBConnection.getConnection();

			String updateQuery = "update customers set last_updated_by = ?, last_update_date = ? where phone_number = ?";
			preparedStmt = dbConn
					.prepareStatement(updateQuery);

			java.util.Date currentDate = new java.util.Date();

			preparedStmt.setBigDecimal(1, updatedBy);
			preparedStmt.setDate(2, new Date(currentDate.getTime()));
			preparedStmt.setString(3, customer.getPhoneNumber());
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}

	}
	
	
	public CustomerBean insert(CustomerBean customer) {
		
		BigDecimal customerKey = BigDecimal.ZERO;
		ResultSet rs  = null;
		Connection dbConn = null;
		PreparedStatement preparedStmt = null;
		try {
			dbConn = DBConnection.getConnection();
			String insertQuery = "INSERT into customers(first_name, last_name, gender , phone_number, last_gps_latitude, last_gps_longitude , password , " + 
			"email , facebook_unique_id, twitter_unique_id, google_unique_id  , address, address2, address3, shipping_address, profile_image_id, summary, active_flag, approve_flag, " + 
			"created_by , last_updated_by) " + 
			"values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			preparedStmt = dbConn.prepareStatement(insertQuery);
			
			if(customer.getFirstName() == null) throw new BadRequestException("CustomerDAO.insert", "Customer name not provided");
				preparedStmt.setString(1, customer.getFirstName());
			preparedStmt.setString(2, customer.getLastName());
			preparedStmt.setString(3, customer.getGender());
			if(customer.getPhoneNumber() == null) throw new BadRequestException("CustomerDAO.insert", "Customer phone not provided");
				preparedStmt.setString(4, customer.getPhoneNumber());
			preparedStmt.setDouble(5, (customer.getLastGPSLatitude() == null ? 0 : customer.getLastGPSLatitude()));
			preparedStmt.setDouble(6, (customer.getLastGPSLongitude() == null ? 0 : customer.getLastGPSLongitude()));
			preparedStmt.setString(7, customer.getPassword());
			preparedStmt.setString(8, customer.getEmail());
			preparedStmt.setString(9, customer.getFacebookUniqueId());
			preparedStmt.setString(10, customer.getTwitterUniqueId());
			preparedStmt.setString(11, customer.getGoogleUniqueId());
			preparedStmt.setBigDecimal(12, customer.getAddress());
			preparedStmt.setBigDecimal(13, customer.getAddress2());
			preparedStmt.setBigDecimal(14, customer.getAddress3());
			preparedStmt.setBigDecimal(15, customer.getShippingAddress());
			preparedStmt.setBigDecimal(16, customer.getProfileImageId());
			preparedStmt.setString(17, customer.getSummary());
			preparedStmt.setString(18, customer.getActiveFlag());
			preparedStmt.setString(19, customer.getApproveFlag());
			
			preparedStmt.setBigDecimal(20, BigDecimal.ONE);
			preparedStmt.setBigDecimal(21, BigDecimal.ONE);		
			
			preparedStmt.execute();
			
			rs = preparedStmt.getGeneratedKeys();
			
			if (rs != null && rs.next()) {
				customerKey = rs.getBigDecimal(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
			if (preparedStmt != null) try { preparedStmt.close(); } catch (SQLException ignore) { }
			if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) { }
		}
		customer = this.getCustomer(customerKey);
		return customer;
	}
	
	
	public void update(CustomerBean customer) {
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		
		try {
			dbConn = DBConnection.getConnection();

			String updateQuery = "update customers phone_number = ? ,first_name = ? , last_name = ? , gender = ? , phone_number = ? , last_gps_latitude = ? , last_gps_longitude = ? , " +
			"password = ? , email = ? , facebook_unique_id = ? , twitter_unique_id = ? , google_unique_id = ? , address = ? , address2 = ? , address3 = ? , shipping_address = ? , " + 
			"profile_image_id = ? , summary = ? , active_flag = ? , approve_flag = ? , last_updated_by = ? ," + 
			"last_update_date ? , last_login_date ? where customer_id = ?";
			preparedStmt = dbConn .prepareStatement(updateQuery);
			
			preparedStmt.setString(1, customer.getPhoneNumber());
			preparedStmt.setString(2, customer.getFirstName());
			preparedStmt.setString(3, customer.getLastName());
			preparedStmt.setString(4, customer.getGender());
			preparedStmt.setString(5, customer.getPhoneNumber());
			preparedStmt.setDouble(6, customer.getLastGPSLatitude());
			preparedStmt.setDouble(7, customer.getLastGPSLongitude());
			
			preparedStmt.setString(8, customer.getPassword());			
			preparedStmt.setString(9, customer.getEmail());
			preparedStmt.setString(10, customer.getFacebookUniqueId());
			preparedStmt.setString(11, customer.getTwitterUniqueId());
			preparedStmt.setString(12, customer.getGoogleUniqueId());
			preparedStmt.setBigDecimal(13, customer.getAddress());
			preparedStmt.setBigDecimal(14, customer.getAddress2());
			preparedStmt.setBigDecimal(15, customer.getAddress3());
			preparedStmt.setBigDecimal(16, customer.getShippingAddress());
			
			preparedStmt.setBigDecimal(17, customer.getProfileImageId());
			preparedStmt.setString(18, customer.getSummary());
			preparedStmt.setString(19, customer.getActiveFlag());
			preparedStmt.setString(20, customer.getApproveFlag());
			
			preparedStmt.setBigDecimal(21, customer.getLastUpdatedBy());
			preparedStmt.setDate(22, Utility.convertFromJAVADateToSQLDate(customer.getLastUpdateDate()) );
			preparedStmt.setDate(23, Utility.convertFromJAVADateToSQLDate(customer.getLastLoginDate()) );
			preparedStmt.setBigDecimal(24, customer.getCustomerId());
			
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}

	}
	

	public static void main(String args[]) {
		CustomerDAO cust = new CustomerDAO();

		CustomerBean customer = new CustomerBean();
		customer.setPhoneNumber("9915088339");
		customer.setFirstName("AJ");
		cust.insertNewCustomerNameNumber(customer);

		// CustomerBean customer = cust.getCustomer("9915088332");
		//
		// CustomerBean customer = new CustomerBean();
		// customer.setPhoneNumber("9915088332");
		// cust.updateLoginDate(customer);
	}

}

