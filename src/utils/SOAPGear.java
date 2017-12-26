package utils;

//Imports
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;


/**********************************************
 * This class have to manage the soap request
 **********************************************/
public class SOAPGear
	{
	/************
	 * Variables
	 ************/
    private SOAPConnection con; //Defines the connection to AXL for the test driver
    private String port; //"8443"
    private String host; //"localhost"
    private String username; //null
    private String password; //null
	
	/***************
	 * Constructeur
	 ***************/
	public SOAPGear(String port, String host, String username, String password)
		{
		this.port = port;
		this.host = host;
		this.username = username;
		this.password = password;
		init();
		}
	
	private void init()
		{
        try
        	{
            X509TrustManager xtm = new HttpsTrustManager();
            TrustManager[] mytm = { xtm };
            SSLContext ctx = SSLContext.getInstance("SSL");
            ctx.init(null, mytm, null);
            SSLSocketFactory sf = ctx.getSocketFactory();
            
            SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
            con = scf.createConnection();

            HttpsURLConnection.setDefaultSSLSocketFactory(sf);
            //Utilité à vérifier
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier()
            	{
                public boolean verify(String hostname, SSLSession session)
                	{
                    return true;
                	}
            	}
            );
        	}
        catch (Exception e)
        	{
            e.printStackTrace();
        	}
		}
    
    
    public String getUrlEndpoint()
    	{
        return new String("https://" + username + ":" + password + "@" + host + ":" + port + "/axl/");
    	}
	
    /*******************************************************************
     * This method provides the ability to send a specific SOAPMessage
     *******************************************************************/
    public SOAPMessage sendMessage(SOAPMessage requestMessage) throws Exception
    	{
        SOAPMessage reply = null;
        String soapRequest;
        String soapAnswer;
        try
        	{
        	variables.getLogger().debug("*****************************************************************************");
        	variables.getLogger().debug("Sending message...");
        	variables.getLogger().debug("---------------------");
        	soapRequest = utils.convertSOAPToString.convert(requestMessage);
        	variables.getLogger().debug(soapRequest);
            //requestMessage.writeTo(System.out);
            variables.getLogger().debug("\n---------------------");

            reply = con.call(requestMessage, getUrlEndpoint());

            if (reply != null)
            	{
                //Check if reply includes soap fault
                SOAPPart replySP = reply.getSOAPPart();
                SOAPEnvelope replySE = replySP.getEnvelope();
                SOAPBody replySB = replySE.getBody();

                if (replySB.hasFault())
                	{
                	variables.getLogger().error("AXL ERROR: " + replySB.getFault().getFaultString());
                	}
                else 
                	{
                	variables.getLogger().info("AXL : Positive response received.");
                	}
                variables.getLogger().debug("---------------------");
                //reply.writeTo(System.out);
                soapAnswer = utils.convertSOAPToString.convert(reply);
                variables.getLogger().debug("AXL reply convert to string : "+soapAnswer);
                variables.getLogger().debug("\n---------------------");
            	return reply;
            	}
            else
            	{
            	variables.getLogger().debug("AXL : No reply was received!");
            	variables.getLogger().debug("---------------------");
            	return null;
            	}
        	}
        catch (Exception e)
        	{
            throw e;
        	}
    	}
    
    
    public SOAPMessage execute(SOAPMessage soapMessage) throws SOAPException
    	{
        try
        	{
        	return sendMessage(soapMessage);
        	}
        catch (Exception e)
        	{
            throw new SOAPException("Serveur AXL injoigniable");
        	}
    	}
    
    public void closeCon()
    	{
    	try
    		{
    		con.close();
    		variables.getLogger().info("Fermeture de la connexion AXL");
    		}
    	catch(Exception e)
    		{
    		e.printStackTrace();
    		e.getMessage();
    		}
    	}
	
	/*2012*//*RATEL Alexandre 8)*/
	}
