package com.forage.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.forage.bean.CustomerBean;
import com.forage.bean.UserPreferenceBean;
import com.forage.bean.VendorBean;
import com.forage.exception.NotFoundException;
import com.forage.user.DBConnection;

public class UserPreferenceDAO {

	
	public UserPreferenceBean getPreference(String phone){
		
		UserPreferenceBean preference = null;
		VendorBean vendor = null;
		BigDecimal userId = null;
		
		CustomerDAO customerDAO = new CustomerDAO();
		CustomerBean customer = customerDAO.getCustomer(phone);
		if(customer == null || customer.getCustomerId() == null){
			VendorDAO vendorDAO = new VendorDAO();
			vendor = vendorDAO.getVendor(phone);
			if(vendor == null || vendor.getVendorId() == null){
				throw new NotFoundException("UserPreferenceDAO.getPreference", "Phone <"+ phone + "> not registered.");
			}
			userId = vendor.getVendorId();
		}else{
			userId = customer.getCustomerId();
		}
		preference = this.getUserPreference(userId);
		return preference;
	}
	
	
	public UserPreferenceBean getPreference(BigDecimal preferenceId){
		UserPreferenceBean userPreference = null;
		Connection dbConn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			dbConn = DBConnection.getConnection();
			stmt = dbConn.createStatement();
			String query = "SELECT * FROM user_preferences WHERE preference_id = '"
					+ preferenceId + "'";
			rs = stmt.executeQuery(query);
			userPreference= new UserPreferenceBean();
			while (rs.next()) {
				userPreference.setPreferenceId(preferenceId);
				userPreference.setUserId(rs.getBigDecimal("user_id"));
				userPreference.setSearchRadius(rs.getBigDecimal("search_radius"));
				userPreference.setQuantity(rs.getBigDecimal("quantity"));
				
				userPreference.setBreakfastTime(rs.getString("breakfast_time"));
				userPreference.setBrunchTime(rs.getString("brunch_time"));
				userPreference.setLunchTime(rs.getString("lunch_time"));
				userPreference.setDinnerTime(rs.getString("dinner_time"));
				userPreference.setCuisine1(rs.getString("cuisine1"));
				userPreference.setCuisine2(rs.getString("cuisine2"));
				userPreference.setCuisine3(rs.getString("cuisine3"));
				userPreference.setCuisine4(rs.getString("cuisine4"));
				
				userPreference.setMinPrice(rs.getBigDecimal("min_price"));
				userPreference.setMaxPrice(rs.getBigDecimal("max_price"));
				
				userPreference.setNewVendorAlert(rs.getString("new_vendor_alert"));
				userPreference.setVendorUpdate(rs.getString("vendor_update"));
				userPreference.setMenuUpdate(rs.getString("menu_update"));
				userPreference.setReviewUpdate(rs.getString("review_update"));
				userPreference.setPhotoUpdate(rs.getString("photo_update"));
				userPreference.setFriendUpdate(rs.getString("friend_update"));
				userPreference.setAppUpdate(rs.getString("app_update"));
				
				userPreference.setAttribute1(rs.getString("attribute1"));
				userPreference.setAttribute2(rs.getString("attribute2"));
				userPreference.setAttribute3(rs.getString("attribute3"));
				userPreference.setAttribute4(rs.getString("attribute4"));
				userPreference.setAttribute5(rs.getString("attribute5"));
				
				
				userPreference.setApproveFlag(rs.getString("approve_flag"));
				userPreference.setEnabledFlag(rs.getString("enabled_flag"));
				
				userPreference.setCreatedBy(rs.getBigDecimal("created_by"));
				userPreference.setCreatedDate(rs.getDate("creation_date"));
				userPreference.setLastUpdatedBy(rs.getBigDecimal("last_updated_by"));
				userPreference.setLastUpdateDate(rs.getDate("last_update_date"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ignore) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ignore) {
				}
			if (dbConn != null)
				try {
					dbConn.close();
				} catch (SQLException ignore) {
				}
		}

		return userPreference;
	}
	
	
	public UserPreferenceBean getUserPreference(BigDecimal userId){
		UserPreferenceBean userPreference = null;
		Connection dbConn = null;
		Statement stmt = null;
		ResultSet rs = null;
		BigDecimal preferenceId = null;
		try {
			dbConn = DBConnection.getConnection();
			stmt = dbConn.createStatement();
			String query = "SELECT * FROM user_preferences WHERE user_id = '"
					+ userId + "'";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				preferenceId = rs.getBigDecimal("preference_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ignore) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException ignore) {
				}
			if (dbConn != null)
				try {
					dbConn.close();
				} catch (SQLException ignore) {
				}
		}
		if(preferenceId != null){
			userPreference = this.getPreference(preferenceId);
		}
		return userPreference;
	}
	
	public void updateUserPreference(UserPreferenceBean preference){
		Connection dbConn = null;
		PreparedStatement preparedStmt = null;
		try {
			dbConn = DBConnection.getConnection();
			String updateQuery = "update user_preferences set user_id = ?, search_radius = ?, quantity = ?, breakfast_time = ?, brunch_time = ?, " + 
			"lunch_time = ?, dinner_time = ?, cuisine1 = ?, cuisine2 = ?, cuisine3 = ?, cuisine4 = ?, min_price = ?, max_price = ?, " +
			"new_vendor_alert = ?, vendor_update = ?, menu_update = ?, review_update = ?, photo_update = ?, friend_update = ?, app_update = ?, attribute1 = ?, attribute2 = ?, " + 
			"attribute3 = ?, attribute4 = ?, attribute5 = ?, approve_flag = ?, enabled_flag = ? where preference_id = ?";
			// 28 attribute
			
			
			preparedStmt = dbConn.prepareStatement(updateQuery);
			
			preparedStmt.setBigDecimal(1, preference.getUserId());
			preparedStmt.setBigDecimal(2, preference.getSearchRadius());
			preparedStmt.setBigDecimal(3, preference.getQuantity());
			preparedStmt.setString(4, preference.getBreakfastTime());
			preparedStmt.setString(5, preference.getBrunchTime());
			preparedStmt.setString(6, preference.getLunchTime());
			preparedStmt.setString(7, preference.getDinnerTime());
			
			preparedStmt.setString(8, preference.getCuisine1());
			preparedStmt.setString(9, preference.getCuisine2());
			preparedStmt.setString(10, preference.getCuisine3());
			preparedStmt.setString(11, preference.getCuisine4());
			
			preparedStmt.setBigDecimal(12, preference.getMinPrice());
			preparedStmt.setBigDecimal(13, preference.getMaxPrice());
			
			preparedStmt.setString(14, preference.getNewVendorAlert());
			preparedStmt.setString(15, preference.getVendorUpdate());
			preparedStmt.setString(16, preference.getMenuUpdate());
			preparedStmt.setString(17, preference.getReviewUpdate());
			preparedStmt.setString(18, preference.getPhotoUpdate());
			preparedStmt.setString(19, preference.getFriendUpdate());
			preparedStmt.setString(20, preference.getAppUpdate());
			
			preparedStmt.setString(21, preference.getAttribute1());
			preparedStmt.setString(22, preference.getAttribute2());
			preparedStmt.setString(23, preference.getAttribute3());
			preparedStmt.setString(24, preference.getAttribute4());
			preparedStmt.setString(25, preference.getAttribute5());
			
			preparedStmt.setString(26, preference.getApproveFlag());
			preparedStmt.setString(27, preference.getEnabledFlag());
			
			preparedStmt.setBigDecimal(28, preference.getPreferenceId());
				
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (preparedStmt != null)
				try {
					preparedStmt.close();
				} catch (SQLException ignore) {
				}
			if (dbConn != null)
				try {
					dbConn.close();
				} catch (SQLException ignore) {
				}
		}
	}
	
	
	public UserPreferenceBean insert(UserPreferenceBean preference) {

		Connection dbConn = null;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			dbConn = DBConnection.getConnection();
			String insertQuery = "INSERT into user_preferences(user_id, search_radius, quantity , breakfast_time, brunch_time, lunch_time , " + 
			"dinner_time , cuisine1, cuisine2, cuisine3  , cuisine4, min_price, max_price, new_vendor_alert, vendor_update, menu_update, " + 
			"review_update, photo_update, friend_update , app_update , attribute1, attribute2, attribute3, attribute4, attribute5 , approve_flag, enabled_flag, created_by, last_updated_by ) " + 
			"values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			// 29 attributes
			
			preparedStmt = dbConn.prepareStatement(insertQuery);
			
			preparedStmt.setBigDecimal(1, preference.getUserId());
			preparedStmt.setBigDecimal(2, preference.getSearchRadius());
			preparedStmt.setBigDecimal(3, preference.getQuantity());
			
			preparedStmt.setString(4, preference.getBreakfastTime());
			preparedStmt.setString(5, preference.getBrunchTime());
			preparedStmt.setString(6, preference.getLunchTime());
			preparedStmt.setString(7, preference.getDinnerTime());
			
			preparedStmt.setString(8, preference.getCuisine1());
			preparedStmt.setString(9, preference.getCuisine2());
			preparedStmt.setString(10, preference.getCuisine3());
			preparedStmt.setString(11, preference.getCuisine4());
			
			preparedStmt.setBigDecimal(12, preference.getMinPrice());
			preparedStmt.setBigDecimal(13, preference.getMaxPrice());
			
			
			preparedStmt.setString(14, preference.getNewVendorAlert());
			preparedStmt.setString(15, preference.getVendorUpdate());
			preparedStmt.setString(16, preference.getMenuUpdate());
			preparedStmt.setString(17, preference.getReviewUpdate());
			preparedStmt.setString(18, preference.getPhotoUpdate());
			preparedStmt.setString(19, preference.getFriendUpdate());
			preparedStmt.setString(20, preference.getAppUpdate());
			
			preparedStmt.setString(21, preference.getAttribute1());
			preparedStmt.setString(22, preference.getAttribute2());
			preparedStmt.setString(23, preference.getAttribute3());
			preparedStmt.setString(24, preference.getAttribute4());
			preparedStmt.setString(25, preference.getAttribute5());
			
			preparedStmt.setString(26, preference.getApproveFlag());
			preparedStmt.setString(27, preference.getEnabledFlag());
					
			preparedStmt.setBigDecimal(28, BigDecimal.ONE);
			preparedStmt.setBigDecimal(29, BigDecimal.ONE);		

			preparedStmt.execute();
			
			rs = preparedStmt.getGeneratedKeys();

			BigDecimal preferenceKey = null;
			if (rs != null && rs.next()) {
				preferenceKey = rs.getBigDecimal(1);
			}
			preference.setPreferenceId(preferenceKey);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
			if (preparedStmt != null)
				try {
					preparedStmt.close();
				} catch (SQLException ignore) {
				}
			if (dbConn != null)
				try {
					dbConn.close();
				} catch (SQLException ignore) {
				}
		}
		preference = this.getPreference(preference.getPreferenceId());
		return preference;
	}
	
	public void approvePreference(BigDecimal preferenceId, boolean approve) {
		Connection dbConn = null;
		PreparedStatement preparedStmt = null;

		try {
			dbConn = DBConnection.getConnection();
			String approveString = "";
			if (approve) {
				approveString = "Y";
			} else {
				approveString = "N";
			}

			String query = "update user_preferences set approve_flag = ? where preference_id = ?";
			preparedStmt = dbConn.prepareStatement(query);
			preparedStmt.setString(1, approveString);
			preparedStmt.setBigDecimal(2, preferenceId);
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}
	}
	
	public void enableReview(BigDecimal preferenceId, boolean enable) {
		Connection dbConn = null;
		PreparedStatement preparedStmt = null;

		try {
			dbConn = DBConnection.getConnection();
			String enableString = "";
			if (enable) {
				enableString = "Y";
			} else {
				enableString = "N";
			}

			String query = "update user_preferences set enabled_flag = ? where preference_id = ?";
			preparedStmt = dbConn.prepareStatement(query);
			preparedStmt.setString(1, enableString);
			preparedStmt.setBigDecimal(2, preferenceId);
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}

	}
	
	public void updateLastModified(UserPreferenceBean preference, BigDecimal updatedBy) {
		Connection dbConn = null;
		PreparedStatement preparedStmt = null;

		try {
			dbConn = DBConnection.getConnection();

			String updateQuery = "update user_preferences set last_updated_by = ?, last_update_date = ? where preference_id = ?";
			preparedStmt = dbConn.prepareStatement(updateQuery);

			java.util.Date currentDate = new java.util.Date();

			preparedStmt.setBigDecimal(1, updatedBy);
			preparedStmt.setDate(2, new Date(currentDate.getTime()));
			preparedStmt.setBigDecimal(3, preference.getPreferenceId());
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
	
}
