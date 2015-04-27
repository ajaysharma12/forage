package com.forage.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.forage.bean.CustomerBean;
import com.forage.exception.AlreadyExistException;
import com.forage.user.DBConnection;

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
		
		boolean insertStatus = false;
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
				insertStatus = preparedStmt.execute();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}
		
		if (insertStatus) {
			customer = getCustomer(customer.getPhoneNumber());
		}

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
		
		return customer;
	}
	
	public CustomerBean getCustomer(BigDecimal customerId) {
		CustomerBean customer = new CustomerBean();
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

		customer = getCustomer(customerPhoneNumber);
		
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
