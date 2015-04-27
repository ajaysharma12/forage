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

import com.forage.bean.ImageBean;
import com.forage.user.DBConnection;
import com.forage.user.Utility;

public class ImageDAO {
	public ImageBean createImage(ImageBean imageBean){
		
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		ResultSet rs  = null;
		
		try {
			dbConn = DBConnection.getConnection();

			String query = "insert into images (parent_image_id, image_type, image_size, image_category, image_name, image_path, menu_id, customer_id, vendor_id, created_by, creation_date, last_updated_by, last_update_date) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);" ;
			preparedStmt = dbConn.prepareStatement(query);
			preparedStmt.setBigDecimal(1, imageBean.getParentImageId());
			preparedStmt.setString(2, imageBean.getImageType());
			preparedStmt.setBigDecimal(3, imageBean.getImageSize());
			preparedStmt.setString(4, imageBean.getImageCategory());
			preparedStmt.setString(5, imageBean.getImageName());
			preparedStmt.setString(6, imageBean.getImagePath());
			preparedStmt.setBigDecimal(7, imageBean.getMenuId());
			preparedStmt.setBigDecimal(8, imageBean.getCustomerId());
			preparedStmt.setBigDecimal(9, imageBean.getVendorId());
			
			preparedStmt.setBigDecimal(10, imageBean.getCreatedBy());
			preparedStmt.setDate(11, Utility.convertFromJAVADateToSQLDate(imageBean.getCreatedDate()) );
			preparedStmt.setBigDecimal(12, imageBean.getLastUpdatedBy());
			preparedStmt.setDate(13, Utility.convertFromJAVADateToSQLDate(imageBean.getLastUpdateDate()) );
			
			preparedStmt.execute();
			rs = preparedStmt.getGeneratedKeys();
			
			BigDecimal imageKey = BigDecimal.ZERO;
			if (rs != null && rs.next()) {
				imageKey = rs.getBigDecimal(1);
			}
			
			imageBean.setImageId(imageKey);
			
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

		return imageBean;
	}
	
	
	public List<ImageBean> getMenuImages(BigDecimal menuId){
		return this.getImages("MENU", menuId);
	}
	
	public List<ImageBean> getVendorImages(BigDecimal vendorId){
		return this.getImages("VENDOR", vendorId);
	}
	
	public List<ImageBean> getCustomerImages(BigDecimal customerId){
		return this.getImages("CUSTOMER", customerId);
	}
	
	public List<ImageBean> getImages(String imageCategory, BigDecimal id){
		List<ImageBean> imageList = new ArrayList<ImageBean>();
		
		Connection dbConn  = null;
		Statement stmt  = null;
		ResultSet rs  = null;
		
		try {
			dbConn = DBConnection.getConnection();
			stmt = dbConn.createStatement();
			String query = null;
			
			
			switch(imageCategory){
			case "VENDOR" : 
				query = "SELECT * FROM images WHERE vendor_id = '"
						+ id + "'";
				break;
			case "CUSTOMER" : 
				query = "SELECT * FROM images WHERE customer_id = '"
						+ id + "'";
				break;
			case "MENU" :
				query = "SELECT * FROM images WHERE menu_id = '"
						+ id + "'";				
				break;
			case "MENUITEM" :
				query = "SELECT * FROM images WHERE menu_id = '"
						+ id + "'";
				break;
			default :
				break;
			}
			
			rs = stmt.executeQuery(query);
			while(rs.next()){
				BigDecimal imageId = rs.getBigDecimal("image_id");
				imageList.add(getImage(imageId));
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

		
		return imageList;
	}
	
	
	public ImageBean getImage(BigDecimal imageId){
		ImageBean imageBean = new ImageBean();
		
		Connection dbConn  = null;
		Statement stmt  = null;
		ResultSet rs  = null;
		
		try {
			dbConn = DBConnection.getConnection();
			stmt = dbConn.createStatement();
			String query = "SELECT * FROM images WHERE image_id = '"
					+ imageId + "'";
			rs = stmt.executeQuery(query);
			while(rs.next()){
				imageBean.setImageId(imageId);
				imageBean.setParentImageId(rs.getBigDecimal("parent_image_id"));
				
				imageBean.setImageType(rs.getString("image_type"));				
				imageBean.setImageSize(rs.getBigDecimal("image_size"));
				imageBean.setImageCategory(rs.getString("image_category"));
				imageBean.setImageName(rs.getString("image_name"));
				imageBean.setImagePath(rs.getString("image_path"));
				
				imageBean.setMenuId(rs.getBigDecimal("menu_id"));
				imageBean.setCustomerId(rs.getBigDecimal("customer_id"));
				imageBean.setVendorId(rs.getBigDecimal("vendor_id"));
				
				imageBean.setApproveFlag(rs.getString("approve_flag"));
				imageBean.setEnabledFlag(rs.getString("enabled_flag"));
				
				imageBean.setCreatedBy(rs.getBigDecimal("created_by"));
				imageBean.setCreatedDate(rs.getDate("creation_date"));
				imageBean.setLastUpdatedBy(rs.getBigDecimal("last_updated_by"));
				imageBean.setLastUpdateDate(rs.getDate("last_update_date"));
				
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

		
		return imageBean;
	}
	
	
	public void approveImage(BigDecimal imageId){
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		
		try {
			dbConn = DBConnection.getConnection();
			String query = "update images set approve_flag = ? where image_id = ?";
			preparedStmt = dbConn.prepareStatement(query);
			preparedStmt.setString(1, "Y");
			preparedStmt.setBigDecimal(2, imageId);
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
	
	public void renameImage(BigDecimal imageId, String imageNewName){
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		
		try {
			dbConn = DBConnection.getConnection();
			String query = "update images set image_name = ? where image_id = ?";
			preparedStmt = dbConn.prepareStatement(query);
			preparedStmt.setString(1, imageNewName);
			preparedStmt.setBigDecimal(2, imageId);
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
	
	public void approveImage(BigDecimal imageId, boolean approve){
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
			
			String query = "update images set approve_flag = ? where image_id = ?";
			preparedStmt = dbConn.prepareStatement(query);
			preparedStmt.setString(1, approveString);
			preparedStmt.setBigDecimal(2, imageId);
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
	
	
	public void enableImage(BigDecimal imageId, boolean enable){
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;

		try {
			dbConn = DBConnection.getConnection();
			String enableString = "";
			if(enable){
				enableString = "Y";
			}else{
				enableString = "N";
			}
			
			String query = "update images set enabled_flag = ? where image_id = ?";
			preparedStmt = dbConn.prepareStatement(query);
			preparedStmt.setString(1, enableString);
			preparedStmt.setBigDecimal(2, imageId);
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
	
	
	public void updateImageTag(BigDecimal imageId, String imageNewTag){
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;

		try {
			dbConn = DBConnection.getConnection();
			String query = "update images set image_tag = ? where image_id = ?";
			preparedStmt = dbConn.prepareStatement(query);
			preparedStmt.setString(1, imageNewTag);
			preparedStmt.setBigDecimal(2, imageId);
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
	
	
	public void updateLastModified(ImageBean image, BigDecimal updatedBy) {
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		
		try {
			dbConn = DBConnection.getConnection();

			String updateQuery = "update images set last_updated_by = ?, last_update_date = ? where image_id = ?";
			preparedStmt = dbConn
					.prepareStatement(updateQuery);

			java.util.Date currentDate = new java.util.Date();

			preparedStmt.setBigDecimal(1, updatedBy);
			preparedStmt.setDate(2, new Date(currentDate.getTime()));
			preparedStmt.setBigDecimal(3, image.getImageId());
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
