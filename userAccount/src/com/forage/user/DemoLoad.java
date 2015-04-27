package com.forage.user;

import java.io.File;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
  
public class DemoLoad
{
    public static void main(String args[])
    {
    	System.out.println("hello");
    	DemoLoad fileUpload = new DemoLoad () ;
        File file = new File("D:/2Ajay/5misc/kanujCamera/112___02/IMG_1314.JPG") ;
        //Upload the file
        try {
			fileUpload.executeMultiPartRequest("http://localhost:8080/userAccount/image/uploadpart1",
			        file, file.getName(), "File Uploaded :: IMG_1314.JPG") ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } 
     
    public void executeMultiPartRequest(String urlString, File file, String fileName, String fileDescription) throws Exception
    {
        HttpClient client = new DefaultHttpClient() ;
        HttpPost postRequest = new HttpPost (urlString) ;
        try
        {
            //Set various attributes
            MultipartEntity multiPartEntity = new MultipartEntity () ;
            multiPartEntity.addPart("file", new StringBody(fileDescription != null ? fileDescription : "")) ;
            multiPartEntity.addPart("file", new StringBody(fileName != null ? fileName : file.getName())) ;
  
            FileBody fileBody = new FileBody(file, "application/octect-stream") ;
            //Prepare payload
            multiPartEntity.addPart("file", fileBody) ;
  
            //Set to request body
            postRequest.setEntity(multiPartEntity) ;
             
            //Send request
            HttpResponse response = client.execute(postRequest) ;
             
            //Verify response if any
            if (response != null)
            {
                System.out.println(response.getStatusLine().getStatusCode());
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace() ;
        }
    }
}

