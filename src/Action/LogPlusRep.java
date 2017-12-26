package Action;

//Import
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.net.util.Base64;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import utils.MethodesUtiles;
import utils.logGear;
import utils.variables;



public class LogPlusRep extends Thread
	{
	/************
	 * Variables
	 ************/
	String ObjetXML;
	String ObjetEnvoyee;
	String ObjetXMLReponse;
	String IPCCM;
	String login;
	String pass;
	String mac;
	String userID;
	String profile;
	boolean LoginLogout;
	int timeOut;
	
	String ligne;
	ArrayList<String[]> status;
	int index;
	
	//Traitement XML
	Document document;
	
	//Connexion Socket
	Socket s;
	InetAddress addr;
	BufferedReader in;
	BufferedWriter  out;
	
	//Connexion http
	URL myUrl;
	HttpURLConnection connexion;
	HttpsURLConnection connexionS;
	
	/***************
	 * Constructeur
	 ***************/
	public LogPlusRep(String mac, String userID, String profile, String LogLogout, int index)
		{
		this.IPCCM = variables.getIPCCM();
		this.login = variables.getLogin();
		this.pass = variables.getPass();
		this.mac = mac;
		this.userID = userID;
		this.profile = profile;
		this.status = variables.getStatus();
		this.index = index;
		this.timeOut = Integer.parseInt(MethodesUtiles.getTargetOption("timeout"));
		
		logGear.writeLog("/\\Lancement du Thread/\\"+(index+1),this,72);
		
		if(LogLogout.compareTo("true")==0)
			{
			this.LoginLogout = true;
			logGear.writeLog("Thread "+(index+1)+" Requete de login",this,73);
			}
		else
			{
			this.LoginLogout = false;
			logGear.writeLog("Thread "+(index+1)+" Requete de logout",this,78);
			}
		
		start();
		}
	
	public void run()
		{
		CreateXmlObject();
		CreateObject();
		
		ObjetXMLReponse = "";
		
		logGear.writeLog("Thread "+(index+1)+" - UserID : "+userID+" - Mac : "+mac,this,97);
		
		variables.getLogger().debug("Thread "+(index+1)+" Message envoyé au serveur : "+ObjetEnvoyee);
		
		if(variables.getRequestType().compareTo("HTTPS") == 0)
			{
			variables.getLogger().info("Thread "+(index+1)+" Connexion HTTPS");
			connexionURLS();
			}
		else
			{
			variables.getLogger().info("Thread "+(index+1)+" Connexion HTTP");
			connexionURL();
			}
			
		
		variables.getLogger().debug("Thread "+(index+1)+" Reponse du serveur : "+ObjetXMLReponse);
		logGear.writeLog("/\\Fin du Thread/\\"+(index+1),this,102);
		}
	
	/***************************************************
	 * Méthode qui envoi la requete http au serveur et 
	 * réceptionne la réponse
	 ***************************************************/
	public void connexionURL()
		{
		try
			{
			myUrl = new URL("http://"+IPCCM+":8080/emservice/EMServiceServlet");
			connexion = null;
			connexion = (HttpURLConnection)myUrl.openConnection();
			connexion.setConnectTimeout(timeOut);
			connexion.setRequestMethod("POST");
			connexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connexion.setRequestProperty("content-Length", Integer.toString(ObjetXML.length()));
			connexion.setUseCaches(false);
			connexion.setDoInput(true);
			connexion.setDoOutput(true);
			
			out = new BufferedWriter(new OutputStreamWriter(connexion.getOutputStream()));
			
			//Envoi de la requete
			out.write(ObjetEnvoyee);
		    out.flush();
		    logGear.writeLog("Thread "+(index+1)+" Requete envoyee au serveur",this,120);
			
		    //Accueil de la réponse
		    in = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
		    answerAnalyser();
			}
		catch(Exception exc)
			{
			String[] str = new String[]{"error","Serveur injoignable"};
			status.set(index, str);
			logGear.writeLog("Thread "+(index+1)+" ERROR : Connexion avec le serveur : "+exc.getMessage(),this,150);
			exc.printStackTrace();
			}
		finally
			{
			try
				{
				out.close();
				in.close();
				connexion.disconnect();
				logGear.writeLog("Thread "+(index+1)+" Connexion terminée correctement",this,160);
				}
			catch(Exception e)
				{
				logGear.writeLog("Thread "+(index+1)+" ERROR : Connexion terminée Incorrectement",this,164);
				}
			}
		}
	
	/***************************************************
	 * Méthode qui envoi la requete https au serveur et 
	 * réceptionne la réponse
	 ***************************************************/
	public void connexionURLS()
		{
		try
			{
			init();
			
			String cred = variables.getLogin()+":"+variables.getPass();
	        cred = new String(Base64.encodeBase64(cred.getBytes()));
	        
	        myUrl = new URL("https://"+IPCCM+":8443/emservice/EMServiceServlet");
	        connexionS = (HttpsURLConnection)myUrl.openConnection();
	        connexionS.setRequestProperty("Authorization", "Basic "+cred);
	        connexionS.setRequestMethod("POST");
	        connexionS.setConnectTimeout(timeOut);
	        connexionS.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connexionS.setRequestProperty("content-Length", Integer.toString(ObjetXML.length()));
	        connexionS.setDoOutput(true);
			
			out = new BufferedWriter(new OutputStreamWriter(connexionS.getOutputStream(),"UTF-8"));
			
			//Envoi de la requete
			out.write(ObjetEnvoyee);
		    out.flush();
		    logGear.writeLog("Thread "+(index+1)+" Requete envoyee au serveur",this,120);
			
		    //Accueil de la réponse
		    in = new BufferedReader(new InputStreamReader(connexionS.getInputStream(),"UTF-8"));
		    answerAnalyser();
			}
		catch(Exception exc)
			{
			String[] str = new String[]{"error","Serveur injoignable"};
			status.set(index, str);
			logGear.writeLog("Thread "+(index+1)+" ERROR : Connexion avec le serveur : "+exc.getMessage(),this,150);
			exc.printStackTrace();
			}
		finally
			{
			try
				{
				out.close();
				in.close();
				connexionS.disconnect();
				logGear.writeLog("Thread "+(index+1)+" Connexion terminée correctement",this,160);
				}
			catch(Exception e)
				{
				logGear.writeLog("Thread "+(index+1)+" ERROR : Connexion terminée Incorrectement",this,164);
				}
			}
		}
	
	/**************************************************************
	 * Si le Booleen est à "true" on logue le profile
	 * S'il est à "false" on le délogue
	 **************************************************************/
	public void CreateXmlObject()
		{
		ObjetXML = null;
		
		ObjetXML = "\n";
		ObjetXML += "\n";
		ObjetXML += "<request>\n";
		ObjetXML += "<appInfo>\n";
		ObjetXML += "<appID>"+login+"</appID>\n";
		ObjetXML += "<appCertificate><![CDATA["+pass+"]]></appCertificate>\n";
		ObjetXML +=	"</appInfo>\n";
		
		if(LoginLogout)
			{
			ObjetXML +=	"<login>\n";
			ObjetXML +=	"<deviceName>SEP"+mac+"</deviceName>\n";
			ObjetXML +=	"<userID>"+userID+"</userID>\n";
			if(!Pattern.matches("^$", profile))
				{
				ObjetXML +=	"<deviceProfile>"+profile+"</deviceProfile>\n";
				}
			ObjetXML +=	"</login>\n";
			}
		else
			{
			ObjetXML +=	"<logout>\n";
			ObjetXML +=	"<deviceName>SEP"+mac+"</deviceName>\n";
			ObjetXML +=	"</logout>\n";
			}
		ObjetXML += "</request>\n";
		}
	
	/********************************************
	 * Methode qui créer l'objet à envoyer au CCM
	 ********************************************/
	public void CreateObject()
		{
		ObjetEnvoyee = null;
		
		ObjetEnvoyee = "appid="+login;
		ObjetEnvoyee += "&appPassword="+pass;
		ObjetEnvoyee += "&devicename=SEP"+mac;
		ObjetEnvoyee += "&userid="+userID;
		ObjetEnvoyee += "&deviceprofile="+profile;
		ObjetEnvoyee += "&xml=";
		
		ObjetXML = convertEncodeType(ObjetXML);
		
		ObjetEnvoyee += ObjetXML;
		}
	
	/*************************************************************
	 * Methode qui converti les caractères spéciaux pour obtenir
	 * un semblant de standardisation ASCII
	 *************************************************************/
	public String convertEncodeType(String textToConvert)
		{
		textToConvert = textToConvert.replace("\n", "%0A");
		textToConvert = textToConvert.replace("<", "%3C");
		textToConvert = textToConvert.replace(">", "%3E");
		textToConvert = textToConvert.replace("/", "%2F");
		textToConvert = textToConvert.replace("[", "%5B");
		textToConvert = textToConvert.replace("]", "%5D");
		textToConvert = textToConvert.replace("!", "%21");
		
		return textToConvert;
		}
	
	/******************************************************************************
	 * Cette méthode lit la source XML passé en paramètre.
	 * Elle transforme le contenu du fichier en objet Document grâce à la
	 * technique SAX
	 ******************************************************************************/
	public String parserXML(String sourceXML) throws Exception
		{
		SAXBuilder sxb = new SAXBuilder();
		document = sxb.build(new InputSource(new StringReader(sourceXML)));
		
		return document.getRootElement().getContent(0).getValue();
		}
	
	private void init()
		{
        try
        	{
            X509TrustManager xtm = new utils.HttpsTrustManager();
            TrustManager[] mytm = { xtm };
            SSLContext ctx = SSLContext.getInstance("SSL");
            ctx.init(null, mytm, null);

            HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());

            //Utilité à vérifier
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier()
            	{
                public boolean verify(String hostname, SSLSession session)
                	{
                    return true;
                	}
            	}
            );
            
        	connexionS = null;
        	connexionS.setDefaultSSLSocketFactory(ctx.getSocketFactory());
        	}
        catch (Exception e)
        	{
            e.printStackTrace();
        	}
		}
	
	private void answerAnalyser() throws Exception
		{
		while ((ligne = in.readLine()) != null)
        	{
            ObjetXMLReponse += ligne;
         	}
	    
	    if(Pattern.matches(".*<success/>.*", ObjetXMLReponse))
	    	{
	    	String[] str = new String[]{"success","No error"};
	    	status.set(index, str);
	    	}
	    else
	    	{
	    	if(Pattern.matches(".*<error.*", ObjetXMLReponse))
	    		{
	    		String[] str = new String[]{"error",parserXML(ObjetXMLReponse)};
	    		status.set(index, str);
	    		}
	    	else
	    		{
	    		String[] str = new String[]{"error","Une reponse a ete obtenu mais pas du serveur CCM"};
	    		status.set(index, str);
	    		}
	    	}
		}
	
	/*2013*//*RATEL Alexandre 8)*/
	}
