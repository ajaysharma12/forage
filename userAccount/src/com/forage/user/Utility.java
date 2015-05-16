package com.forage.user;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.io.FilenameUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
 
public class Utility {
    /**
     * Null check Method
     * 
     * @param txt
     * @return
     */
    public static boolean isNotNull(String txt) {
        // System.out.println("Inside isNotNull");
        return txt != null && txt.trim().length() >= 0 ? true : false;
    }
 
    /**
     * Method to construct JSON
     * 
     * @param tag
     * @param status
     * @return
     */
    public static String constructJSON(String tag, boolean status) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", tag);
            obj.put("status", new Boolean(status));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }
 
    /**
     * Method to construct JSON with Error Msg
     * 
     * @param tag
     * @param status
     * @param err_msg
     * @return
     */
    public static String constructJSON(String tag, boolean status, String err_msg) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", tag);
            obj.put("status", new Boolean(status));
            obj.put("errsor_msg", err_msg);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }
    
    
    /**
     * Method to construct status JSON
     * 
     * @param action
     * @param status
     * @return
     */
    public static String constructActionStatus(String action, String status) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("action", action);
            obj.put("status", status);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }
    
    
  
    public  final static String getDateFormatted(  )   {
          DateFormat df = new SimpleDateFormat( "yyyyMMdd" ) ;
          df.setTimeZone( TimeZone.getTimeZone( "IST" )  ) ;
          return ( df.format( new Date(  )  )  ) ;
      }
    public  final static String getTimeFormatted(  )   {
          DateFormat df = new SimpleDateFormat( "hhmmss" ) ;
          df.setTimeZone ( TimeZone.getTimeZone ( "IST" )  ) ;
          return ( df.format( new Date(  )  )  ) ;
      }
    
    public static final String getFileExtension(final String filename) {
		if (filename == null)
			return null;
		final String afterLastSlash = filename.substring(filename.lastIndexOf('/') + 1);
		final int afterLastBackslash = afterLastSlash.lastIndexOf('\\') + 1;
		final int dotIndex = afterLastSlash.indexOf('.', afterLastBackslash);
		return (dotIndex == -1) ? "" : afterLastSlash.substring(dotIndex + 1);
	}
    
    public static final String getFileNameWithoutExtension(final String filename) {
		if (filename == null)
			return null;
		String fileNameWithOutExt = FilenameUtils.removeExtension(filename);
		return fileNameWithOutExt;
	}
    
    
    public static int getCurrentYear() {
    	int year = Calendar.getInstance().get(Calendar.YEAR);
        return year;
    }
    
    public static int getCurrentMonth(){
    	int month = Calendar.getInstance().get(Calendar.MONTH);
    	return month+1;
    }
    
    public static java.sql.Date convertFromJAVADateToSQLDate(java.util.Date javaDate) {
        java.sql.Date sqlDate = null;
        if (javaDate != null) {
            sqlDate = new java.sql.Date(javaDate.getTime());
        }
        return sqlDate;
    }
    
	public Date getCurrentTimeStamp(){
		return new java.util.Date();
	}
    
}
