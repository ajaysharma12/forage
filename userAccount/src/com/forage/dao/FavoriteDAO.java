package com.forage.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.forage.bean.FavoriteBean;
import com.forage.user.DBConnection;

public class FavoriteDAO {
	
	public List<FavoriteBean> getFavoriteList(BigDecimal customerId){
		ArrayList<FavoriteBean> favoriteList = new ArrayList<FavoriteBean>();
		
		Connection dbConn  = null;
		Statement stmt  = null;
		ResultSet rs  = null;
		try {
			dbConn = DBConnection.getConnection();
			stmt = dbConn.createStatement();
			String query = "SELECT * FROM favorites WHERE customer_id = '"
					+ customerId + "'";
			rs = stmt.executeQuery(query);
			while(rs.next()){
				BigDecimal favoriteId = rs.getBigDecimal("favorite_id");
				favoriteList.add(getFavorite(favoriteId));
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

		
		return favoriteList;
	}
	

	public List<FavoriteBean> getVendorFavoriteRows(BigDecimal vendorId){
		ArrayList<FavoriteBean> favoriteList = new ArrayList<FavoriteBean>();
		
		Connection dbConn  = null;
		Statement stmt  = null;
		ResultSet rs  = null;
		
		try {
			dbConn = DBConnection.getConnection();
			stmt = dbConn.createStatement();
			String query = "SELECT * FROM favorites WHERE vendor_id = '"
					+ vendorId + "'";
			rs = stmt.executeQuery(query);
			while(rs.next()){
				BigDecimal favoriteId = rs.getBigDecimal("favorite_id");
				favoriteList.add(getFavorite(favoriteId));
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

		
		return favoriteList;
	}
	
	
	
	public FavoriteBean getFavorite(BigDecimal customerId, BigDecimal vendorId){
		FavoriteBean favoriteBean = new FavoriteBean();
		
		Connection dbConn  = null;
		Statement stmt  = null;
		ResultSet rs  = null;
		
		try {
			dbConn = DBConnection.getConnection();
			stmt = dbConn.createStatement();
			String query = "SELECT * FROM favorites WHERE customer_id = '"
					+ customerId + "' and vendor_id = '" + vendorId + "'";
			rs = stmt.executeQuery(query);
			while(rs.next()){
				favoriteBean.setFavoriteId(rs.getBigDecimal("favorite_id"));
				favoriteBean.setCustomerId(rs.getBigDecimal("customer_id"));
				favoriteBean.setVendorId(rs.getBigDecimal("vendor_id"));
				favoriteBean.setStarRating(rs.getString("star_rating"));
				favoriteBean.setFavoriteFlag(rs.getString("favorite_flag"));
				favoriteBean.setBlacklistFlag(rs.getString("blacklist_flag"));
				favoriteBean.setSearchShowFlag(rs.getString("search_show_flag"));
				
				favoriteBean.setCreatedBy(rs.getBigDecimal("created_by"));
				favoriteBean.setCreatedDate(rs.getDate("creation_date"));
				favoriteBean.setLastUpdatedBy(rs.getBigDecimal("last_updated_by"));
				favoriteBean.setLastUpdateDate(rs.getDate("last_update_date"));
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
	        if (stmt  != null) try { stmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}

		
		return favoriteBean;
	}
	
	
	public FavoriteBean getFavorite(BigDecimal favoriteId){
		FavoriteBean favoriteBean = new FavoriteBean();
		
		Connection dbConn  = null;
		Statement stmt  = null;
		ResultSet rs  = null;
		try {
			dbConn = DBConnection.getConnection();
			stmt = dbConn.createStatement();
			String query = "SELECT * FROM favorites WHERE favorite_id = '"
					+ favoriteId + "'";
			rs = stmt.executeQuery(query);
			while(rs.next()){
				favoriteBean.setFavoriteId(favoriteId);
				favoriteBean.setCustomerId(rs.getBigDecimal("customer_id"));
				favoriteBean.setVendorId(rs.getBigDecimal("vendor_id"));
				favoriteBean.setStarRating(rs.getString("star_rating"));
				favoriteBean.setFavoriteFlag(rs.getString("favorite_flag"));
				favoriteBean.setBlacklistFlag(rs.getString("blacklist_flag"));
				favoriteBean.setSearchShowFlag(rs.getString("search_show_flag"));
				
				favoriteBean.setCreatedBy(rs.getBigDecimal("created_by"));
				favoriteBean.setCreatedDate(rs.getDate("creation_date"));
				favoriteBean.setLastUpdatedBy(rs.getBigDecimal("last_updated_by"));
				favoriteBean.setLastUpdateDate(rs.getDate("last_update_date"));
				
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

		
		return favoriteBean;
	}
	
	
	public FavoriteBean setFavoriteVendor(BigDecimal customerId, BigDecimal vendorId){
		FavoriteBean favoriteBean = new FavoriteBean();
		java.util.Date currentDate = new java.util.Date();
		
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		ResultSet rs  = null;
		try {
			dbConn = DBConnection.getConnection();

			String query = "insert into favorites (customer_id, vendor_id, created_by, creation_date, last_updated_by, last_update_date) values ( ?, ?, ?, ?, ?, ?);" ;
			preparedStmt = dbConn.prepareStatement(query);
			preparedStmt.setBigDecimal(1, customerId);
			preparedStmt.setBigDecimal(2, vendorId);
			preparedStmt.setBigDecimal(3, customerId);
			preparedStmt.setDate(4, new Date(currentDate.getTime()) );
			preparedStmt.setBigDecimal(5, customerId);
			preparedStmt.setDate(6, new Date(currentDate.getTime()) );
			
			preparedStmt.execute();
			rs = preparedStmt.getGeneratedKeys();
			
			BigDecimal favoriteKey = BigDecimal.ZERO;
			if (rs != null && rs.next()) {
				favoriteKey = rs.getBigDecimal(1);
			}
			
			favoriteBean = this.getFavorite(favoriteKey);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}

		return favoriteBean;
	}
	
	
	public FavoriteBean updateFavorite(FavoriteBean favoriteBean, BigDecimal updatedBy){
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		
		try {
			dbConn = DBConnection.getConnection();
			String query = "update favorites set star_rating = ?, favorite_flag = ?, blacklist_flag = ?, search_show_flag = ?, approve_flag = ?, enabled_flag = ? where favorite_id = ?";
			preparedStmt = dbConn.prepareStatement(query);
			
			preparedStmt.setString(1, favoriteBean.getStarRating());
			preparedStmt.setString(2, favoriteBean.getFavoriteFlag());
			preparedStmt.setString(3, favoriteBean.getBlacklistFlag());
			preparedStmt.setString(4, favoriteBean.getSearchShowFlag());
			preparedStmt.setString(5, favoriteBean.getApproveFlag());
			preparedStmt.setString(6, favoriteBean.getEnabledFlag());
			
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}
		
		updateLastModified(favoriteBean,updatedBy);
		
		return favoriteBean;
	}
	
	
	public void updateLastModified(FavoriteBean favoriteBean, BigDecimal updatedBy) {
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		
		try {
			dbConn = DBConnection.getConnection();

			String updateQuery = "update favorites set last_updated_by = ?, last_update_date = ? where favorite_id = ?";
			preparedStmt = dbConn.prepareStatement(updateQuery);

			java.util.Date currentDate = new java.util.Date();

			preparedStmt.setBigDecimal(1, updatedBy);
			preparedStmt.setDate(2, new Date(currentDate.getTime()));
			preparedStmt.setBigDecimal(3, favoriteBean.getFavoriteId());
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
 
		
		favoriteBean.setLastUpdatedBy(updatedBy);
	}
	
	
	public static void main(String args[]) {
		FavoriteDAO favoriteDAO = new FavoriteDAO();
		favoriteDAO.setFavoriteVendor(BigDecimal.ONE, new BigDecimal(2));

	}
	

}
