package com.forage.user;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.forage.bean.ImageBean;
import com.forage.exception.BadRequestException;
import com.forage.exception.NotFoundException;

public class ImageUtility {
	
	//  quality results
	//  Original size = 1.2 mb
	//  0.01 = 093 kb
	//	0.05 = 104 kb
	//	0.10 = 130 kb
	//  0.20 = 180 kb

	
	final static String FILE_SUFFIX_JPG = "jpg";
	
	final static int[] MDPI = new int[] {48,48};   		// 1 x
	final static int[] HDPI = new int[] {72,72};   		// 1.5 x
	final static int[] XHDPI = new int[] {96,96};  		// 2 x
	final static int[] XXHDPI = new int[] {144,144};   	// 3 x
	final static int[] XXXHDPI = new int[] {192,192};   // 4 x
	final static int[] i275 = new int[] {275,275};   // 275
	final static int[] i400 = new int[] {400,400};   // 400
	final static int[] i800 = new int[] {800,800};   // 800
	
	final static String IMAGE_MDPI = "mdpi";   // 1x
	final static String IMAGE_HDPI = "hdpi";   // 1.5x
	final static String IMAGE_XHDPI = "xhdpi";   // 2x
	final static String IMAGE_XXHDPI = "xxhdpi";   // 3x
	final static String IMAGE_XXXHDPI = "xxxhdpi";   // 4x
	final static String IMAGE_275 = "275";   // 275
	final static String IMAGE_400 = "400";   // 320
	final static String IMAGE_800 = "800";   // 800
	
	final static float MDPI_quality = 1.0f;
	final static float HDPI_quality = 1.0f;
	final static float XHDPI_quality = 0.95f;
	final static float XXHDPI_quality = 0.90f;
	final static float XXXHDPI_quality = 0.85f;
	final static float i275_quality = 0.85f;
	final static float i400_quality = 0.70f;
	final static float i800_quality = 0.60f;
	
	
	
	public static boolean uploadFile(ImageBean imageBean, InputStream is){
		ByteArrayOutputStream baos = null;
		imageBean.setImageName(imageNameTimestamped(imageBean));
		imageBean.setImageType(FILE_SUFFIX_JPG);
		imageBean.setImagePath(fetchUploadPath(imageBean));

		baos = getBAOS(is);
		is = new ByteArrayInputStream(baos.toByteArray()); 
		
		putMDPI(imageBean, new ByteArrayInputStream(baos.toByteArray()));
		putHDPI(imageBean, new ByteArrayInputStream(baos.toByteArray()));
		putXHDPI(imageBean, new ByteArrayInputStream(baos.toByteArray()));
		putXXHDPI(imageBean, new ByteArrayInputStream(baos.toByteArray()));
		putXXXHDPI(imageBean, new ByteArrayInputStream(baos.toByteArray()));
		puti275(imageBean, new ByteArrayInputStream(baos.toByteArray()));
		puti400(imageBean, new ByteArrayInputStream(baos.toByteArray()));
		puti800(imageBean, new ByteArrayInputStream(baos.toByteArray()));
		
		try {
			baos.flush();
			baos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	private static ByteArrayOutputStream getBAOS(InputStream input){
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[2048];
			int len;
			while ((len = input.read(buffer)) > -1) {
				baos.write(buffer, 0, len);
			}
			baos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos;
	}

	private static void putMDPI(ImageBean imageBean, InputStream is) {
		try {
			BufferedImage bi = resizeUsingImageIO(is, MDPI[0], MDPI[1]);
			InputStream imageStream = new ByteArrayInputStream(convertToJpgByteArray(bi));

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			compressImage(imageStream, os, imageBean.getImageName(), imageBean.getImageType(), MDPI_quality);
			imageStream = new ByteArrayInputStream(os.toByteArray());
			writeToLocalFileSystem(imageBean, IMAGE_MDPI, os);
			// amazonUpload(imageBean,imageStream);

			is.close();
			os.close();
			imageStream.close();
			bi.flush();
		} catch (IOException io) {

		}
	}
	
	
	private static void putHDPI(ImageBean imageBean, InputStream is) {
		try {
			BufferedImage bi = resizeUsingImageIO(is, HDPI[0], HDPI[1]);
			InputStream imageStream = new ByteArrayInputStream(convertToJpgByteArray(bi));
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			compressImage(imageStream, os, imageBean.getImageName(), imageBean.getImageType(), HDPI_quality);
			imageStream = new ByteArrayInputStream(os.toByteArray());
			writeToLocalFileSystem(imageBean, IMAGE_HDPI, os);
			amazonUpload(imageBean, IMAGE_HDPI, imageStream);
			
			is.close();
			os.close();
			imageStream.close();
			bi.flush();
			
		} catch (IOException io) {

		}
	}
	
	
	private static void putXHDPI(ImageBean imageBean, InputStream is) {
		try {
			BufferedImage bi = resizeUsingImageIO(is, XHDPI[0], XHDPI[1]);
			InputStream imageStream = new ByteArrayInputStream(convertToJpgByteArray(bi));
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			compressImage(imageStream, os, imageBean.getImageName(), imageBean.getImageType(), XHDPI_quality);
			imageStream = new ByteArrayInputStream(os.toByteArray());
			writeToLocalFileSystem(imageBean, IMAGE_XHDPI, os);
			amazonUpload(imageBean, IMAGE_XHDPI, imageStream);
			
			is.close();
			os.close();
			imageStream.close();
			bi.flush();
			
		} catch (IOException io) {

		}
	}
	
	private static void putXXHDPI(ImageBean imageBean, InputStream is) {
		try {
			BufferedImage bi = resizeUsingImageIO(is, XXHDPI[0], XXHDPI[1]);
			InputStream imageStream = new ByteArrayInputStream(convertToJpgByteArray(bi));
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			compressImage(imageStream, os, imageBean.getImageName(), imageBean.getImageType(), XXHDPI_quality);
			imageStream = new ByteArrayInputStream(os.toByteArray());
			writeToLocalFileSystem(imageBean, IMAGE_XXHDPI, os);
			amazonUpload(imageBean, IMAGE_XXHDPI, imageStream);
			
			is.close();
			os.close();
			imageStream.close();
			bi.flush();
		} catch (IOException io) {

		}
	}
	
	private static void putXXXHDPI(ImageBean imageBean, InputStream is) {
		try {
			BufferedImage bi = resizeUsingImageIO(is, XXXHDPI[0], XXXHDPI[1]);
			InputStream imageStream = new ByteArrayInputStream(convertToJpgByteArray(bi));
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			compressImage(imageStream, os, imageBean.getImageName(), imageBean.getImageType(), XXXHDPI_quality);
			imageStream = new ByteArrayInputStream(os.toByteArray());
			writeToLocalFileSystem(imageBean, IMAGE_XXXHDPI, os);
			amazonUpload(imageBean, IMAGE_XXXHDPI, imageStream);
			
			is.close();
			os.close();
			imageStream.close();
			bi.flush();
		} catch (IOException io) {

		}
	}
	
	
	private static void puti275(ImageBean imageBean, InputStream is) {
		try {
			BufferedImage bi = resizeUsingImageIO(is, i275[0], i275[1]);
			InputStream imageStream = new ByteArrayInputStream(convertToJpgByteArray(bi));
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			compressImage(imageStream, os, imageBean.getImageName(), imageBean.getImageType(), i275_quality);
			imageStream = new ByteArrayInputStream(os.toByteArray());
			writeToLocalFileSystem(imageBean, IMAGE_275, os);
			amazonUpload(imageBean, IMAGE_275, imageStream);
			
			is.close();
			os.close();
			imageStream.close();
			bi.flush();
			
		} catch (IOException io) {

		}
	}
	
	private static void puti400(ImageBean imageBean, InputStream is) {
		try {
			BufferedImage bi = resizeUsingImageIO(is, i400[0], i400[1]);
			InputStream imageStream = new ByteArrayInputStream(convertToJpgByteArray(bi));
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			compressImage(imageStream, os, imageBean.getImageName(), imageBean.getImageType(), i400_quality);
			imageStream = new ByteArrayInputStream(os.toByteArray());
			writeToLocalFileSystem(imageBean, IMAGE_400, os);
			amazonUpload(imageBean, IMAGE_400, imageStream);
			
			is.close();
			os.close();
			imageStream.close();
			bi.flush();
			
		} catch (IOException io) {

		}
	}
	
	
	private static void puti800(ImageBean imageBean, InputStream is) {
		try {
			BufferedImage bi = resizeUsingImageIO(is, i800[0], i800[1]);
			InputStream imageStream = new ByteArrayInputStream(convertToJpgByteArray(bi));
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			compressImage(imageStream, os, imageBean.getImageName(), imageBean.getImageType(), i800_quality);
			imageStream = new ByteArrayInputStream(os.toByteArray());
			writeToLocalFileSystem(imageBean, IMAGE_800, os);
			amazonUpload(imageBean, IMAGE_800, imageStream);
			
			is.close();
			os.close();
			imageStream.close();
			bi.flush();
			
		} catch (IOException io) {

		}
	}
	
	

	
	
	private static byte[] convertToJpgByteArray(BufferedImage bi) throws IOException
	{
	    final ByteArrayOutputStream output = new ByteArrayOutputStream();
	    byte[] imageData = null;
	    //Convert JPEG and then a byte array
	    if (ImageIO.write(bi, FILE_SUFFIX_JPG, new DataOutputStream(output)))
	    {
	        imageData = output.toByteArray();
	    }
	    return imageData;
	}
	
	private static BufferedImage resizeUsingImageIO(InputStream is, int width, int height)
    {
		BufferedImage srcImage = null;
		BufferedImage bi = null;
		try {
			srcImage = ImageIO.read(is);
			bi = Scalr.resize(srcImage, Method.QUALITY, Mode.AUTOMATIC, width, height);
		} catch (IOException ex) {

		}       
        return bi;
    }
	
	
//	private static BufferedImage resizeUsingImageIO(InputStream is, int size)
//    {
//		Image srcImage = null;
//		try {
//			srcImage = ImageIO.read(is);
//		} catch (IOException ex) {
//
//		}
//
//		int w = srcImage.getWidth(null);
//		int h = srcImage.getHeight(null);
//
//		// Determine the scaling required to get desired result.
//		float scaleW = (float) size / (float) w;
//		float scaleH = (float) size / (float) h;
//		
//        //Create an image buffer in which to paint on, create as an opaque Rgb type image, it doesn't matter what type
//        //the original image is we want to convert to the best type for displaying on screen regardless
//        BufferedImage bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
//
//        // Set the scale.
//        AffineTransform tx = new AffineTransform();
//        tx.scale(scaleW, scaleH);
//
//        // Paint image.
//        Graphics2D g2d = bi.createGraphics();
//        g2d.setColor(Color.WHITE);
//        g2d.fillRect(0, 0, size, size);
//        g2d.setComposite(AlphaComposite.SrcOver);
//        g2d.drawImage(srcImage, tx, null);
//        g2d.dispose();
//        return bi;
//    }
	
	private static String imageNameTimestamped(ImageBean image){
		if(image.getImageName() == null)
			return null;
		
		String imageName = image.getImageName();
		String underScore = "_";
		String endTimeStamp = Utility.getDateFormatted() + underScore + Utility.getTimeFormatted();

		if (image.getCustomerId() != null) {
			imageName = imageName + underScore + image.getCustomerId().toString() + underScore + endTimeStamp;
		} else if (image.getVendorId() != null) {
			imageName = imageName + underScore + image.getVendorId().toString() + underScore + endTimeStamp;
		} else if (image.getMenuId() != null) {
			imageName = imageName + underScore + image.getVendorId().toString() + underScore + endTimeStamp;
		} else {
			throw new NotFoundException("UtilityFtp.imageNameTimestamped","Image has no customer/vendor/menu associated to it");
		}
		return imageName;
	}
	
	private static String fetchUploadPath(ImageBean imageBean){
		
		String path = null;
		String currentYear = "" + Utility.getCurrentYear();
		String currentMonth = "" + Utility.getCurrentMonth();
		
		switch(imageBean.getImageCategory()){
		case "VENDOR" : 
			path = "vendor";			
			break;
		case "CUSTOMER" : 
			path = "customer";
			break;
		case "MENU" :
			path = "menu";
			break;
		case "MENUITEM" :
			path = "menu";
			break;
		default :
			throw new BadRequestException("UtilityFtp.fetchUploadPath", "No Image category provided.");
		}
		
		path = path + "/" + currentYear + "/" + currentMonth;
		
		return path;
	}
	

	private static void compressImage(InputStream is, OutputStream os, String fileName, String imageType, float quality) {

		try {

			// create a BufferedImage as the result of decoding the supplied
			// InputStream
			BufferedImage image = ImageIO.read(is);

			Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix(imageType);
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
	
	private static void writeToLocalFileSystem(ImageBean image, String imageClass, ByteArrayOutputStream os){
		
//		String localFilePath = "C:/Users/asharma5/Downloads/test/out";
//		localFilePath = localFilePath + "/" + image.getImagePath() + "/" + image.getImageName() + "_" + imageClass + "." + image.getImageType();
//		System.out.println("file path : " + localFilePath);
//		FileOutputStream fop = null;
//		File fileout = new File(localFilePath);
//		try{
//			if (!fileout.exists()) {
//				fileout.createNewFile();
//			}
//			fop = new FileOutputStream(fileout);
//			fop.write(os.toByteArray());
//			fop.close();
//		}catch(IOException ecc){
//			
//		}		
	}
	
	private static boolean amazonUpload(ImageBean image, String imageClass, InputStream inputStream){
		boolean uploadFlag = false;
		String accessKey = "AKIAJTLJHLL3Q4ALQSKQ";
		String securityKey = "8FJWc7uA5EX+MK3590eaGFUPHAXFvbjUkYJ7x3eH";		
		String bucketName = "foragebuds";
//		String keyName = "test/2015/04/Test.jpg";
		String keyName = image.getImagePath() + "/" + image.getImageName() + "_" + imageClass + "." + image.getImageType();
		
		AWSCredentials credentials=new BasicAWSCredentials(accessKey,securityKey);
		AmazonS3 s3client = new AmazonS3Client(credentials);
        try {
            PutObjectResult result = s3client.putObject(new PutObjectRequest(bucketName, keyName, inputStream, null));
            uploadFlag = true;
            System.out.println("Etag:" + result.getETag() + "-->" + result);
            System.out.println("File Uploaded");
         } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
            		"means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
            		"means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
        return uploadFlag;
	}

	// for image upload
	public static void main(String[] args) {

//		File file = new File("D:/3Code/workspaceWeb/userAccount/src/com/forage/user/DSC_0369.jpg");
		
//		File file = new File("C:/Users/asharma5/Downloads/test/in/DSC_0368.jpg");
//		File file = new File("C:/Users/asharma5/Downloads/test/in/D4.jpg");
		File file = new File("C:/Users/asharma5/Downloads/test/in/DSC_0369.png");
		
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(file);

			System.out.println("Total file size to read (in bytes) : " + fis.available());

			ImageBean imageBean = new ImageBean();
			imageBean.setCustomerId(BigDecimal.ONE);
			imageBean.setImageType(Utility.getFileExtension(file.getName()));
			imageBean.setImageName(Utility.getFileNameWithoutExtension(file.getName()));
			imageBean.setImageSize(new BigDecimal(file.length()));
			imageBean.setImageCategory("CUSTOMER");

			
			uploadFile(imageBean, fis);
//			ImageDAO imageDAO = new ImageDAO();
//			imageDAO.createImage(imageBean);
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
	
	// for amazon access
	
//	public static void main(String[] args) throws IOException {
//		
//		String accessKey = "AKIAJTLJHLL3Q4ALQSKQ";
//		String securityKey = "8FJWc7uA5EX+MK3590eaGFUPHAXFvbjUkYJ7x3eH";
//		AWSCredentials credentials=new BasicAWSCredentials(accessKey,securityKey);
//		
//		ClientConfiguration clientConfig = new ClientConfiguration();
////		clientConfig.setProtocol(Protocol.HTTP);
//		
////		AWSCredentials propCred = new PropertiesCredentials(UtilityFtp.class.getResourceAsStream("AwsCredentials.properties"));
////		System.out.println("access Key : " + propCred.getAWSAccessKeyId());
////		System.out.println("secret Key : " + propCred.getAWSSecretKey());
//		
//		
//		AmazonS3 conn = new AmazonS3Client(credentials, clientConfig);
////		conn.setEndpoint("endpoint.com");
//		conn.setEndpoint("http://foragebuds.s3-website-us-east-1.amazonaws.com");
////		conn.setEndpoint("foragebuds");
//		
//		String existingBucketName = "http://foragebuds.s3-website-us-east-1.amazonaws.com";
//		String keyName = "DSC_0368.jpg";
//
//		String filePath = "D://2Ajay//5misc//MobilePics//DSC_0368.jpg";
//		String amazonFileUploadLocationOriginal = existingBucketName + "/";
//		AmazonS3 s3Client = null;
//		try{
//			System.out.println("before making a connnection");
//			s3Client = new AmazonS3Client(credentials);
////			s3Client = new AmazonS3Client(propCred);
//			System.out.println("after making a connnection");	
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
//			
//		FileInputStream stream = new FileInputStream(filePath);
//		ObjectMetadata objectMetadata = new ObjectMetadata();
//		System.out.println("configuring put object");
//		PutObjectRequest putObjectRequest = new PutObjectRequest(amazonFileUploadLocationOriginal, keyName, stream, objectMetadata);
//		System.out.println("trying to put through conn");
//		PutObjectResult result1 = conn.putObject(putObjectRequest);
//		System.out.println("Etag:" + result1.getETag() + "-->" + result1);
//		
//		PutObjectResult result = s3Client.putObject(putObjectRequest);
//		stream.close();
//		System.out.println("Etag:" + result.getETag() + "-->" + result);
//	}
	
	
	
	
	// Amazon get list of files and it worked
	
//public static void main(String[] args) throws IOException {
//		
//		String accessKey = "AKIAJTLJHLL3Q4ALQSKQ";
//		String securityKey = "8FJWc7uA5EX+MK3590eaGFUPHAXFvbjUkYJ7x3eH";
//		AWSCredentials credentials=new BasicAWSCredentials(accessKey,securityKey);
//		String bucketName = "foragebuds";
//		
//		
//		AmazonS3 s3client = new AmazonS3Client(credentials);
//        try {
//            System.out.println("Listing objects");
//   
//            ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
//                .withBucketName(bucketName);
//            ObjectListing objectListing;            
//            do {
//                objectListing = s3client.listObjects(listObjectsRequest);
//                System.out.println("Count : " + objectListing.getObjectSummaries().size());
//                
//                for (S3ObjectSummary objectSummary : 
//                	objectListing.getObjectSummaries()) {
//                    System.out.println(" - " + objectSummary.getKey() + "  " +
//                            "(size = " + objectSummary.getSize() + 
//                            ")");
//                }
//                listObjectsRequest.setMarker(objectListing.getNextMarker());
//            } while (objectListing.isTruncated());
//            System.out.println("Listing objects Done");
//         } catch (AmazonServiceException ase) {
//            System.out.println("Caught an AmazonServiceException, " +
//            		"which means your request made it " +
//                    "to Amazon S3, but was rejected with an error response " +
//                    "for some reason.");
//            System.out.println("Error Message:    " + ase.getMessage());
//            System.out.println("HTTP Status Code: " + ase.getStatusCode());
//            System.out.println("AWS Error Code:   " + ase.getErrorCode());
//            System.out.println("Error Type:       " + ase.getErrorType());
//            System.out.println("Request ID:       " + ase.getRequestId());
//            ase.printStackTrace();
//        } catch (AmazonClientException ace) {
//            System.out.println("Caught an AmazonClientException, " +
//            		"which means the client encountered " +
//                    "an internal error while trying to communicate" +
//                    " with S3, " +
//                    "such as not being able to access the network.");
//            System.out.println("Error Message: " + ace.getMessage());
//        }
//    }

// Amazon get list of files and it worked
	
	
	// Amazon file upload start and it worked
	
//	public static void main(String[] args) throws IOException {
//
//		String accessKey = "AKIAJTLJHLL3Q4ALQSKQ";
//		String securityKey = "8FJWc7uA5EX+MK3590eaGFUPHAXFvbjUkYJ7x3eH";
//		AWSCredentials credentials=new BasicAWSCredentials(accessKey,securityKey);
//		String bucketName = "foragebuds";
//		AmazonS3 s3client = new AmazonS3Client(credentials);
//		
//		String keyName = "test/2015/04/Test.jpg";
//		String filePath = "D://2Ajay//5misc//MobilePics//Test.jpg";
//		
//        try {
//            System.out.println("Uploading a new object to S3 from a file\n");
//            File file = new File(filePath);
//            s3client.putObject(new PutObjectRequest(bucketName, keyName, file));
//            System.out.println("File Uploaded");
//         } catch (AmazonServiceException ase) {
//            System.out.println("Caught an AmazonServiceException, which " +
//            		"means your request made it " +
//                    "to Amazon S3, but was rejected with an error response" +
//                    " for some reason.");
//            System.out.println("Error Message:    " + ase.getMessage());
//            System.out.println("HTTP Status Code: " + ase.getStatusCode());
//            System.out.println("AWS Error Code:   " + ase.getErrorCode());
//            System.out.println("Error Type:       " + ase.getErrorType());
//            System.out.println("Request ID:       " + ase.getRequestId());
//        } catch (AmazonClientException ace) {
//            System.out.println("Caught an AmazonClientException, which " +
//            		"means the client encountered " +
//                    "an internal error while trying to " +
//                    "communicate with S3, " +
//                    "such as not being able to access the network.");
//            System.out.println("Error Message: " + ace.getMessage());
//        }
//	}
	
	// Amazon file upload ends
	
	
}
