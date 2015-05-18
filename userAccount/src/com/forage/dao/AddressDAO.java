package com.forage.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.forage.bean.AddressBean;
import com.forage.user.DBConnection;

public class AddressDAO {

	public AddressBean insertNewAddress(AddressBean address) {
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		
		try {
			dbConn = DBConnection.getConnection();
			String query = "INSERT into addresses(line1, line2, line3, line4, line5, line6, line7, line8, line9, line10, locality, latitude, longitude, town, city, state_province, pincode, country, tag, created_by, last_updated_by) values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
			preparedStmt = dbConn.prepareStatement(query);
			preparedStmt.setString(1, address.getLine1());
			preparedStmt.setString(2, address.getLine2() == null ? "" : address.getLine2() );
			preparedStmt.setString(3, address.getLine3() == null ? "" : address.getLine3() );
			preparedStmt.setString(4, address.getLine4() == null ? "" : address.getLine4() );
			preparedStmt.setString(5, address.getLine5() == null ? "" : address.getLine5() );
			preparedStmt.setString(6, address.getLine6() == null ? "" : address.getLine6() );
			preparedStmt.setString(7, address.getLine7() == null ? "" : address.getLine7() );
			preparedStmt.setString(8, address.getLine8() == null ? "" : address.getLine8() );
			preparedStmt.setString(9, address.getLine9() == null ? "" : address.getLine9() );
			preparedStmt.setString(10, address.getLine10() == null ? "" : address.getLine10() );
			
			preparedStmt.setString(11, address.getLocality() == null ? "" : address.getLocality() );
			
			preparedStmt.setDouble(12, address.getLatitude() == null ? null : address.getLatitude() );
			preparedStmt.setDouble(13, address.getLongitude() == null ? null : address.getLongitude() );
			
			preparedStmt.setString(14, address.getTown());
			preparedStmt.setString(15, address.getCity() == null ? "" : address.getCity());
			preparedStmt.setString(16, address.getStateProvince() == null ? "" : address.getStateProvince());
			preparedStmt.setString(17, address.getPincode() == null ? "0" : address.getPincode());
			preparedStmt.setString(18, address.getCountry() == null ? "" : address.getCountry());
			preparedStmt.setString(19, address.getTag() == null ? null : address.getTag() );
			preparedStmt.setBigDecimal(20, address.getCreatedBy());
			preparedStmt.setBigDecimal(21, address.getLastUpdatedBy());
			
			preparedStmt.execute();
			ResultSet rs = preparedStmt.getGeneratedKeys();
			
			BigDecimal addressKey = BigDecimal.ZERO;
			if (rs != null && rs.next()) {
			    addressKey = rs.getBigDecimal(1);
			}
			
			address.setAddressId(addressKey);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt  .close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}
//		address = getAddress(address.getAddressId());
		return address;
	}
	
	
	
	public AddressBean getAddress(BigDecimal addressId) {
		AddressBean address = new AddressBean();
		Connection dbConn  = null;
		Statement stmt  = null;
		ResultSet rs  = null;
		
		try {
			dbConn = DBConnection.getConnection();
			stmt = dbConn.createStatement();
			String query = "SELECT * FROM addresses WHERE address_id = '" + addressId + "'";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				address.setAddressId(rs.getBigDecimal("address_id"));
				address.setLine1(rs.getString("line1"));
				address.setLine2(rs.getString("line2"));
				address.setLine3(rs.getString("line3"));
				address.setLine4(rs.getString("line4"));
				address.setLine5(rs.getString("line5"));
				address.setLine6(rs.getString("line6"));
				address.setLine7(rs.getString("line7"));
				address.setLine8(rs.getString("line8"));
				address.setLine9(rs.getString("line9"));
				address.setLine10(rs.getString("line10"));
				
				address.setLocality(rs.getString("locality"));
				
				address.setLatitude(rs.getDouble("latitude"));
				address.setLongitude(rs.getDouble("longitude"));
				
				address.setTown(rs.getString("town"));
				address.setCity(rs.getString("city"));
				address.setStateProvince(rs.getString("state_province"));
				address.setPincode(rs.getString("pincode"));
				address.setCountry(rs.getString("Country"));
				address.setTag(rs.getString("tag"));
				address.setApproveFlag(rs.getString("approve_flag"));
				address.setEnabledFlag(rs.getString("enabled_flag"));
				address.setCreatedBy(rs.getBigDecimal("created_by"));
				address.setCreatedDate(rs.getDate("creation_date"));
				address.setLastUpdatedBy(rs.getBigDecimal("last_updated_by"));
				address.setLastUpdateDate(rs.getDate("last_update_date"));
				
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
	        if (stmt  != null) try {stmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}

		
		return address;
	}
	
	public AddressBean updateAddress(AddressBean address, BigDecimal updatedBy) {
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		
		try {
			dbConn = DBConnection.getConnection();
			String query = "update addresses set line1 = ?, line2 = ?, line3 = ?, line4 = ?, line5 = ?, line6 = ?, line7 = ?, line8 = ?, line9 = ?, line10 = ?, " +
			"locality = ? , latitude = ?, longitude = ?, town = ?, city = ?, state_province = ?, pincode = ?, country = ?, tag = ?, approve_flag = ?, enabled_flag = ? where address_id = ?";
			preparedStmt = dbConn.prepareStatement(query);
		
			preparedStmt.setString(1, address.getLine1());
			preparedStmt.setString(2, address.getLine2() == null ? "" : address.getLine2() );
			preparedStmt.setString(3, address.getLine3() == null ? "" : address.getLine3() );
			preparedStmt.setString(4, address.getLine4() == null ? "" : address.getLine4() );
			preparedStmt.setString(5, address.getLine5() == null ? "" : address.getLine5() );
			preparedStmt.setString(6, address.getLine6() == null ? "" : address.getLine6() );
			preparedStmt.setString(7, address.getLine7() == null ? "" : address.getLine7() );
			preparedStmt.setString(8, address.getLine8() == null ? "" : address.getLine8() );
			preparedStmt.setString(9, address.getLine9() == null ? "" : address.getLine9() );
			preparedStmt.setString(10, address.getLine10() == null ? "" : address.getLine10() );
			preparedStmt.setString(11, address.getLocality() == null ? "" : address.getLocality() );
			preparedStmt.setDouble(12, address.getLatitude() == null ? null : address.getLatitude() );
			preparedStmt.setDouble(13, address.getLongitude() == null ? null : address.getLongitude() );
			preparedStmt.setString(14, address.getTown());
			preparedStmt.setString(15, address.getCity() == null ? "" : address.getCity());
			preparedStmt.setString(16, address.getStateProvince() == null ? "" : address.getStateProvince());
			preparedStmt.setString(17, address.getPincode() == null ? "0" : address.getPincode());
			preparedStmt.setString(18, address.getCountry() == null ? "" : address.getCountry());
			preparedStmt.setString(19, address.getTag() == null ? null : address.getTag() );
			preparedStmt.setString(20, address.getApproveFlag());
			preparedStmt.setString(21, address.getEnabledFlag());
			preparedStmt.setBigDecimal(22, address.getAddressId());
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}
		updateLastModified(address,updatedBy);		
		return address;
	}
	
	public void updateLastModified(AddressBean address, BigDecimal updatedBy) {
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		
		try {
			dbConn = DBConnection.getConnection();

			String updateQuery = "update addresses set last_updated_by = ?, last_update_date = ? where address_id = ?";
			preparedStmt = dbConn.prepareStatement(updateQuery);

			java.util.Date currentDate = new java.util.Date();

			preparedStmt.setBigDecimal(1, updatedBy);
			preparedStmt.setDate(2, new Date(currentDate.getTime()));
			preparedStmt.setBigDecimal(3, address.getAddressId());
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
