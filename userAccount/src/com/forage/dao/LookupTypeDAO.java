package com.forage.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.forage.bean.LookupTypeBean;
import com.forage.user.DBConnection;

public class LookupTypeDAO {

	public LookupTypeBean getLookupType(String vendorType, String lookupType, String tag) {
		LookupTypeBean lookupTypeBean = null;
		Connection dbConn  = null;
		Statement stmt  = null;
		ResultSet rs  = null;

		try {
			dbConn = DBConnection.getConnection();
			stmt = dbConn.createStatement();
			String query = "SELECT * FROM lookup_types WHERE lookup_type = '" + lookupType + "' and vendor_type = '" + vendorType + "'";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				lookupTypeBean = new LookupTypeBean();
				lookupTypeBean.setLookupType(rs.getString("lookup_type"));
				lookupTypeBean.setVendorType(rs.getString("vendor_type"));
				
				lookupTypeBean.setAttribute1(rs.getString("attribute1"));
				lookupTypeBean.setAttribute2(rs.getString("attribute2"));
				lookupTypeBean.setAttribute3(rs.getString("attribute3"));
				lookupTypeBean.setAttribute4(rs.getString("attribute4"));
				lookupTypeBean.setAttribute5(rs.getString("attribute5"));
				
				lookupTypeBean.setCreatedBy(rs.getBigDecimal("created_by"));
				lookupTypeBean.setCreatedDate(rs.getDate("creation_date"));
				lookupTypeBean.setLastUpdatedBy(rs.getBigDecimal("last_updated_by"));
				lookupTypeBean.setLastUpdateDate(rs.getDate("last_update_date"));
				lookupTypeBean.setLastUpdateLogin(rs.getBigDecimal("last_update_login"));					
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
		if(lookupTypeBean != null){
			// LookupValue List starts
			LookupValueDAO lookupValueDAO = new LookupValueDAO();
			lookupTypeBean.setLookupValues(lookupValueDAO.getLookupValues(vendorType, lookupType, tag));	
		}
		return lookupTypeBean;
	}
}
