package com.forage.action;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.forage.bean.MenuBean;
import com.forage.dao.MenuDAO;
import com.forage.json.MenuJSON;

@Path("/menu")
public class MenuAction {
	@GET
	@Path("/tag")  
	@Produces(MediaType.APPLICATION_JSON)
	public String tagMenu(@QueryParam("menuid")BigDecimal menuId, 
						  @QueryParam("vendor")BigDecimal vendorId,
						  @QueryParam("tag")String tag){
		MenuDAO menuDAO = new MenuDAO();
		MenuBean menu = menuDAO.getMenu(menuId);
		menu.setMenuTag(tag);
		menuDAO.updateMenu(menu);
		menuDAO.updateLastModified(menu, vendorId);
		return MenuJSON.construct(menu);
	}
	
	@GET
	@Path("/approve")  
	@Produces(MediaType.APPLICATION_JSON)
	public String approveMenu(@QueryParam("menuid")BigDecimal menuId, 
						  	  @QueryParam("approver")BigDecimal approveId){
		MenuDAO menuDAO = new MenuDAO();
		menuDAO.approveMenu(menuId, true);
		MenuBean menu = menuDAO.getMenu(menuId);
		menuDAO.updateLastModified(menu, approveId);
		return MenuJSON.construct(menu);
	}
	
	@GET
	@Path("/allmenus")  
	@Produces(MediaType.APPLICATION_JSON)
	public String getVendorMenus(@QueryParam("vendor")BigDecimal vendorId){
		MenuDAO menuDAO = new MenuDAO();
		List<MenuBean> menuList = menuDAO.getVendorMenus(vendorId);
		return MenuJSON.constructList(menuList);
	}
	
	@GET
	@Path("/breakfastmenus")  
	@Produces(MediaType.APPLICATION_JSON)
	public String getVendorBreakfastMenus(@QueryParam("vendor")BigDecimal vendorId){
		MenuDAO menuDAO = new MenuDAO();
		List<MenuBean> menuList = menuDAO.getVendorBreakfastMenus(vendorId);
		return MenuJSON.constructList(menuList);
	}
	
	@GET
	@Path("/lunchmenus")  
	@Produces(MediaType.APPLICATION_JSON)
	public String getVendorLunchMenus(@QueryParam("vendor")BigDecimal vendorId){
		MenuDAO menuDAO = new MenuDAO();
		List<MenuBean> menuList = menuDAO.getVendorLunchMenus(vendorId);
		return MenuJSON.constructList(menuList);
	}
	
	@GET
	@Path("/dinnermenus")  
	@Produces(MediaType.APPLICATION_JSON)
	public String getVendorDinnerMenus(@QueryParam("vendor")BigDecimal vendorId){
		MenuDAO menuDAO = new MenuDAO();
		List<MenuBean> menuList = menuDAO.getVendorDinnerMenus(vendorId);
		return MenuJSON.constructList(menuList);
	}
	
	@GET
	@Path("/brunchmenus")  
	@Produces(MediaType.APPLICATION_JSON)
	public String getVendorBrunchMenus(@QueryParam("vendor")BigDecimal vendorId){
		MenuDAO menuDAO = new MenuDAO();
		List<MenuBean> menuList = menuDAO.getVendorBrunchMenus(vendorId);
		return MenuJSON.constructList(menuList);
	}
	
	@GET
	@Path("/get")  
	@Produces(MediaType.APPLICATION_JSON)
	public String getMenu(@QueryParam("menu")BigDecimal menuId){
		MenuDAO menuDAO = new MenuDAO();
		MenuBean menu = menuDAO.getMenu(menuId);
		return MenuJSON.construct(menu);
	}
	
	@POST
	@Path("/create")  
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String createMenu(MenuBean menu){
		MenuDAO menuDAO = new MenuDAO();
		menu = menuDAO.createMenu(menu);
		return MenuJSON.construct(menu);
	}
	
	
	@GET
	@Path("/addmenuitem")  
	@Produces(MediaType.APPLICATION_JSON)
	public String addMenuItem(@QueryParam("menuitem") String menuItem, 
							  @QueryParam("userid") BigDecimal userId){
		LookupAction lookupAction = new LookupAction();
		return lookupAction.createLookupCode("MENU_ITEM_TYPES", menuItem, userId);
	}
	
	@GET
	@Path("/addcuisinelookup")  
	@Produces(MediaType.APPLICATION_JSON)
	public String addCuisine(@QueryParam("cuisine") String cuisine, 
							 @QueryParam("userid") BigDecimal userId){
		LookupAction lookupAction = new LookupAction();
		return lookupAction.createLookupCode("CUISINE_TYPE", cuisine, userId);
	}
	
}