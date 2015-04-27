package com.forage.user;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import com.forage.bean.ImageBean;

public class UtilityFtp {

	public static String ftpHost = "127.0.0.1";
	public static String ftpUser = "forage";
	public static String ftpPwd = "forage";

	public static boolean putFile(ImageBean imageBean, InputStream is)
			throws IllegalStateException {

		FTPClient ftpCon = null;

		try {
			ftpCon = new FTPClient();
			ftpCon.connect(ftpHost); // Its dummy Address
			if (ftpCon.login(ftpUser, ftpPwd)) {

				ftpCon.enterLocalPassiveMode(); // Very Important
				ftpCon.setFileType(FTP.BINARY_FILE_TYPE); // Very Important

				java.util.Date date = new java.util.Date();

				// choose Image name = customer id, vendor id or menu_id + timeStamp.
				String imageName;
				String underScore = "_";
				String endTimeStamp = Utility.getDateFormatted() + underScore + Utility.getTimeFormatted();

				if (imageBean.getCustomerId() != null) {
					imageName = "image" + underScore
							+ imageBean.getCustomerId().toString() + underScore
							+ endTimeStamp;
				} else if (imageBean.getVendorId() != null) {
					imageName = "image" + underScore
							+ imageBean.getVendorId().toString() + underScore
							+ endTimeStamp;
				} else if (imageBean.getMenuId() != null) {
					imageName = "image" + underScore
							+ imageBean.getVendorId().toString() + underScore
							+ endTimeStamp;
				} else {
					throw new IllegalStateException(
							"Image has no associated customer or vendor or menu to it");
				}
				imageBean.setImageName(imageName);
				
				// Choose upload path
				String uploadPath = shortListUploadPath(imageBean, ftpCon);
				imageBean.setImagePath(uploadPath);

				// Check image size and compress it based on condition
				int compressCount = 0;

				while (true) {
					ByteArrayOutputStream os = new ByteArrayOutputStream();
					System.out.println("Total file size to read (in bytes) : " + is.available());
					if (is.available() < 51200 || compressCount == 2)
						break;

					compressCount++;
					compressImage(is, os, imageBean.getImageName());

					is.close();
					is = new ByteArrayInputStream(os.toByteArray());
					os.close();
				}

				// TODO
				// Set the image path

				ftpCon.changeWorkingDirectory("customer");

				// boolean result = con.storeFile("hello" + ".jpg", is);
				boolean result = ftpCon.storeFile(imageName + ".jpg", is);
				is.close();

				if (result)
					System.out.println("upload result succeeded");

				ftpCon.changeToParentDirectory();
				ftpCon.logout();
				ftpCon.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}
	
	
	private static String shortListUploadPath(ImageBean imageBean, FTPClient ftpCon ){
		
		String path = null;
		
		switch(imageBean.getImageCategory()){
		case "VENDOR" : 
			path = getVendorPath(ftpCon);			
			break;
		case "CUSTOMER" : 
			path = getCustomerPath(ftpCon);
			break;
		case "MENU" :
			path = getMenuPath(ftpCon);
			break;
		case "MENUITEM" :
			path = getMenuPath(ftpCon);
			break;
		default :
			break;
		}
		
		return path;
	}
	
	
	private static String getVendorPath(FTPClient ftpCon){
		String path = null;		
		try{
			ftpCon.changeToParentDirectory();
			ftpCon.changeWorkingDirectory("vendor");
			String currentYear = "" + Utility.getCurrentYear();
			if(ftpCon.changeWorkingDirectory(currentYear)){
				path = ftpCon.printWorkingDirectory();
			}else{
				ftpCon.makeDirectory(currentYear);
				ftpCon.changeWorkingDirectory(currentYear);
				path = ftpCon.printWorkingDirectory();
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		return path;
	}
	
	private static String getCustomerPath(FTPClient ftpCon){
		String path = null;		
		try{
			ftpCon.changeToParentDirectory();
			ftpCon.changeWorkingDirectory("customer");
			String currentYear = "" + Utility.getCurrentYear();
			if(ftpCon.changeWorkingDirectory(currentYear)){
				path = ftpCon.printWorkingDirectory();
			}else{
				ftpCon.makeDirectory(currentYear);
				ftpCon.changeWorkingDirectory(currentYear);
				path = ftpCon.printWorkingDirectory();
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		return path;
	}
	
	private static String getMenuPath(FTPClient ftpCon){
		String path = null;		
		try{
			ftpCon.changeToParentDirectory();
			ftpCon.changeWorkingDirectory("menu");
			String currentYear = "" + Utility.getCurrentYear();
			if(ftpCon.changeWorkingDirectory(currentYear)){
				path = ftpCon.printWorkingDirectory();
			}else{
				ftpCon.makeDirectory(currentYear);
				ftpCon.changeWorkingDirectory(currentYear);
				path = ftpCon.printWorkingDirectory();
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		return path;
	}
	
	
	

	public static void compressImage(InputStream is, OutputStream os,
			String fileName) {

		try {

			float quality = 0.2f;

			// create a BufferedImage as the result of decoding the supplied
			// InputStream
			BufferedImage image = ImageIO.read(is);
			String ext = Utility.getExtension(fileName);

			Iterator<ImageWriter> writers = ImageIO
					.getImageWritersBySuffix(ext);
			if (!writers.hasNext())
				throw new IllegalStateException("No writers found");

			ImageWriter writer = (ImageWriter) writers.next();
			ImageOutputStream ios = ImageIO.createImageOutputStream(os);
			writer.setOutput(ios);

			ImageWriteParam param = writer.getDefaultWriteParam();

			// compress to a given quality
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(quality);

			writer.write(null, new IIOImage(image, null, null), param);

			ios.close();
			writer.dispose();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		File file = new File(
				"D:/3Code/workspaceWeb/userAccount/src/com/forage/user/DSC_0369.jpg");
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(file);

			System.out.println("Total file size to read (in bytes) : "
					+ fis.available());

			ImageBean imageBean = new ImageBean();
			imageBean.setCustomerId(BigDecimal.ONE);
			imageBean
					.setImageType(Utility.getExtension("D:/3Code/workspaceWeb/userAccount/src/com/forage/user/DSC_0369.jpg"));
			imageBean.setImageName(file.getName());
			imageBean.setImageCategory("CUSTOMER");

			putFile(imageBean, fis);

			fis.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}



}
