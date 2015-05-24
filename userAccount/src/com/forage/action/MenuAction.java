package com.forage.action;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
		this.ensureDistinctMenuItems(menu);
		menu = menuDAO.createMenu(menu);
		return MenuJSON.construct(menu);
	}
	
	@POST
	@Path("new/item/{menuitem}/{userid}")  
	@Produces(MediaType.APPLICATION_JSON)
	public String addMenuItem(@PathParam("menuitem") String menuItem, 
								@PathParam("userid") BigDecimal userId){
		LookupAction lookupAction = new LookupAction();
		return lookupAction.createLookupCode("TIFFIN", "MENU_ITEM_TYPES", menuItem, userId);
	}
	
	@POST
	@Path("new/cuisine/{cuisine}/{userid}")  
	@Produces(MediaType.APPLICATION_JSON)
	public String addCuisine(@QueryParam("cuisine") String cuisine, 
							 @QueryParam("userid") BigDecimal userId){
		LookupAction lookupAction = new LookupAction();
		return lookupAction.createLookupCode("TIFFIN", "CUISINE_TYPE", cuisine, userId);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String updateMenu(MenuBean menu){
		MenuDAO menuDAO = new MenuDAO();
		this.ensureDistinctMenuItems(menu);
		menu = menuDAO.createMenu(menu);
		return MenuJSON.construct(menu);
	}
	
	@PUT
	@Path("item/{menuid}/{number}/{value}")  
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMenuItem(@PathParam("menuid")BigDecimal menuId, 
								@PathParam("number")int number,
								@PathParam("value")String value){
		MenuDAO menuDAO = new MenuDAO();
		MenuBean menu = menuDAO.getMenu(menuId);
		setMenuItemAtNumber(menu, number, value);
		menuDAO.updateMenu(menu);
		return Response.ok(MenuJSON.constructStatus("create", Response.Status.OK.toString(), menu)).build();
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
	
	private void ensureDistinctMenuItems(MenuBean menu){
		Set<String> menuSet= prepareMenuSet(menu);
		setMenuItemsNull(menu);
		fillMenu(menuSet, menu);
	}
	
	
	private Set<String> prepareMenuSet(MenuBean menu){
		Set<String> menuSet = null;
		boolean menuBool = false;
		if(menu!=null && menu.getMenuItem1() != null){
			menuSet	= new HashSet<String>();
			menuBool = (menu.getMenuItem1() != null) ? menuSet.add(menu.getMenuItem1()) :  false;
			menuBool = (menu.getMenuItem2() != null) ? menuSet.add(menu.getMenuItem2()) :  false;
			menuBool = (menu.getMenuItem3() != null) ? menuSet.add(menu.getMenuItem3()) :  false;
			menuBool = (menu.getMenuItem4() != null) ? menuSet.add(menu.getMenuItem4()) :  false;
			menuBool = (menu.getMenuItem5() != null) ? menuSet.add(menu.getMenuItem5()) :  false;
			menuBool = (menu.getMenuItem6() != null) ? menuSet.add(menu.getMenuItem6()) :  false;
			menuBool = (menu.getMenuItem7() != null) ? menuSet.add(menu.getMenuItem7()) :  false;
			menuBool = (menu.getMenuItem8() != null) ? menuSet.add(menu.getMenuItem8()) :  false;
			menuBool = (menu.getMenuItem9() != null) ? menuSet.add(menu.getMenuItem9()) :  false;
			menuBool = (menu.getMenuItem10() != null) ? menuSet.add(menu.getMenuItem10()) :  false;
			menuBool = (menu.getMenuItem11() != null) ? menuSet.add(menu.getMenuItem11()) :  false;
			menuBool = (menu.getMenuItem12() != null) ? menuSet.add(menu.getMenuItem12()) :  false;
			menuBool = (menu.getMenuItem13() != null) ? menuSet.add(menu.getMenuItem13()) :  false;
			menuBool = (menu.getMenuItem14() != null) ? menuSet.add(menu.getMenuItem14()) :  false;
			menuBool = (menu.getMenuItem15() != null) ? menuSet.add(menu.getMenuItem15()) :  false;
			menuBool = (menu.getMenuItem16() != null) ? menuSet.add(menu.getMenuItem16()) :  false;
			menuBool = (menu.getMenuItem17() != null) ? menuSet.add(menu.getMenuItem17()) :  false;
			menuBool = (menu.getMenuItem18() != null) ? menuSet.add(menu.getMenuItem18()) :  false;
			menuBool = (menu.getMenuItem19() != null) ? menuSet.add(menu.getMenuItem19()) :  false;
			menuBool = (menu.getMenuItem20() != null) ? menuSet.add(menu.getMenuItem20()) :  false;
			if(menuBool);
		}
		return menuSet;
	}
	
	
	private void setMenuItemsNull(MenuBean menu){
		if(menu != null){
			menu.setMenuItem1(null);
			menu.setMenuItem2(null);
			menu.setMenuItem3(null);
			menu.setMenuItem4(null);
			menu.setMenuItem5(null);
			menu.setMenuItem6(null);
			menu.setMenuItem7(null);
			menu.setMenuItem8(null);
			menu.setMenuItem9(null);
			menu.setMenuItem10(null);
			menu.setMenuItem11(null);
			menu.setMenuItem12(null);
			menu.setMenuItem13(null);
			menu.setMenuItem14(null);
			menu.setMenuItem15(null);
			menu.setMenuItem16(null);
			menu.setMenuItem17(null);
			menu.setMenuItem18(null);
			menu.setMenuItem19(null);
			menu.setMenuItem20(null);
		}
	}
	
	private void fillMenu(Set<String> menuSet, MenuBean menu){
		if( menuSet != null && !menuSet.isEmpty() && menu != null){
			Iterator<String> menuItr = menuSet.iterator();
			int menuItemCount = 0;
			while(menuItr.hasNext()){
				menuItemCount++;
				String menuItem = menuItr.next();
				setMenuItemAtNumber(menu, menuItemCount, menuItem);
			}
		}
	}
	
	
	private void setMenuItemAtNumber(MenuBean menu, int itemNumber, String value){
		switch(itemNumber){
			case 1 : menu.setMenuItem1(value); break;
			case 2 : menu.setMenuItem2(value); break;
			case 3 : menu.setMenuItem3(value); break;
			case 4 : menu.setMenuItem4(value); break;
			case 5 : menu.setMenuItem5(value); break;
			case 6 : menu.setMenuItem6(value); break;
			case 7 : menu.setMenuItem7(value); break;
			case 8 : menu.setMenuItem8(value); break;
			case 9 : menu.setMenuItem9(value); break;
			case 10 : menu.setMenuItem10(value); break;
			case 11 : menu.setMenuItem11(value); break;
			case 12 : menu.setMenuItem12(value); break;
			case 13 : menu.setMenuItem13(value); break;
			case 14 : menu.setMenuItem14(value); break;
			case 15 : menu.setMenuItem15(value); break;
			case 16 : menu.setMenuItem16(value); break;
			case 17 : menu.setMenuItem17(value); break;
			case 18 : menu.setMenuItem18(value); break;
			case 19 : menu.setMenuItem19(value); break;
			case 20 : menu.setMenuItem20(value); break;
			default : break;
		}
	}
	
}
