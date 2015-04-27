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

import com.forage.bean.ReviewBean;
import com.forage.user.DBConnection;
import com.forage.user.Utility;

public class ReviewDAO {

	public ReviewBean createReview(ReviewBean reviewBean) {

		Connection dbConn = null;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;

		try {
			dbConn = DBConnection.getConnection();

			String query = "insert into reviews (customer_id, vendor_id, in_time_delivery, food_quality, remarks, approve_flag, enabled_flag, created_by, creation_date, last_updated_by, last_update_date) values"
					+ " ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

			preparedStmt = dbConn.prepareStatement(query);
			preparedStmt.setBigDecimal(1, reviewBean.getCustomerId());
			preparedStmt.setBigDecimal(2, reviewBean.getVendorId());
			preparedStmt.setString(3, reviewBean.getInTimeDelivery());
			preparedStmt.setString(4, reviewBean.getFoodQuality());
			preparedStmt.setString(5, reviewBean.getRemarks());
			preparedStmt.setString(6, reviewBean.getApproveFlag());
			preparedStmt.setString(7, reviewBean.getEnabledFlag());

			preparedStmt.setBigDecimal(8, reviewBean.getCreatedBy());
			preparedStmt.setDate(9, Utility
					.convertFromJAVADateToSQLDate(reviewBean.getCreatedDate()));
			preparedStmt.setBigDecimal(10, reviewBean.getLastUpdatedBy());
			preparedStmt.setDate(11, Utility
					.convertFromJAVADateToSQLDate(reviewBean
							.getLastUpdateDate()));

			preparedStmt.execute();
			rs = preparedStmt.getGeneratedKeys();

			BigDecimal reviewKey = BigDecimal.ZERO;
			if (rs != null && rs.next()) {
				reviewKey = rs.getBigDecimal(1);
			}
			reviewBean.setReviewId(reviewKey);
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

		return reviewBean;
	}

	public List<ReviewBean> getVendorReviews(BigDecimal vendorId) {
		return getReviewList(vendorId, "VENDOR");
	}

	public List<ReviewBean> getReviewsWrittenByCustomer(BigDecimal customerId) {
		return getReviewList(customerId, "CUSTOMER");
	}

	public List<ReviewBean> getReviewList(BigDecimal userId, String userType) {
		List<BigDecimal> reviewIDs = new ArrayList<BigDecimal>();
		List<ReviewBean> reviewList = new ArrayList<ReviewBean>();

		Connection dbConn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			dbConn = DBConnection.getConnection();
			stmt = dbConn.createStatement();

			String query = "";
			if (userType.equals("VENDOR")) {
				query = "SELECT * FROM reviews WHERE vendor_id = '" + userId
						+ "'";
			} else if (userType.equals("CUSTOMER")) {
				query = "SELECT * FROM reviews WHERE customer_id = '" + userId
						+ "'";
			}

			rs = stmt.executeQuery(query);
			while (rs.next()) {
				BigDecimal reviewId = rs.getBigDecimal("review_id");
				reviewIDs.add(reviewId);
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
		
		if(!reviewIDs.isEmpty()){
			Iterator<BigDecimal> itr = reviewIDs.iterator(); 
			BigDecimal reviewId = itr.next();
			ReviewBean review = getReview(reviewId);
			reviewList.add(review);
		}		
		return reviewList;
	}

	public ReviewBean getReview(BigDecimal reviewId) {
		ReviewBean review = new ReviewBean();

		Connection dbConn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			dbConn = DBConnection.getConnection();
			stmt = dbConn.createStatement();
			String query = "SELECT * FROM reviews WHERE review_id = '"
					+ reviewId + "'";
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				review.setReviewId(rs.getBigDecimal("review_id"));
				review.setCustomerId(rs.getBigDecimal("customer_id"));
				review.setVendorId(rs.getBigDecimal("vendor_id"));
				review.setInTimeDelivery(rs.getString("in_time_delivery"));
				review.setFoodQuality(rs.getString("food_quality"));
				review.setRemarks(rs.getString("remarks"));
				review.setApproveFlag(rs.getString("approve_flag"));
				review.setEnabledFlag(rs.getString("enabled_flag"));
				review.setCreatedBy(rs.getBigDecimal("created_by"));
				review.setCreatedDate(rs.getDate("creation_date"));
				review.setLastUpdatedBy(rs.getBigDecimal("last_updated_by"));
				review.setLastUpdateDate(rs.getDate("last_update_date"));
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

		return review;
	}

	public ReviewBean updateReview(ReviewBean review) {

		Connection dbConn = null;
		PreparedStatement preparedStmt = null;

		try {
			dbConn = DBConnection.getConnection();
			String query = "update reviews set customer_id = ?, vendor_id = ?, in_time_delivery = ?, food_quality = ?, remarks = ?, approve_flag = ?, enabled_flag = ?, "
					+ " created_by = ?, creation_date = ?, last_updated_by = ?, last_update_date = ? "
					+ " where review_id = ?";
			preparedStmt = dbConn.prepareStatement(query);

			preparedStmt.setBigDecimal(1, review.getCustomerId());
			preparedStmt.setBigDecimal(2, review.getVendorId());

			preparedStmt.setString(3, review.getInTimeDelivery());
			preparedStmt.setString(4, review.getFoodQuality());
			preparedStmt.setString(5, review.getRemarks());
			preparedStmt.setString(6, review.getApproveFlag());
			preparedStmt.setString(7, review.getEnabledFlag());
			preparedStmt.setBigDecimal(8, review.getCreatedBy());
			preparedStmt.setDate(9, Utility.convertFromJAVADateToSQLDate(review
					.getCreatedDate()));
			preparedStmt.setBigDecimal(10, review.getLastUpdatedBy());
			preparedStmt.setDate(11, Utility
					.convertFromJAVADateToSQLDate(review.getLastUpdateDate()));

			preparedStmt.setBigDecimal(12, review.getReviewId());

			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
	        if (preparedStmt  != null) try { preparedStmt.close(); } catch (SQLException ignore) {}
	        if (dbConn != null) try { dbConn.close(); } catch (SQLException ignore) {}
		}
		return review;
	}

	public void updateLastModified(ReviewBean review, BigDecimal updatedBy) {
		Connection dbConn = null;
		PreparedStatement preparedStmt = null;

		try {
			dbConn = DBConnection.getConnection();

			String updateQuery = "update reviews set last_updated_by = ?, last_update_date = ? where review_id = ?";
			preparedStmt = dbConn.prepareStatement(updateQuery);

			java.util.Date currentDate = new java.util.Date();

			preparedStmt.setBigDecimal(1, updatedBy);
			preparedStmt.setDate(2, new Date(currentDate.getTime()));
			preparedStmt.setBigDecimal(3, review.getReviewId());
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

	public void approveReview(BigDecimal reviewId, boolean approve) {
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

			String query = "update reviews set approve_flag = ? where review_id = ?";
			preparedStmt = dbConn.prepareStatement(query);
			preparedStmt.setString(1, approveString);
			preparedStmt.setBigDecimal(2, reviewId);
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

	public void enableReview(BigDecimal reviewId, boolean enable) {
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

			String query = "update reviews set enabled_flag = ? where review_id = ?";
			preparedStmt = dbConn.prepareStatement(query);
			preparedStmt.setString(1, enableString);
			preparedStmt.setBigDecimal(2, reviewId);
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
