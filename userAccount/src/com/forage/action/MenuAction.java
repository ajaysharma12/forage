package com.forage.action;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.forage.bean.MenuBean;
import com.forage.dao.MenuDAO;
import com.forage.json.MenuJSON;

@Path("/menu")
public class MenuAction {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String createMenu(MenuBean menu){
		MenuDAO menuDAO = new MenuDAO();
		menu = menuDAO.createMenu(menu);
		return MenuJSON.construct(menu);
	}
	
	@POST
	@Path("new/item/{menuitem}/{userid}")  
	@Produces(MediaType.APPLICATION_JSON)
	public String addMenuItem(@PathParam("menuitem") String menuItem, 
								@PathParam("userid") BigDecimal userId){
		LookupAction lookupAction = new LookupAction();
		return lookupAction.createLookupCode("MENU_ITEM_TYPES", menuItem, userId);
	}
	
	@POST
	@Path("new/cuisine/{cuisine}/{userid}")  
	@Produces(MediaType.APPLICATION_JSON)
	public String addCuisine(@QueryParam("cuisine") String cuisine, 
							 @QueryParam("userid") BigDecimal userId){
		LookupAction lookupAction = new LookupAction();
		return lookupAction.createLookupCode("CUISINE_TYPE", cuisine, userId);
	}
	
	@PUT
	@Path("item/{menuid}/{number}/{value}")  
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMenuItem(@PathParam("menuid")BigDecimal menuId, 
								@PathParam("number")int number,
								@PathParam("value")String tag){
		
		// TODO add the implementation here
		return Response.ok("Not Yet Implemented", MediaType.APPLICATION_JSON).build();
	}
	
	@PUT
	@Path("tag/{menuid}/{tagname}/{vendorid}")  
	@Produces(MediaType.APPLICATION_JSON)
	public String tagMenu(@PathParam("menuid")BigDecimal menuId, 
						@PathParam("vendorid")BigDecimal vendorId,
						@PathParam("tagname")String tag){
		MenuDAO menuDAO = new MenuDAO();
		MenuBean menu = menuDAO.getMenu(menuId);
		menu.setMenuTag(tag);
		menuDAO.updateMenu(menu);
		menuDAO.updateLastModified(menu, vendorId);
		return MenuJSON.construct(menu);
	}
	
	@PUT
	@Path("approve/{menuid}/{approverid}")  
	@Produces(MediaType.APPLICATION_JSON)
	public String approveMenu(@PathParam("menuid")BigDecimal menuId, 
			@PathParam("approver")BigDecimal approveId){
		MenuDAO menuDAO = new MenuDAO();
		menuDAO.approveMenu(menuId, true);
		MenuBean menu = menuDAO.getMenu(menuId);
		menuDAO.updateLastModified(menu, approveId);
		return MenuJSON.construct(menu);
	}
	
	@GET
	@Path("vendor/{vendorid}")  
	@Produces(MediaType.APPLICATION_JSON)
	public String getVendorMenus(@PathParam("vendorid")BigDecimal vendorId){
		MenuDAO menuDAO = new MenuDAO();
		List<MenuBean> menuList = menuDAO.getVendorMenus(vendorId);
		return MenuJSON.constructList(menuList);
	}
	
	@GET
	@Path("vendor/{vendorid}/breakfast")  
	@Produces(MediaType.APPLICATION_JSON)
	public String getVendorBreakfastMenus(@PathParam("vendorid")BigDecimal vendorId){
		MenuDAO menuDAO = new MenuDAO();
		List<MenuBean> menuList = menuDAO.getVendorBreakfastMenus(vendorId);
		return MenuJSON.constructList(menuList);
	}
	
	@GET
	@Path("vendor/{vendorid}/lunch")  
	@Produces(MediaType.APPLICATION_JSON)
	public String getVendorLunchMenus(@PathParam("vendorid")BigDecimal vendorId){
		MenuDAO menuDAO = new MenuDAO();
		List<MenuBean> menuList = menuDAO.getVendorLunchMenus(vendorId);
		return MenuJSON.constructList(menuList);
	}
	
	@GET
	@Path("vendor/{vendorid}/dinner")  
	@Produces(MediaType.APPLICATION_JSON)
	public String getVendorDinnerMenus(@PathParam("vendorid")BigDecimal vendorId){
		MenuDAO menuDAO = new MenuDAO();
		List<MenuBean> menuList = menuDAO.getVendorDinnerMenus(vendorId);
		return MenuJSON.constructList(menuList);
	}
	
	@GET
	@Path("vendor/{vendorid}/brunch")  
	@Produces(MediaType.APPLICATION_JSON)
	public String getVendorBrunchMenus(@PathParam("vendorid")BigDecimal vendorId){
		MenuDAO menuDAO = new MenuDAO();
		List<MenuBean> menuList = menuDAO.getVendorBrunchMenus(vendorId);
		return MenuJSON.constructList(menuList);
	}
	
	@GET
	@Path("{id}")  
	@Produces(MediaType.APPLICATION_JSON)
	public String getMenu(@QueryParam("id")BigDecimal menuId){
		MenuDAO menuDAO = new MenuDAO();
		MenuBean menu = menuDAO.getMenu(menuId);
		return MenuJSON.construct(menu);
	}
	
}
