package com.forage.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.forage.bean.LookupValueBean;
import com.forage.user.DBConnection;

public class LookupValueDAO {
	
	public List<LookupValueBean> getLookupValues(String lookupType, String tag){
		List<LookupValueBean> lookupValueList = new ArrayList<LookupValueBean>();
		List<String> lookupCodes = new ArrayList<String>();
		
		Connection dbConn  = null;
		Statement stmt  = null;
		ResultSet rs  = null;

		try {
			dbConn = DBConnection.getConnection();
			stmt = dbConn.createStatement();
			String query = "";
			if(tag == null || "".equals(tag)){
				query = "SELECT * FROM lookup_values WHERE lookup_type = '" + lookupType + "'";	
			}else{
				query = "SELECT * FROM lookup_values WHERE lookup_type = '" + lookupType + "' and tag = '" + tag + "'";
			}
			
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String lookupCode = rs.getString("lookup_code");
				lookupCodes.add(lookupCode);
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
		
		if(!lookupCodes.isEmpty()){
			Iterator<String> itr = lookupCodes.iterator();
			while(itr.hasNext()){
				String lookupCode = itr.next();
				lookupValueList.add(this.getLookupValue(lookupType, lookupCode));	
			}
		}
		
		return lookupValueList;
	}
	
	public List<LookupValueBean> getLookupValues(String lookupType, String tag, String language){
		List<LookupValueBean> lookupValueList = new ArrayList<LookupValueBean>();
		List<String> lookupCodes = new ArrayList<String>();
		
		Connection dbConn  = null;
		Statement stmt  = null;
		ResultSet rs  = null;

		try {
			dbConn = DBConnection.getConnection();
			stmt = dbConn.createStatement();
			String query = "SELECT * FROM lookup_values WHERE lookup_type = '" + lookupType + "'";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String lookupCode = rs.getString("lookup_code");
				lookupCodes.add(lookupCode);				
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
		
		if(!lookupCodes.isEmpty()){
			Iterator<String> itr = lookupCodes.iterator();
			while(itr.hasNext()){
				String lookupCode = itr.next();
				lookupValueList.add(this.getLookupValue(lookupType, lookupCode));	
			}
		}
		
		return lookupValueList;
	}
	
	public LookupValueBean getLookupValue(String lookupType, String lookupCode, String language){
		LookupValueBean lookupValueBean = new LookupValueBean();
		
		Connection dbConn  = null;
		Statement stmt  = null;
		ResultSet rs  = null;
		
		try {
			dbConn = DBConnection.getConnection();
			stmt = dbConn.createStatement();
			String query = "SELECT * FROM lookup_values WHERE lookup_type = '" + lookupType + "' and lookup_code = '" + lookupCode + "' and language = '"+ language +"'";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				lookupValueBean.setLookupType(rs.getString("lookup_type"));
				lookupValueBean.setLanguage(rs.getString("language"));
				lookupValueBean.setLookupCode(rs.getString("lookup_code"));
				lookupValueBean.setMeaning(rs.getString("meaning"));
				lookupValueBean.setDescription(rs.getString("description"));
				lookupValueBean.setTag(rs.getString("tag"));
				
				lookupValueBean.setAttribute1(rs.getString("attribute1"));
				lookupValueBean.setAttribute2(rs.getString("attribute2"));
				lookupValueBean.setAttribute3(rs.getString("attribute3"));
				lookupValueBean.setAttribute4(rs.getString("attribute4"));
				lookupValueBean.setAttribute5(rs.getString("attribute5"));
				
				lookupValueBean.setStartActiveDate(rs.getDate("start_Date_active"));
				lookupValueBean.setEndActiveDate(rs.getDate("end_date_active") == null ? null : rs.getDate("end_date_active"));
				
				lookupValueBean.setApproveFlag(rs.getString("approve_flag"));
				lookupValueBean.setEnabledFlag(rs.getString("enabled_flag"));
				
				lookupValueBean.setCreatedBy(rs.getBigDecimal("created_by"));
				lookupValueBean.setCreatedDate(rs.getDate("creation_date"));
				lookupValueBean.setLastUpdatedBy(rs.getBigDecimal("last_updated_by"));
				lookupValueBean.setLastUpdateDate(rs.getDate("last_update_date"));
				lookupValueBean.setLastUpdateLogin(rs.getBigDecimal("last_update_login"));
				
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

		return lookupValueBean;
	}
	
	
	public LookupValueBean getLookupValue(String lookupType, String lookupCode){
		String language = "US";		
		return this.getLookupValue(lookupType, lookupCode, language);
	}
	
	
	public LookupValueBean insertLookup(LookupValueBean lookupValueBean, BigDecimal createdBy){
		
		// Lookup presence check
		LookupValueBean lookupValueBeanCheck = new LookupValueBean();
		lookupValueBeanCheck = this.getLookupValue(lookupValueBean.getLookupType(), lookupValueBean.getLookupCode());
		if(lookupValueBeanCheck.getMeaning() != null){
			return lookupValueBeanCheck;
		}
		
		// New Lookup creation
		if(lookupValueBean.getLanguage() == null){
			lookupValueBean.setLanguage("US");
		}
		
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;

		try {
			dbConn = DBConnection.getConnection();
			String query = "INSERT into lookup_values ( lookup_type, language, lookup_code, meaning, description, tag, attribute1, attribute2, attribute3, attribute4, attribute5, start_Date_active, end_date_active, approve_flag, enabled_flag, created_by, last_updated_by, last_update_login) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
			preparedStmt = dbConn.prepareStatement(query);
			preparedStmt.setString(1, lookupValueBean.getLookupType());
			preparedStmt.setString(2, lookupValueBean.getLanguage());
			preparedStmt.setString(3, lookupValueBean.getLookupCode());
			preparedStmt.setString(4, lookupValueBean.getMeaning());
			preparedStmt.setString(5, lookupValueBean.getDescription());
			preparedStmt.setString(6, lookupValueBean.getTag());
			preparedStmt.setString(7, lookupValueBean.getAttribute1());
			preparedStmt.setString(8, lookupValueBean.getAttribute2());
			preparedStmt.setString(9, lookupValueBean.getAttribute3());
			preparedStmt.setString(10, lookupValueBean.getAttribute4());
			preparedStmt.setString(11, lookupValueBean.getAttribute5());
			preparedStmt.setDate(12, lookupValueBean.getStartActiveDate() !=null ? new java.sql.Date(lookupValueBean.getStartActiveDate().getTime()) :   null  );
			preparedStmt.setDate(13, lookupValueBean.getEndActiveDate() !=null ? new java.sql.Date(lookupValueBean.getEndActiveDate().getTime()) :   null  );
			preparedStmt.setString(14, lookupValueBean.getApproveFlag() != null ? lookupValueBean.getApproveFlag() : "N"    );
			preparedStmt.setString(15, lookupValueBean.getEnabledFlag() != null ? lookupValueBean.getEnabledFlag() : "Y"    );
			preparedStmt.setBigDecimal(16, createdBy);
			preparedStmt.setBigDecimal(17, createdBy);
			preparedStmt.setBigDecimal(18, createdBy);
			preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}
		lookupValueBean = this.getLookupValue(lookupValueBean.getLookupType(), lookupValueBean.getLookupCode(), lookupValueBean.getLanguage());
		
		return lookupValueBean;
	}
	
	public LookupValueBean updateLookup(LookupValueBean lookupValueBean){
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		
		try {
			dbConn = DBConnection.getConnection();
			String query = "update lookup_values set meaning = ?, description = ?, tag = ?, attribute1 = ?, attribute2 = ?, attribute3 = ?, attribute4 = ?, attribute5 = ?, start_Date_active = ?, end_date_active = ?, " +
			"approve_flag = ?, enabled_flag = ?, last_updated_by = ?, last_update_login = ? where lookup_type = ? and language = ? and lookup_code = ?";
			
			preparedStmt = dbConn.prepareStatement(query);
			
			preparedStmt.setString(1, lookupValueBean.getMeaning());
			preparedStmt.setString(2, lookupValueBean.getDescription());
			preparedStmt.setString(3, lookupValueBean.getTag());
			preparedStmt.setString(4, lookupValueBean.getAttribute1());
			preparedStmt.setString(5, lookupValueBean.getAttribute2());
			preparedStmt.setString(6, lookupValueBean.getAttribute3());
			preparedStmt.setString(7, lookupValueBean.getAttribute4());
			preparedStmt.setString(8, lookupValueBean.getAttribute5());
			
			preparedStmt.setDate(9, lookupValueBean.getStartActiveDate() !=null ? new java.sql.Date(lookupValueBean.getStartActiveDate().getTime()) :   null );
			preparedStmt.setDate(10, lookupValueBean.getEndActiveDate() !=null ? new java.sql.Date(lookupValueBean.getEndActiveDate().getTime()) :   null );
			
			preparedStmt.setString(11, lookupValueBean.getApproveFlag() !=null ? lookupValueBean.getApproveFlag() :  "N");
			preparedStmt.setString(12, lookupValueBean.getEnabledFlag() !=null ? lookupValueBean.getEnabledFlag() : "Y" );
			
			preparedStmt.setBigDecimal(13, lookupValueBean.getLastUpdatedBy());
			preparedStmt.setBigDecimal(14, lookupValueBean.getLastUpdateLogin());
			
			preparedStmt.setString(15, lookupValueBean.getLookupType());
			preparedStmt.setString(16, lookupValueBean.getLanguage());
			preparedStmt.setString(17, lookupValueBean.getLookupCode());
			
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}

		return lookupValueBean;
	}
		
	
	public void updateLastModified(LookupValueBean lookupValueBean, BigDecimal updatedBy) {
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		
		try {
			dbConn = DBConnection.getConnection();

			String updateQuery = "update lookup_values set last_updated_by = ?, last_update_date = ? where lookup_type = ? and language = ? and lookup_code = ?";
			preparedStmt = dbConn.prepareStatement(updateQuery);
			java.util.Date currentDate = new java.util.Date();

			preparedStmt.setBigDecimal(1, updatedBy);
			preparedStmt.setDate(2, new Date(currentDate.getTime()));
			preparedStmt.setString(3, lookupValueBean.getLookupType());
			preparedStmt.setString(4, lookupValueBean.getLanguage());
			preparedStmt.setString(5, lookupValueBean.getLookupCode());
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
	
	
	public void approveLookup(LookupValueBean lookupValueBean, boolean approve){
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;

		try {
			dbConn = DBConnection.getConnection();
			String approveString = "";
			if(approve){
				approveString = "Y";
			}else{
				approveString = "N";
			}			
			String query = "update lookup_values set approve_flag = ? where lookup_type = ?";
			preparedStmt = dbConn.prepareStatement(query);
			preparedStmt.setString(1, approveString);
			preparedStmt.setString(2, lookupValueBean.getLookupType());
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
	
	public void enableLookup(LookupValueBean lookupValueBean, boolean approve){
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;

		try {
			dbConn = DBConnection.getConnection();
			String enableString = "";
			if(approve){
				enableString = "Y";
			}else{
				enableString = "N";
			}
			
			String query = "update lookup_values set enabled_flag = ? where lookup_type = ?";
			preparedStmt = dbConn.prepareStatement(query);
			preparedStmt.setString(1, enableString);
			preparedStmt.setString(2, lookupValueBean.getLookupType());
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
	

}
