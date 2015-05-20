package com.forage.action;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.forage.bean.ImageBean;
import com.forage.bean.MenuBean;
import com.forage.dao.ImageDAO;
import com.forage.dao.MenuDAO;
import com.forage.exception.BadRequestException;
import com.forage.exception.NotFoundException;
import com.forage.json.ImageJSON;
import com.forage.user.ImageUtility;
import com.forage.user.Utility;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.multipart.file.FileDataBodyPart;

@Path("/image")
public class ImageAction {
	
	
	@POST	
	@Path("/upload")  
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON) 
	public String uploadImage(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail
			, 
			@FormDataParam("id") BigDecimal id, 
			@FormDataParam ("category") String imageCategory
			)
	{
		
//		imageId
//		parentImageId
//		imageType			: is set (reset in ImageUtility.uploadFile)
//		imageSize			: is set
//		imageCategory		: is set
//		imageName			: is set (reset in ImageUtility.uploadFile)
//		imagePath			: is set in ImageUtility.uploadFile
//		imageTag			: 
//		menuId				: is set here
//		customerId			: is set here
//		vendorId			: is set here
//		approveFlag			: is set here
//		enabledFlag			: is set here
//		createdBy			: is set here
//		createdDate
//		lastUpdatedBy		: is set here
//		lastUpdateDate
		
		ImageBean imageBean = new ImageBean();
		imageBean.setImageCategory(imageCategory);
		MenuDAO menuDAO = null;
		MenuBean menu = null;
		
		switch(imageCategory){
		case "VENDOR" : 
			imageBean.setVendorId(id);
			imageBean.setCreatedBy(id);
			imageBean.setLastUpdatedBy(id);
			break;
		case "CUSTOMER" : 
			imageBean.setCustomerId(id);
			imageBean.setCreatedBy(id);
			imageBean.setLastUpdatedBy(id);
			break;
		case "MENU" :
			imageBean.setMenuId(id);
			menuDAO = new MenuDAO();
			menu = menuDAO.getMenu(id);			
			imageBean.setCreatedBy(menu.getVendorId());
			imageBean.setLastUpdatedBy(menu.getVendorId());
			break;
		case "MENUITEM" :
			imageBean.setMenuId(id);
			menuDAO = new MenuDAO();
			menu = menuDAO.getMenu(id);			
			imageBean.setCreatedBy(menu.getVendorId());
			imageBean.setLastUpdatedBy(menu.getVendorId());
			break;
		default :
			throw new BadRequestException("ImageAction.uploadImage", "No Image Category Mentioned");
		}
		imageBean.setImageType(Utility.getFileExtension(fileDetail.getName()));
		imageBean.setImageName(Utility.getFileNameWithoutExtension(fileDetail.getName()));
		imageBean.setImageSize(new BigDecimal(fileDetail.getSize()));
		imageBean.setApproveFlag("N");
		imageBean.setEnabledFlag("N");
		imageBean.setCreatedDate(fileDetail.getCreationDate());
		imageBean.setLastUpdateDate(fileDetail.getModificationDate());
		
		ImageUtility.uploadFile(imageBean, uploadedInputStream);
		
		ImageDAO imageDAO = new ImageDAO();
		imageDAO.createImage(imageBean);
		
		return ImageJSON.construct(imageBean);
		
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON) 
	public String getImage(@PathParam("id") BigDecimal imageId){
		ImageDAO imageDAO = new ImageDAO();
		ImageBean image = imageDAO.getImage(imageId);
		if(image.getImageId() == null){
			throw new NotFoundException("ImageAction.getImage", "Image " + imageId +" not registered ");
		}
		return ImageJSON.constructStatus("ImageAction.getImage", "Success", image);
	}
	
	
	@GET
	@Path("/menu/{id}")
	@Produces(MediaType.APPLICATION_JSON) 
	public String getMenuImage(@PathParam("id") BigDecimal menuId){
		ImageDAO imageDAO = new ImageDAO();
		List<ImageBean> imageList = imageDAO.getMenuImages(menuId);
		if(imageList.isEmpty()){
			throw new NotFoundException("ImageAction.getMenuImage", "Images for menu " + menuId +" not loaded. ");
		}
		return ImageJSON.constructListStatus("ImageAction.getMenuImage", "Success", imageList);
	}
	
	@GET
	@Path("/vendor/{id}")
	@Produces(MediaType.APPLICATION_JSON) 
	public String getVendorImage(@PathParam("id") BigDecimal vendorId){
		ImageDAO imageDAO = new ImageDAO();
		List<ImageBean> imageList = imageDAO.getVendorImages(vendorId);
		if(imageList.isEmpty()){
			throw new NotFoundException("ImageAction.getVendorImage", "Images for vendor " + vendorId +" not loaded. ");
		}
		return ImageJSON.constructListStatus("ImageAction.getVendorImage", "Success", imageList);
	}
	
	
	@GET
	@Path("/customer/{id}")
	@Produces(MediaType.APPLICATION_JSON) 
	public String getCustomerImage(@PathParam("id") BigDecimal customerId){
		ImageDAO imageDAO = new ImageDAO();
		List<ImageBean> imageList = imageDAO.getCustomerImages(customerId);
		if(imageList.isEmpty()){
			throw new NotFoundException("ImageAction.getCustomerImage", "Images for customer " + customerId +" not loaded. ");
		}
		return ImageJSON.constructListStatus("ImageAction.getCustomerImage", "Success", imageList);
	}
	
	@PUT
	@Path("/approve/{imageid}/{approver}/{onoff}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String approveImage(@PathParam("imageid") BigDecimal imageId, @PathParam("approver") BigDecimal userId, @PathParam("onoff") String onoff){
		ImageDAO imageDAO = new ImageDAO();
		ImageBean image = imageDAO.getImage(imageId);
		if(image == null || image.getImageId() == null){
			throw new NotFoundException("ImageAction.approveImage", "Image <"+ imageId +"> do not exists.");
		}else{
			if("ON".equals(onoff)){
				imageDAO.approveImage(imageId, true);
			}else if("OFF".equals(onoff)){
				imageDAO.approveImage(imageId, false);
			}	
		}
		imageDAO.updateLastModified(image, userId);
		return ImageJSON.constructStatus("ImageAction.approveImage", "Success", image);
	}
	
	
	@PUT
	@Path("/{id}/tag/{tag}")
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateImageTag(@PathParam("id") BigDecimal imageId, 
								@PathParam("tag") String tag){
		ImageDAO imageDAO = new ImageDAO();
		boolean isUpdated = imageDAO.updateImageTag(imageId, tag);
		if(!isUpdated){
			throw new NotFoundException("ImageAction.updateImageTag", "Image " + imageId +" not found. ");
		}
		ImageBean image = imageDAO.getImage(imageId);
		return ImageJSON.constructStatus("ImageAction.updateImageTag", "Success", image);
	}
	
	
	
	
	
//	@POST	
//	@Path("/uploadpart1")  
//	@Produces(MediaType.APPLICATION_JSON) 
//	public String uploadImage(
//			@FormParam("file") InputStream uploadedInputStream,
//			@FormParam("file") FormDataContentDisposition fileDetail, 
//			@FormParam("id") BigDecimal custVendOrMenuId, 
//			@FormParam ("category") String imageCategory){
//		ImageBean image = new ImageBean();
//		
//		ImageBean imageBean = new ImageBean();
//		imageBean.setImageCategory(imageCategory);
//		
//		switch(imageCategory){
//		case "VENDOR" : 
//			imageBean.setVendorId(custVendOrMenuId);
//			break;
//		case "CUSTOMER" : 
//			imageBean.setCustomerId(custVendOrMenuId);
//			break;
//		case "MENU" :
//			imageBean.setMenuId(custVendOrMenuId);
//			break;
//		case "MENUITEM" :
//			imageBean.setMenuId(custVendOrMenuId);
//			break;
//		default :
//			imageBean.setCustomerId(custVendOrMenuId);
//			break;
//		}
//		imageBean.setImageType(fileDetail.getType());
//		imageBean.setImageName(fileDetail.getFileName());
//		imageBean.setImageSize(new BigDecimal(fileDetail.getSize()));
//		imageBean.setCreatedDate(fileDetail.getCreationDate());
//		imageBean.setLastUpdateDate(fileDetail.getModificationDate());
//		
//		UtilityFtp.putFile(image, uploadedInputStream);
//		
//		return ImageJSON.construct(imageBean);
//		
//	}
	
//	@POST	
//	@Path("/uploadpart2")  
//	@Produces(MediaType.APPLICATION_JSON) 
//	public String imageDBAssociation(@FormParam ("category") String imageCategory,
//			@FormParam("id") BigDecimal custVendOrMenuId){
//		
//		
//	}
	
	
	
//	public static void main(String args[])
//    {
//		try{
//		String url = "http://localhost:8080/userAccount/image/upload";
//		String fileName = "D:/2Ajay/5misc/kanujCamera/112___02/IMG_1314.JPG";
//		File fileToUpload = new File(fileName);
//		
//		HttpClient httpclient = new DefaultHttpClient();
//		HttpPost httppost = new HttpPost(url);
//		FileBody fileContent= new FileBody(fileToUpload);	
//		StringBody comment = new StringBody("Filename: " + fileName);
//		
//		MultipartEntity reqEntity = new MultipartEntity();
//		reqEntity.addPart("file", fileContent);
//				
//		httppost.setEntity(reqEntity);
//		HttpResponse response = httpclient.execute(httppost);
//		HttpEntity resEntity = response.getEntity();
//		}catch (Exception ex)
//	        {
//	            ex.printStackTrace() ;
//	        }
//	}
	
	
	public static void main(String args[])
    {
		try{
		String url = "http://localhost:8080/userAccount/image/upload";
		String fileName = "D:/2Ajay/5misc/kanujCamera/112___02/IMG_1314.JPG";
		File fileToUpload = new File(fileName);
		
		
		ClientConfig cc = new DefaultClientConfig();
		Client client = Client.create(cc);
		
		WebResource resource = client.resource(url);
		
        FormDataMultiPart form = new FormDataMultiPart();
		form.field("id", "1");
		form.field("category", "CUSTOMER");
		form.bodyPart(new FileDataBodyPart("file", fileToUpload, MediaType.MULTIPART_FORM_DATA_TYPE));
		
        ClientResponse response = resource.type(MediaType.MULTIPART_FORM_DATA).post(ClientResponse.class, form);
		
		}catch (Exception ex)
	        {
	            ex.printStackTrace() ;
	        }
	}
	

}
