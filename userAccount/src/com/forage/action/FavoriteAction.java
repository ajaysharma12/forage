package com.forage.action;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.forage.bean.FavoriteBean;
import com.forage.dao.FavoriteDAO;
import com.forage.json.FavoriteJSON;
import com.forage.user.Utility;

@Path("/favorite")
public class FavoriteAction {
	@GET	
	@Path("/set")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String setFavorite(@QueryParam("customer") BigDecimal customerId, @QueryParam("vendor") BigDecimal vendorId){
		FavoriteDAO favoriteDAO = new FavoriteDAO();
		FavoriteBean favorite = favoriteDAO.getFavorite(customerId, vendorId);
		if(favorite.getFavoriteId() != null){
			return Utility.constructActionStatus("setFavorite", "Favorite Already Set");
		}else{
			favorite = favoriteDAO.setFavoriteVendor(customerId, vendorId);
		}
		return FavoriteJSON.constructStatus("setFavorite", "Success", favorite);
	}
	
	@GET	
	@Path("/setStars")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String setStarRating(@QueryParam("customer") BigDecimal customerId, @QueryParam("vendor") BigDecimal vendorId, @QueryParam("stars") String starRating){
		FavoriteDAO favoriteDAO = new FavoriteDAO();
		FavoriteBean favorite = favoriteDAO.getFavorite(customerId, vendorId);
		if(favorite.getFavoriteId() == null){
			this.setFavorite(customerId, vendorId);
			favorite = favoriteDAO.getFavorite(customerId, vendorId);
		}
		favorite.setStarRating(starRating);
		favorite = favoriteDAO.updateFavorite(favorite, customerId);
		return FavoriteJSON.constructStatus("setFavorite", "Success", favorite);
	}
	
	@GET	
	@Path("/blacklist")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String blacklistVendor(@QueryParam("customer") BigDecimal customerId, @QueryParam("vendor") BigDecimal vendorId, @QueryParam("stars") boolean blacklist){
		FavoriteDAO favoriteDAO = new FavoriteDAO();
		FavoriteBean favorite = favoriteDAO.getFavorite(customerId, vendorId);
		if(favorite.getFavoriteId() == null){
			this.setFavorite(customerId, vendorId);
			favorite = favoriteDAO.getFavorite(customerId, vendorId);
		}
		if(blacklist){
			favorite.setBlacklistFlag("Y");	
		}else{
			favorite.setBlacklistFlag("N");
		}		
		favorite = favoriteDAO.updateFavorite(favorite, customerId);
		return FavoriteJSON.constructStatus("blacklist", "Success", favorite);
	}
	
	@GET	
	@Path("/showInResults")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String showInResults(@QueryParam("customer") BigDecimal customerId, @QueryParam("vendor") BigDecimal vendorId, @QueryParam("stars") boolean showInResults){
		FavoriteDAO favoriteDAO = new FavoriteDAO();
		FavoriteBean favorite = favoriteDAO.getFavorite(customerId, vendorId);
		if(favorite.getFavoriteId() == null){
			this.setFavorite(customerId, vendorId);
			favorite = favoriteDAO.getFavorite(customerId, vendorId);
		}
		if(showInResults){
			favorite.setSearchShowFlag("Y");	
		}else{
			favorite.setSearchShowFlag("N");
		}		
		favorite = favoriteDAO.updateFavorite(favorite, customerId);
		return FavoriteJSON.constructStatus("showInResults", "Success", favorite);
	}
	
	
	@GET	
	@Path("/list")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String listFavorites(@QueryParam("customer") BigDecimal customerId){
		FavoriteDAO favoriteDAO = new FavoriteDAO();		
		List<FavoriteBean> favoriteList = favoriteDAO.getFavoriteList(customerId);
		return FavoriteJSON.constructList( favoriteList);
	}
	
	@GET	
	@Path("/vendorStats")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String vendorStats(@QueryParam("customer") BigDecimal vendorId){
		FavoriteDAO favoriteDAO = new FavoriteDAO();		
		List<FavoriteBean> favoriteList = favoriteDAO.getVendorFavoriteRows(vendorId);
		return FavoriteJSON.constructList( favoriteList);
	}
	
}
