package com.forage.action;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import com.forage.bean.ImageBean;
import com.forage.bean.MenuBean;
import com.forage.dao.MenuDAO;
import com.forage.json.ImageJSON;
import com.forage.user.UtilityFtp;
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
			@FormDataParam("id") BigDecimal custVendOrMenuId, 
			@FormDataParam ("category") String imageCategory
			)
	{
		
		ImageBean imageBean = new ImageBean();
		imageBean.setImageCategory(imageCategory);
		MenuDAO menuDAO = null;
		MenuBean menu = null;
		
		switch(imageCategory){
		case "VENDOR" : 
			imageBean.setVendorId(custVendOrMenuId);
			imageBean.setCreatedBy(custVendOrMenuId);
			imageBean.setLastUpdatedBy(custVendOrMenuId);
			break;
		case "CUSTOMER" : 
			imageBean.setCustomerId(custVendOrMenuId);
			imageBean.setCreatedBy(custVendOrMenuId);
			imageBean.setLastUpdatedBy(custVendOrMenuId);
			break;
		case "MENU" :
			imageBean.setMenuId(custVendOrMenuId);
			menuDAO = new MenuDAO();
			menu = menuDAO.getMenu(custVendOrMenuId);			
			imageBean.setCreatedBy(menu.getVendorId());
			imageBean.setLastUpdatedBy(menu.getVendorId());
			break;
		case "MENUITEM" :
			imageBean.setMenuId(custVendOrMenuId);
			menuDAO = new MenuDAO();
			menu = menuDAO.getMenu(custVendOrMenuId);			
			imageBean.setCreatedBy(menu.getVendorId());
			imageBean.setLastUpdatedBy(menu.getVendorId());
			break;
		default :
			imageBean.setCustomerId(custVendOrMenuId);
			break;
		}
		imageBean.setImageType(fileDetail.getType());
		imageBean.setImageName(fileDetail.getFileName());
		imageBean.setImageSize(new BigDecimal(fileDetail.getSize()));
		imageBean.setCreatedDate(fileDetail.getCreationDate());
		imageBean.setLastUpdateDate(fileDetail.getModificationDate());
		
		UtilityFtp.putFile(imageBean, uploadedInputStream);
		
		return ImageJSON.construct(imageBean);
		
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
