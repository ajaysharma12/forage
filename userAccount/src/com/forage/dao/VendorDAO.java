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

import com.forage.bean.VendorBean;
import com.forage.exception.AlreadyExistException;
import com.forage.user.DBConnection;

public class VendorDAO {

	public VendorBean getVendor(BigDecimal vendorId) {
		String vendorPhoneNumber = null;
		VendorBean vendor = new VendorBean();
		Connection dbConn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			dbConn = DBConnection.getConnection();
			stmt = dbConn.createStatement();
			String query = "SELECT * FROM vendors WHERE vendor_id = '"
					+ vendorId + "'";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				vendorPhoneNumber = rs.getString("phone_number");
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

		if (vendorPhoneNumber != null)
			vendor = this.getVendor(vendorPhoneNumber);

		return vendor;
	}

	public VendorBean getVendor(String phoneNumber) {
		VendorBean vendor = new VendorBean();
		Connection dbConn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			dbConn = DBConnection.getConnection();
			stmt = dbConn.createStatement();
			String query = "SELECT * FROM vendors WHERE phone_number = '"
					+ phoneNumber + "'";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				vendor.setVendorId(rs.getBigDecimal("vendor_id"));
				vendor.setParentVendorId(rs.getBigDecimal("parent_vendor_id"));
				vendor.setName(rs.getString("name"));
				vendor.setPhoneNumber(rs.getString("phone_number"));
				vendor.setLastGPSLatitude(rs.getDouble("last_gps_latitude"));
				vendor.setLastGPSLongitude(rs.getDouble("last_gps_longitude"));
				vendor.setPassword(rs.getString("password"));
				vendor.setEmail(rs.getString("email"));
				vendor.setFacebookUniqueId(rs.getString("facebook_unique_id"));
				vendor.setTwitterUniqueId(rs.getString("twitter_unique_id"));
				vendor.setGoogleUniqueId(rs.getString("google_unique_id"));
				vendor.setAddress1(rs.getBigDecimal("address1"));
				vendor.setAddress2(rs.getBigDecimal("address2"));
				vendor.setAddress3(rs.getBigDecimal("address3"));
				vendor.setProfileImageId(rs.getBigDecimal("profile_image_id"));
				vendor.setActiveFlag(rs.getString("active_flag"));
				vendor.setApproveFlag(rs.getString("approve_flag"));

				vendor.setStatus(rs.getString("status"));
				vendor.setMenuType(rs.getString("menu_type"));
				vendor.setCuisine(rs.getString("cuisine"));
				vendor.setCuisine2(rs.getString("cuisine2"));
				vendor.setCuisine3(rs.getString("cuisine3"));
				vendor.setCuisine4(rs.getString("cuisine4"));

				vendor.setMinPriceMeal(rs.getBigDecimal("min_price_meal"));
				vendor.setMaxPriceMeal(rs.getBigDecimal("max_price_meal"));

				vendor.setCreatedBy(rs.getBigDecimal("created_by"));
				vendor.setCreatedDate(rs.getDate("creation_date"));
				vendor.setLastUpdatedBy(rs.getBigDecimal("last_updated_by"));
				vendor.setLastUpdateDate(rs.getDate("last_update_date"));
				vendor.setLastLoginDate(rs.getDate("last_login_date"));
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

		// Address DAO starts
		AddressDAO addrDAO = new AddressDAO();
		if (vendor.getAddress1() != null) {
			vendor.setAddrBean1(addrDAO.getAddress(vendor.getAddress1()));
		}
		if (vendor.getAddress2() != null) {
			vendor.setAddrBean2(addrDAO.getAddress(vendor.getAddress2()));
		}
		if (vendor.getAddress3() != null) {
			vendor.setAddrBean3(addrDAO.getAddress(vendor.getAddress3()));
		}
		// Address DAO Ends

		// menulist starts
		MenuDAO menuDAO = new MenuDAO();
		vendor.setMenuList(menuDAO.getVendorMenus(vendor.getVendorId()));
		// menulist ends

		return vendor;
	}

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
	public void insertNewVendorNameNumber(VendorBean vendor) {

		boolean insertStatus = false;

		Connection dbConn = null;
		PreparedStatement preparedStmt = null;

		try {
			dbConn = DBConnection.getConnection();
			VendorBean vendorCheck = getVendor(vendor.getPhoneNumber());
			if (!vendorCheck.getCreatedBy().equals(BigDecimal.ONE)
					|| vendorCheck.getVendorId() == null) {
				String query = "INSERT into vendors(name, phone_number, created_by, last_updated_by) values( ?, ?, ?, ?)";
				preparedStmt = dbConn.prepareStatement(query);
				preparedStmt.setString(1, vendor.getName());
				preparedStmt.setString(2, vendor.getPhoneNumber());
				preparedStmt.setBigDecimal(3, BigDecimal.ONE);
				preparedStmt.setBigDecimal(4, BigDecimal.ONE);
				insertStatus = preparedStmt.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
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
		if (insertStatus) {
			vendor = getVendor(vendor.getPhoneNumber());
		}
	}

	public void approveVendor(BigDecimal vendorId, boolean approve) {
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
			String query = "update vendors set approve_flag = ? where vendor_id = ?";
			preparedStmt = dbConn.prepareStatement(query);
			preparedStmt.setString(1, approveString);
			preparedStmt.setBigDecimal(2, vendorId);
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
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
		VendorBean vendor = getVendor(vendorId);
		this.updateLastModified(vendor, vendor.getVendorId());
	}

	public void disApproveVendor(BigDecimal vendor_id) {
		Connection dbConn = null;
		PreparedStatement preparedStmt = null;

		try {
			dbConn = DBConnection.getConnection();
			String query = "update vendors set approve_flag = ? where vendor_id = ?";
			preparedStmt = dbConn.prepareStatement(query);
			preparedStmt.setString(1, "N");
			preparedStmt.setBigDecimal(2, vendor_id);
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
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
		VendorBean vendor = getVendor(vendor_id);
		this.updateLastModified(vendor, vendor.getVendorId());
	}

	public void updateLastModified(VendorBean vendor, BigDecimal updatedBy) {
		Connection dbConn = null;
		PreparedStatement preparedStmt = null;

		try {
			dbConn = DBConnection.getConnection();

			String updateQuery = "update vendors set last_updated_by = ?, last_update_date = ? where phone_number = ?";
			preparedStmt = dbConn.prepareStatement(updateQuery);
			java.util.Date currentDate = new java.util.Date();
			preparedStmt.setBigDecimal(1, updatedBy);
			preparedStmt.setDate(2, new Date(currentDate.getTime()));
			preparedStmt.setString(3, vendor.getPhoneNumber());
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

	public void updateLoginDate(VendorBean vendor) {
		Connection dbConn = null;
		PreparedStatement preparedStmt = null;
		try {
			dbConn = DBConnection.getConnection();
			String updateQuery = "update vendors set last_login_date = ? where phone_number = ?";
			preparedStmt = dbConn.prepareStatement(updateQuery);
			java.util.Date currentDate = new java.util.Date();
			preparedStmt.setDate(1, new Date(currentDate.getTime()));
			preparedStmt.setString(2, vendor.getPhoneNumber());
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

	public void updateAddress1(VendorBean vendor) {
		Connection dbConn = null;
		PreparedStatement preparedStmt = null;
		try {
			dbConn = DBConnection.getConnection();
			String updateQuery = "update vendors set address1 = ? where phone_number = ?";
			preparedStmt = dbConn.prepareStatement(updateQuery);
			preparedStmt.setBigDecimal(1, vendor.getAddress1());
			preparedStmt.setString(2, vendor.getPhoneNumber());
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

	public void updateAddress2(VendorBean vendor) {
		Connection dbConn = null;
		PreparedStatement preparedStmt = null;
		try {
			dbConn = DBConnection.getConnection();
			String updateQuery = "update vendors set address2 = ? where phone_number = ?";
			preparedStmt = dbConn.prepareStatement(updateQuery);
			preparedStmt.setBigDecimal(1, vendor.getAddress1());
			preparedStmt.setString(2, vendor.getPhoneNumber());
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

	public void updateAddress3(VendorBean vendor) {
		Connection dbConn = null;
		PreparedStatement preparedStmt = null;
		try {
			dbConn = DBConnection.getConnection();
			String updateQuery = "update vendors set address3 = ? where phone_number = ?";
			preparedStmt = dbConn.prepareStatement(updateQuery);
			preparedStmt.setBigDecimal(1, vendor.getAddress1());
			preparedStmt.setString(2, vendor.getPhoneNumber());
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

	public void updateEmail(VendorBean vendor) {
		Connection dbConn = null;
		PreparedStatement preparedStmt = null;
		try {
			dbConn = DBConnection.getConnection();
			String updateQuery = "update vendors set email = ? where phone_number = ?";
			preparedStmt = dbConn.prepareStatement(updateQuery);
			preparedStmt.setString(1, vendor.getEmail());
			preparedStmt.setString(2, vendor.getPhoneNumber());
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

	public void updateGPSLocation(VendorBean vendor) {
		Connection dbConn = null;
		PreparedStatement preparedStmt = null;
		try {
			dbConn = DBConnection.getConnection();
			String updateQuery = "update vendors set last_gps_latitude = ?, last_gps_longitude = ? where phone_number = ?";
			preparedStmt = dbConn.prepareStatement(updateQuery);
			preparedStmt.setDouble(1, vendor.getLastGPSLatitude());
			preparedStmt.setDouble(2, vendor.getLastGPSLongitude());
			preparedStmt.setString(3, vendor.getPhoneNumber());
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

	public void updateFacebookId(VendorBean vendor) {
		Connection dbConn = null;
		PreparedStatement preparedStmt = null;

		try {
			dbConn = DBConnection.getConnection();
			String updateQuery = "update vendors set facebook_unique_id = ? where phone_number = ?";
			preparedStmt = dbConn.prepareStatement(updateQuery);
			preparedStmt.setString(1, vendor.getFacebookUniqueId());
			preparedStmt.setString(2, vendor.getPhoneNumber());
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

	public void updateTwitterId(VendorBean vendor) {
		Connection dbConn = null;
		PreparedStatement preparedStmt = null;

		try {
			dbConn = DBConnection.getConnection();

			String updateQuery = "update vendors set twitter_unique_id = ? where phone_number = ?";
			preparedStmt = dbConn.prepareStatement(updateQuery);
			preparedStmt.setString(1, vendor.getTwitterUniqueId());
			preparedStmt.setString(2, vendor.getPhoneNumber());
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

	public void updateGoogleId(VendorBean vendor) {
		Connection dbConn = null;
		PreparedStatement preparedStmt = null;

		try {
			dbConn = DBConnection.getConnection();

			String updateQuery = "update vendors set google_unique_id = ? where phone_number = ?";
			preparedStmt = dbConn.prepareStatement(updateQuery);
			preparedStmt.setString(1, vendor.getGoogleUniqueId());
			preparedStmt.setString(2, vendor.getPhoneNumber());
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

	public void updatePhoneNumber(VendorBean vendor, String phoneNumberParam)
			throws AlreadyExistException {
		Connection dbConn = null;
		PreparedStatement preparedStmt = null;
		try {
			dbConn = DBConnection.getConnection();
			VendorBean vendorBean = new VendorBean();
			if (vendorBean.getName() != null) {
				throw new AlreadyExistException("updatePhoneNumber",
						"Phone is registered with Vendor Id : "
								+ vendorBean.getVendorId());
			}
			String updateQuery = "update vendors set phone_number = ? where vendor_id = ?";
			preparedStmt = dbConn.prepareStatement(updateQuery);
			preparedStmt.setString(1, vendor.getPhoneNumber());
			preparedStmt.setBigDecimal(2, vendor.getVendorId());
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

	public List<VendorBean> getVendorByLatLong(Double latitude,
			Double longitude, BigDecimal distance) {

		List<BigDecimal> vendorIDs = new ArrayList<BigDecimal>();
		List<VendorBean> vendorList = new ArrayList<VendorBean>();

		Connection dbConn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			dbConn = DBConnection.getConnection();

			String query = "SELECT vendor_id, ( 6373 * acos( cos( radians( ? ) ) * cos( radians( last_gps_latitude ) ) * cos(  radians( last_gps_longitude ) - radians( ? ) ) + sin(radians( ? )) * sin(radians(last_gps_latitude))  ) ) distance FROM vendors HAVING distance < ? ORDER BY distance LIMIT 25";
			stmt = dbConn.prepareStatement(query);

			stmt.setDouble(1, latitude);
			stmt.setDouble(2, longitude);
			stmt.setDouble(3, latitude);
			stmt.setBigDecimal(4, distance);

			rs = stmt.executeQuery();
			while (rs.next()) {
				BigDecimal vendorId = rs.getBigDecimal("vendor_id");
				vendorIDs.add(vendorId);
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
		if (!vendorIDs.isEmpty()) {
			Iterator<BigDecimal> itr = vendorIDs.iterator();
			BigDecimal vendorId = itr.next();
			VendorBean vendor = this.getVendor(vendorId);
			vendorList.add(vendor);
		}
		return vendorList;
	}

	public List<VendorBean> getVendorByLocality(String country, String city, String locality) {

		List<VendorBean> vendorList = new ArrayList<VendorBean>();
		List<BigDecimal> vendorIDs = new ArrayList<BigDecimal>();

		Connection dbConn = null;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;

		try {
			dbConn = DBConnection.getConnection();

			String query = "select * from vendors, addresses where addresses.country LIKE ? and addresses.city LIKE ? and addresses.locality LIKE ? and (vendors.address1 = addresses.address_id or vendors.address2 = addresses.address_id or vendors.address3 = addresses.address_id)";
			preparedStmt = dbConn.prepareStatement(query);
			preparedStmt.setString(1, "%" + country + "%");
			preparedStmt.setString(2, "%" + city + "%");
			preparedStmt.setString(3, "%" + locality + "%");
			rs = preparedStmt.executeQuery();
			while (rs.next()) {
				vendorIDs.add(rs.getBigDecimal("vendor_id"));
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

		if (!vendorIDs.isEmpty()) {
			Iterator<BigDecimal> itr = vendorIDs.iterator();
			while (itr.hasNext()) {
				BigDecimal vendorId = itr.next();
				VendorBean vendor = this.getVendor(vendorId);
				vendorList.add(vendor);
			}
		}

		return vendorList;
	}

	public void update(VendorBean vendor) {
		Connection dbConn = null;
		PreparedStatement preparedStmt = null;
		try {
			dbConn = DBConnection.getConnection();
			String updateQuery = "update vendors set parent_vendor_id = ?, name = ?, phone_number = ?, last_gps_latitude = ?, last_gps_longitude = ?, " + 
			"password = ?, email = ?, facebook_unique_id = ?, twitter_unique_id = ?, google_unique_id = ?, address1 = ?, address2 = ?, address3 = ?, " +
			"profile_image_id = ?, active_flag = ?, approve_flag = ?, status = ?, menu_type = ?, cuisine = ?, cuisine2 = ?, cuisine3 = ?, cuisine4 = ?, " + 
			"min_price_meal = ?, max_price_meal = ? where vendor_id = ?";
			
			preparedStmt = dbConn.prepareStatement(updateQuery);
			
			preparedStmt.setBigDecimal(1, vendor.getParentVendorId());
			preparedStmt.setString(2, vendor.getName());
			preparedStmt.setString(3, vendor.getPhoneNumber());
			preparedStmt.setDouble(4, vendor.getLastGPSLatitude());
			preparedStmt.setDouble(5, vendor.getLastGPSLongitude());
			preparedStmt.setString(6, vendor.getPassword());			
			preparedStmt.setString(7, vendor.getEmail());
			preparedStmt.setString(8, vendor.getFacebookUniqueId());
			preparedStmt.setString(9, vendor.getTwitterUniqueId());
			preparedStmt.setString(10, vendor.getGoogleUniqueId());
			preparedStmt.setBigDecimal(11, vendor.getAddress1());
			preparedStmt.setBigDecimal(12, vendor.getAddress2());
			preparedStmt.setBigDecimal(13, vendor.getAddress3());
			preparedStmt.setBigDecimal(14, vendor.getProfileImageId());
			preparedStmt.setString(15, vendor.getActiveFlag());
			preparedStmt.setString(16, vendor.getApproveFlag());
			preparedStmt.setString(17, vendor.getStatus());
			preparedStmt.setString(18, vendor.getMenuType());
			preparedStmt.setString(19, vendor.getCuisine());
			preparedStmt.setString(20, vendor.getCuisine2());
			preparedStmt.setString(21, vendor.getCuisine3());
			preparedStmt.setString(22, vendor.getCuisine4());			
			preparedStmt.setBigDecimal(23, vendor.getMinPriceMeal());
			preparedStmt.setBigDecimal(24, vendor.getMaxPriceMeal());			
			preparedStmt.setBigDecimal(25, vendor.getVendorId());			
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
	
	
	
	public void insert(VendorBean vendor) {
		boolean insertStatus = false;
		
		Connection dbConn = null;
		PreparedStatement preparedStmt = null;
		try {
			dbConn = DBConnection.getConnection();
			String insertQuery = "INSERT into vendors(parent_vendor_id, name, phone_number , last_gps_latitude, last_gps_longitude, password , " + 
			"email , facebook_unique_id, twitter_unique_id, google_unique_id  , address1, address2, address3, profile_image_id, active_flag, approve_flag, " + 
			"status, menu_type, cuisine , cuisine2 , cuisine3, cuisine4, min_price_meal, max_price_meal, created_by , last_updated_by) " + 
			"values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			preparedStmt = dbConn.prepareStatement(insertQuery);
			
			preparedStmt.setBigDecimal(1, vendor.getParentVendorId());
			preparedStmt.setString(2, vendor.getName());
			preparedStmt.setString(3, vendor.getPhoneNumber());
			preparedStmt.setDouble(4, vendor.getLastGPSLatitude());
			preparedStmt.setDouble(5, vendor.getLastGPSLongitude());
			preparedStmt.setString(6, vendor.getPassword());			
			preparedStmt.setString(7, vendor.getEmail());
			preparedStmt.setString(8, vendor.getFacebookUniqueId());
			preparedStmt.setString(9, vendor.getTwitterUniqueId());
			preparedStmt.setString(10, vendor.getGoogleUniqueId());
			preparedStmt.setBigDecimal(11, vendor.getAddress1());
			preparedStmt.setBigDecimal(12, vendor.getAddress2());
			preparedStmt.setBigDecimal(13, vendor.getAddress3());
			preparedStmt.setBigDecimal(14, vendor.getProfileImageId());
			preparedStmt.setString(15, vendor.getActiveFlag());
			preparedStmt.setString(16, vendor.getApproveFlag());
			preparedStmt.setString(17, vendor.getStatus());
			preparedStmt.setString(18, vendor.getMenuType());
			preparedStmt.setString(19, vendor.getCuisine());
			preparedStmt.setString(20, vendor.getCuisine2());
			preparedStmt.setString(21, vendor.getCuisine3());
			preparedStmt.setString(22, vendor.getCuisine4());			
			preparedStmt.setBigDecimal(23, vendor.getMinPriceMeal());
			preparedStmt.setBigDecimal(24, vendor.getMaxPriceMeal());			
			preparedStmt.setBigDecimal(25, BigDecimal.ONE);
			preparedStmt.setBigDecimal(26, BigDecimal.ONE);		

			insertStatus = preparedStmt.execute();
			
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
		if (insertStatus) {
			vendor = getVendor(vendor.getPhoneNumber());
		}
	}

}