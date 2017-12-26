
package utils;

import java.io.ByteArrayOutputStream;

import javax.xml.soap.SOAPMessage;

/**********************************************
 * Class used to convert SOAPMessage to String
 **********************************************/
public class convertSOAPToString
	{
	/**
	 * Variables
	 */
	
	/************************************************************************
     * This method provides the ability to convert a SOAPMessage to a String
     ************************************************************************/
    public static String convert(SOAPMessage inMessage) throws Exception
    	{
    	ByteArrayOutputStream out = new ByteArrayOutputStream(); 
        inMessage.writeTo(out); 
        String strMsg = new String(out.toByteArray());
        return strMsg;
    	}
	
	/*2012*//*RATEL Alexandre 8)*/
	}
