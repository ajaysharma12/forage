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

import com.forage.bean.MenuBean;
import com.forage.user.DBConnection;
import com.forage.user.Utility;

public class MenuDAO {
	
	public  List<MenuBean> getVendorBreakfastMenus(BigDecimal vendorId){
		return this.getVendorMenus(vendorId, "BREAKFAST");
	}
	
	public  List<MenuBean> getVendorLunchMenus(BigDecimal vendorId){
		return this.getVendorMenus(vendorId, "LUNCH");
	}
	
	public  List<MenuBean> getVendorDinnerMenus(BigDecimal vendorId){
		return this.getVendorMenus(vendorId, "DINNER");
	}
	
	public  List<MenuBean> getVendorBrunchMenus(BigDecimal vendorId){
		return this.getVendorMenus(vendorId, "BRUNCH");
	}
	
	public List<MenuBean> getVendorMenus(BigDecimal vendorId){
		return this.getVendorMenus(vendorId, null);
	}
	
	public List<MenuBean> getVendorMenus(BigDecimal vendorId, String mealOfTheDay){
		List<BigDecimal> menuIDs = new ArrayList<BigDecimal>();
		List<MenuBean> menuList = new ArrayList<MenuBean>();
		
		Connection dbConn  = null;
		Statement stmt  = null;
		ResultSet rs  = null;
		
		try {
			dbConn = DBConnection.getConnection();
			stmt = dbConn.createStatement();
			
			String query = "";
			
			if(mealOfTheDay == null){
				query = "SELECT * FROM menus WHERE vendor_id = '"
						+ vendorId + "'";
			}else{
				query = "SELECT * FROM menus WHERE vendor_id = '"
						+ vendorId + "' and meal_of_the_day = " + mealOfTheDay + "'";
			}
			
			rs = stmt.executeQuery(query);
			
			while(rs.next()){
				BigDecimal menuId = rs.getBigDecimal("menu_id");
				menuIDs.add(menuId);
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

		if(!menuIDs.isEmpty()){
			Iterator<BigDecimal> itr = menuIDs.iterator(); 
			BigDecimal menuId = itr.next();
			MenuBean menu = getMenu(menuId);
			menuList.add(menu);	
		}
		return menuList;
	}
	
	
	public MenuBean getMenu(BigDecimal menuId){
		MenuBean menu = new MenuBean();
		
		Connection dbConn  = null;
		Statement stmt  = null;
		ResultSet rs  = null;
		
		try {
			dbConn = DBConnection.getConnection();
			stmt = dbConn.createStatement();
			String query = "SELECT * FROM menus WHERE menu_id = '"
					+ menuId + "'";
			rs = stmt.executeQuery(query);
			
			while(rs.next()){
				menu.setMenuId(rs.getBigDecimal("menu_id"));
				menu.setVendorId(rs.getBigDecimal("vendor_id"));
				menu.setMenuType(rs.getString("menu_type"));
				menu.setCuisine(rs.getString("cuisine"));
				menu.setMealOfTheDay(rs.getString("meal_of_the_day"));
				menu.setDeliveryTimeRange(rs.getString("delivery_time_range"));
				menu.setPrice(rs.getBigDecimal("price"));
				menu.setDayOfWeek(rs.getString("day_of_week"));
				menu.setMenuTag(rs.getString("menu_tag"));
				menu.setApproveFlag(rs.getString("approve_flag"));
				menu.setEnabledFlag(rs.getString("enabled_flag"));
				menu.setMenuItem1(rs.getString("menu_item1"));
				menu.setMenuItem2(rs.getString("menu_item2"));
				menu.setMenuItem3(rs.getString("menu_item3"));
				menu.setMenuItem4(rs.getString("menu_item4"));
				menu.setMenuItem5(rs.getString("menu_item5"));
				menu.setMenuItem6(rs.getString("menu_item6"));
				menu.setMenuItem7(rs.getString("menu_item7"));
				menu.setMenuItem8(rs.getString("menu_item8"));
				menu.setMenuItem9(rs.getString("menu_item9"));
				menu.setMenuItem10(rs.getString("menu_item10"));
				menu.setMenuItem11(rs.getString("menu_item11"));
				menu.setMenuItem12(rs.getString("menu_item12"));
				menu.setMenuItem13(rs.getString("menu_item13"));
				menu.setMenuItem14(rs.getString("menu_item14"));
				menu.setMenuItem15(rs.getString("menu_item15"));
				menu.setMenuItem16(rs.getString("menu_item16"));
				menu.setMenuItem17(rs.getString("menu_item17"));
				menu.setMenuItem18(rs.getString("menu_item18"));
				menu.setMenuItem19(rs.getString("menu_item19"));
				menu.setMenuItem20(rs.getString("menu_item20"));
				menu.setProfileImageId(rs.getBigDecimal("profile_image_id"));
				menu.setCreatedBy(rs.getBigDecimal("created_by"));
				menu.setCreatedDate(rs.getDate("creation_date"));
				menu.setLastUpdatedBy(rs.getBigDecimal("last_updated_by"));
				menu.setLastUpdateDate(rs.getDate("last_update_date"));
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
		return menu;
	}
	
	
	public void approveMenu(BigDecimal menuId, boolean approve){
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
			String query = "update menus set approve_flag = ? where menu_id = ?";
			preparedStmt = dbConn.prepareStatement(query);
			preparedStmt.setString(1, approveString);
			preparedStmt.setBigDecimal(2, menuId);
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
	
	
	public void enableMenu(BigDecimal menuId, boolean approve){
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
			
			String query = "update menus set enabled_flag = ? where menu_id = ?";
			preparedStmt = dbConn.prepareStatement(query);
			preparedStmt.setString(1, enableString);
			preparedStmt.setBigDecimal(2, menuId);
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

	public void updateLastModified(MenuBean menu, BigDecimal updatedBy) {
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;

		try {
			dbConn = DBConnection.getConnection();

			String updateQuery = "update menus set last_updated_by = ?, last_update_date = ? where menu_id = ?";
			preparedStmt = dbConn
					.prepareStatement(updateQuery);

			java.util.Date currentDate = new java.util.Date();

			preparedStmt.setBigDecimal(1, updatedBy);
			preparedStmt.setDate(2, new Date(currentDate.getTime()));
			preparedStmt.setBigDecimal(3, menu.getMenuId());
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
	
	
	public MenuBean createMenu(MenuBean menuBean){
		
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		ResultSet rs  = null;

		try {
			dbConn = DBConnection.getConnection();

			String query = "insert into menus (vendor_id, menu_type, cuisine, meal_of_the_day, delivery_time_range, price, day_of_week, menu_tag, approve_flag, enabled_flag, menu_item1, menu_item2, menu_item3, menu_item4, menu_item5, menu_item6, menu_item7, " +
					"menu_item8, menu_item9, menu_item10, menu_item11, menu_item12, menu_item13, menu_item14, menu_item15, menu_item16, menu_item17, menu_item18, menu_item19, menu_item20, profile_image_id, created_by, creation_date, last_updated_by, last_update_date) values" + 
					" ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);" ;
			
			preparedStmt = dbConn.prepareStatement(query);
			preparedStmt.setBigDecimal(1, menuBean.getVendorId());
			preparedStmt.setString(2, menuBean.getMenuType());
			preparedStmt.setString(3, menuBean.getCuisine());
			preparedStmt.setString(4, menuBean.getMealOfTheDay());
			preparedStmt.setString(5, menuBean.getDeliveryTimeRange());
			preparedStmt.setBigDecimal(6, menuBean.getPrice());
			preparedStmt.setString(7, menuBean.getDayOfWeek());
			preparedStmt.setString(8, menuBean.getMenuTag());
			preparedStmt.setString(9, menuBean.getApproveFlag());
			preparedStmt.setString(10, menuBean.getEnabledFlag());
			
			preparedStmt.setString(11, menuBean.getMenuItem1());
			preparedStmt.setString(12, menuBean.getMenuItem2());
			preparedStmt.setString(13, menuBean.getMenuItem3());
			preparedStmt.setString(14, menuBean.getMenuItem4());
			preparedStmt.setString(15, menuBean.getMenuItem5());
			preparedStmt.setString(16, menuBean.getMenuItem6());
			preparedStmt.setString(17, menuBean.getMenuItem7());
			preparedStmt.setString(18, menuBean.getMenuItem8());
			preparedStmt.setString(19, menuBean.getMenuItem9());
			preparedStmt.setString(20, menuBean.getMenuItem10());
			preparedStmt.setString(21, menuBean.getMenuItem11());
			preparedStmt.setString(22, menuBean.getMenuItem12());
			preparedStmt.setString(23, menuBean.getMenuItem13());
			preparedStmt.setString(24, menuBean.getMenuItem14());
			preparedStmt.setString(25, menuBean.getMenuItem15());
			preparedStmt.setString(26, menuBean.getMenuItem16());
			preparedStmt.setString(27, menuBean.getMenuItem17());
			preparedStmt.setString(28, menuBean.getMenuItem18());
			preparedStmt.setString(29, menuBean.getMenuItem19());
			preparedStmt.setString(30, menuBean.getMenuItem20());
			
			preparedStmt.setBigDecimal(31, menuBean.getProfileImageId());
			
			preparedStmt.setBigDecimal(32, menuBean.getCreatedBy());
			preparedStmt.setDate(33, Utility.convertFromJAVADateToSQLDate(menuBean.getCreatedDate()) );
			preparedStmt.setBigDecimal(34, menuBean.getLastUpdatedBy());
			preparedStmt.setDate(35, Utility.convertFromJAVADateToSQLDate(menuBean.getLastUpdateDate()) );
			
			preparedStmt.execute();
			rs = preparedStmt.getGeneratedKeys();
			
			BigDecimal menuKey = BigDecimal.ZERO;
			if (rs != null && rs.next()) {
				menuKey = rs.getBigDecimal(1);
			}			
			menuBean.setMenuId(menuKey);
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

		return menuBean;
	}

	
	public MenuBean updateMenu(MenuBean menu) {
		Connection dbConn  = null;
		PreparedStatement preparedStmt  = null;
		
		try {
			dbConn = DBConnection.getConnection();
			String query = "update addresses set vendor_id = ?, menu_type = ?, cuisine = ?, meal_of_the_day = ?, delivery_time_range = ?, price = ?, day_of_week = ?, menu_tag = ?, approve_flag = ?, enabled_flag = ?, " +
			"menu_item1 = ?, menu_item2 = ?, menu_item3 = ?, menu_item4 = ?, menu_item5 = ?, menu_item6 = ?, menu_item7 = ?, menu_item8 = ?, menu_item9 = ?, menu_item10 = ?, " +
			"menu_item11 = ?, menu_item12 = ?, menu_item13 = ?, menu_item14 = ?, menu_item15 = ?, menu_item16 = ?, menu_item17 = ?, menu_item18 = ?, menu_item19 = ?, menu_item20 = ?, " + 
			"profile_image_id = ?" +
			" where menu_id = ?";
			preparedStmt = dbConn.prepareStatement(query);
		
			preparedStmt.setBigDecimal(1, menu.getVendorId());
			preparedStmt.setString(2, menu.getMenuType());
			preparedStmt.setString(3, menu.getCuisine());
			preparedStmt.setString(4, menu.getMealOfTheDay() );
			preparedStmt.setString(5, menu.getDeliveryTimeRange() );
			preparedStmt.setBigDecimal(6, menu.getPrice() );
			preparedStmt.setString(7, menu.getDayOfWeek());
			preparedStmt.setString(8, menu.getMenuTag());
			preparedStmt.setString(9, menu.getApproveFlag());
			preparedStmt.setString(10, menu.getEnabledFlag());
			
			preparedStmt.setString(11, menu.getMenuItem1());
			preparedStmt.setString(12, menu.getMenuItem2());
			preparedStmt.setString(13, menu.getMenuItem3());
			preparedStmt.setString(14, menu.getMenuItem4());
			preparedStmt.setString(15, menu.getMenuItem5());
			preparedStmt.setString(16, menu.getMenuItem6());
			preparedStmt.setString(17, menu.getMenuItem7());
			preparedStmt.setString(18, menu.getMenuItem8());
			preparedStmt.setString(19, menu.getMenuItem9());
			preparedStmt.setString(20, menu.getMenuItem10());
			preparedStmt.setString(21, menu.getMenuItem11());
			preparedStmt.setString(22, menu.getMenuItem12());
			preparedStmt.setString(23, menu.getMenuItem13());
			preparedStmt.setString(24, menu.getMenuItem14());
			preparedStmt.setString(25, menu.getMenuItem15());
			preparedStmt.setString(26, menu.getMenuItem16());
			preparedStmt.setString(27, menu.getMenuItem17());
			preparedStmt.setString(28, menu.getMenuItem18());
			preparedStmt.setString(29, menu.getMenuItem19());
			preparedStmt.setString(30, menu.getMenuItem20());
			preparedStmt.setBigDecimal(31, menu.getMenuId());			
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}

		return menu;
	}
	
}
