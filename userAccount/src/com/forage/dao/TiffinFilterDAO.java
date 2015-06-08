package com.forage.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.forage.bean.TiffinFilterBean;
import com.forage.exception.NotFoundException;
import com.forage.user.DBConnection;
import com.forage.user.Utility;

public class TiffinFilterDAO {
	public TiffinFilterBean getTiffinFilters(BigDecimal vendorNumber) {
		TiffinFilterBean tiffin = null;
		Connection dbConn  = null;
		Statement stmt  = null;
		ResultSet rs  = null;
		
		if(vendorNumber != null){
		
			try {
				dbConn = DBConnection.getConnection();
				stmt = dbConn.createStatement();
				String query = "SELECT * FROM vendor_filters WHERE vendor_number = '" + vendorNumber + "'";
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					tiffin = new TiffinFilterBean();
					tiffin.setVendorNumber(vendorNumber);
					tiffin.setVendorType(rs.getString("vendor_type"));
					tiffin.setCuisine1(rs.getString("attribute1"));
					tiffin.setCuisine2(rs.getString("attribute2"));
					tiffin.setCuisine3(rs.getString("attribute3"));
					tiffin.setCuisine4(rs.getString("attribute4"));
					tiffin.setCuisine5(rs.getString("attribute5"));
					tiffin.setMealSize1(rs.getString("attribute11"));
					tiffin.setMealSize2(rs.getString("attribute12"));
					tiffin.setMealSize3(rs.getString("attribute13"));
					tiffin.setMealSize4(rs.getString("attribute14"));
					tiffin.setMealSize5(rs.getString("attribute15"));
					tiffin.setPriceMealSize1(rs.getString("attribute21"));
					tiffin.setPriceMealSize2(rs.getString("attribute22"));
					tiffin.setPriceMealSize3(rs.getString("attribute23"));
					tiffin.setPriceMealSize4(rs.getString("attribute24"));
					tiffin.setPriceMealSize5(rs.getString("attribute25"));
					tiffin.setBreakfastTime(rs.getString("attribute31"));
					tiffin.setBrunchTime(rs.getString("attribute32"));
					tiffin.setLunchTime(rs.getString("attribute33"));
					tiffin.setDinnerTime(rs.getString("attribute34"));
					tiffin.setMenuType(rs.getString("attribute35"));
					
					tiffin.setCreatedBy(rs.getBigDecimal("created_by"));
					tiffin.setCreatedDate(rs.getDate("creation_date"));
					tiffin.setLastUpdatedBy(rs.getBigDecimal("last_updated_by"));
					tiffin.setLastUpdateDate(rs.getDate("last_update_date"));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
		        if (stmt  != null) try { stmt.close(); } catch (SQLException ignore) {}
		        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
			}
		}
		return tiffin;
	}
	
	
	public TiffinFilterBean insertFilters(TiffinFilterBean tiffin) {
		
		// TODO put a logic to check if vendor already exists
		Connection dbConn = null;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;

		try {
			dbConn = DBConnection.getConnection();

			String query = "insert into vendor_filters (vendor_number, vendor_type, attribute1, attribute2, attribute3, attribute4, attribute5, " + 
							"attribute11, attribute12, attribute13, attribute14, attribute15, attribute21, attribute22, attribute23, attribute24, attribute25, " + 
							" attribute31, attribute32, attribute33, attribute34, attribute35, created_by, creation_date, last_updated_by, last_update_date) values"
							+ " ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			
			preparedStmt = dbConn.prepareStatement(query);
			if(tiffin.getVendorNumber() == null || tiffin.getVendorType() == null){
				throw new NotFoundException("TiffinDAO.insert", "Vendor Number and Vendor Type are mandatory fields.");
			}
			preparedStmt.setBigDecimal(1, tiffin.getVendorNumber());
			preparedStmt.setString(2, tiffin.getVendorType());
			// Setting cuisine types
			preparedStmt.setString(3, tiffin.getCuisine1());
			preparedStmt.setString(4, tiffin.getCuisine2());
			preparedStmt.setString(5, tiffin.getCuisine3());
			preparedStmt.setString(6, tiffin.getCuisine4());
			preparedStmt.setString(7, tiffin.getCuisine5());
			// Setting meal sizes
			preparedStmt.setString(8, tiffin.getMealSize1());
			preparedStmt.setString(9, tiffin.getMealSize2());
			preparedStmt.setString(10, tiffin.getMealSize3());
			preparedStmt.setString(11, tiffin.getMealSize4());
			preparedStmt.setString(12, tiffin.getMealSize5());
			// Setting price of meal-sizes
			preparedStmt.setString(13, tiffin.getPriceMealSize1());
			preparedStmt.setString(14, tiffin.getPriceMealSize2());
			preparedStmt.setString(15, tiffin.getPriceMealSize3());
			preparedStmt.setString(16, tiffin.getPriceMealSize4());
			preparedStmt.setString(17, tiffin.getPriceMealSize5());
			// Setting Delivery times of breakfast, brunch, lunch, dinner
			preparedStmt.setString(18, tiffin.getBreakfastTime());
			preparedStmt.setString(19, tiffin.getBrunchTime());
			preparedStmt.setString(20, tiffin.getLunchTime());
			preparedStmt.setString(21, tiffin.getDinnerTime());
			// Setting menu type
			preparedStmt.setString(22, tiffin.getMenuType());

			preparedStmt.setBigDecimal(23, tiffin.getCreatedBy() == null ? BigDecimal.ONE : tiffin.getCreatedBy());
			preparedStmt.setDate(24, Utility.convertFromJAVADateToSQLDate(tiffin.getCreatedDate()));
			preparedStmt.setBigDecimal(25, tiffin.getLastUpdatedBy() == null ? BigDecimal.ONE : tiffin.getLastUpdatedBy() );
			preparedStmt.setDate(26, Utility.convertFromJAVADateToSQLDate(tiffin.getLastUpdateDate()));

			preparedStmt.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}

		return tiffin;
	}
	
	
	
	public TiffinFilterBean updateFilters(TiffinFilterBean tiffin) {
		
		Connection dbConn = null;
		PreparedStatement preparedStmt = null;

		try {
			dbConn = DBConnection.getConnection();
			String query = "update vendor_filters set vendor_type = ?, attribute1 = ?, attribute2 = ?, attribute3 = ?, attribute4 = ?, attribute5 = ?, " + 
							"attribute11 = ?, attribute12 = ?, attribute13 = ?, attribute14 = ?, attribute15 = ?, attribute21 = ?, attribute22 = ?, attribute23 = ?, attribute24 = ?, attribute25 = ?, " + 
							" attribute31 = ?, attribute32 = ?, attribute33 = ?, attribute34 = ?, attribute35 = ?, created_by = ?, creation_date = ?, last_updated_by = ?, last_update_date  = ? "
					+ " where vendor_number = ?";
			preparedStmt = dbConn.prepareStatement(query);
			
			preparedStmt.setString(1, tiffin.getVendorType());
			// Setting cuisine types
			preparedStmt.setString(2, tiffin.getCuisine1());
			preparedStmt.setString(3, tiffin.getCuisine2());
			preparedStmt.setString(4, tiffin.getCuisine3());
			preparedStmt.setString(5, tiffin.getCuisine4());
			preparedStmt.setString(6, tiffin.getCuisine5());
			// Setting meal sizes
			preparedStmt.setString(7, tiffin.getMealSize1());
			preparedStmt.setString(8, tiffin.getMealSize2());
			preparedStmt.setString(9, tiffin.getMealSize3());
			preparedStmt.setString(10, tiffin.getMealSize4());
			preparedStmt.setString(11, tiffin.getMealSize5());
			// Setting price of meal-sizes
			preparedStmt.setString(12, tiffin.getPriceMealSize1());
			preparedStmt.setString(13, tiffin.getPriceMealSize2());
			preparedStmt.setString(14, tiffin.getPriceMealSize3());
			preparedStmt.setString(15, tiffin.getPriceMealSize4());
			preparedStmt.setString(16, tiffin.getPriceMealSize5());
			// Setting Delivery times of breakfast, brunch, lunch, dinner
			preparedStmt.setString(17, tiffin.getBreakfastTime());
			preparedStmt.setString(18, tiffin.getBrunchTime());
			preparedStmt.setString(19, tiffin.getLunchTime());
			preparedStmt.setString(20, tiffin.getDinnerTime());
			// Setting menu type
			preparedStmt.setString(21, tiffin.getMenuType());

			preparedStmt.setBigDecimal(22, tiffin.getCreatedBy());
			preparedStmt.setDate(23, Utility.convertFromJAVADateToSQLDate(tiffin.getCreatedDate()));
			preparedStmt.setBigDecimal(24, tiffin.getLastUpdatedBy());
			preparedStmt.setDate(25, Utility.convertFromJAVADateToSQLDate(tiffin.getLastUpdateDate()));
			
			preparedStmt.setBigDecimal(26, tiffin.getVendorNumber());
			
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}
		
		return tiffin;
	}
}
