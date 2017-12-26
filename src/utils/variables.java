
package utils;

import java.util.ArrayList;
import org.apache.log4j.Logger;

import Fenetre.MainWindow;

/*********************************************
 * Classe contenant les variables statiques
 *********************************************/
public class variables
	{
	/**
	 * Listes des variables statiques
	 */
	private static String nomProg;
	private static String version;
	private static Logger logger;
	private static ArrayList<String[][]> tabOptionGlobal;
	private static String ConfigFileName;
	private static String IPCCM;
	private static String Login;
	private static String axlLogin;
	private static String Pass;
	private static String axlPass;
	private static ArrayList<String[]> status;
	private static ArrayList<String[]> ListeDevice;
	private static MainWindow MainW;
	private static String resultFileName;
	private static ArrayList<String> ListeErreur;
	private static String requestType;
	private static String axlPort;
	private static String versionCCM;
	
	/**
	 * Contructeur
	 */
	public variables()
		{
		ConfigFileName = new String("configFile.xml");
		resultFileName = new String("resultFile");
		setTabOptionGlobal(false);
		axlPort = "8443";
		versionCCM = "8";
		}
	
	/****
	 * Getters and Setters
	 */

	public static String getNomProg()
		{
		return nomProg;
		}

	public static void setNomProg(String nomProg)
		{
		variables.nomProg = nomProg;
		}

	public static String getVersion()
		{
		return version;
		}

	public static void setVersion(String version)
		{
		variables.version = version;
		}

	public static Logger getLogger()
		{
		return logger;
		}

	public static void setLogger(Logger logger)
		{
		variables.logger = logger;
		}

	public static ArrayList<String[][]> getTabOptionGlobal()
		{
		return tabOptionGlobal;
		}

	public static void setTabOptionGlobal(boolean override)
		{
		tabOptionGlobal = MethodesUtiles.getOptionValue(override);
		if(!override)
			{
			//On écrit le fichier
			MethodesUtiles.setOptionValue();
			}
		}

	public static String getConfigFileName()
		{
		return ConfigFileName;
		}

	public static void setConfigFileName(String configFileName)
		{
		ConfigFileName = configFileName;
		}

	public static String getIPCCM()
		{
		return IPCCM;
		}

	public static void setIPCCM(String iPCCM)
		{
		IPCCM = iPCCM;
		}

	public static String getLogin()
		{
		return Login;
		}

	public static void setLogin(String login)
		{
		Login = login;
		}

	public static String getPass()
		{
		return Pass;
		}

	public static void setPass(String pass)
		{
		Pass = pass;
		}

	public static ArrayList<String[]> getStatus()
		{
		return status;
		}

	public static void setStatus(ArrayList<String[]> status)
		{
		variables.status = status;
		}

	public static ArrayList<String[]> getListeDevice()
		{
		return ListeDevice;
		}

	public static void setListeDevice(ArrayList<String[]> listeDevice)
		{
		ListeDevice = listeDevice;
		}

	public static MainWindow getMainW()
		{
		return MainW;
		}

	public static void setMainW(MainWindow mainW)
		{
		MainW = mainW;
		}

	public static String getResultFileName()
		{
		return resultFileName;
		}

	public static void setResultFileName(String resultFileName)
		{
		variables.resultFileName = resultFileName;
		}

	public static ArrayList<String> getListeErreur()
		{
		return ListeErreur;
		}

	public static void setListeErreur(ArrayList<String> listeErreur)
		{
		ListeErreur = listeErreur;
		}

	public static String getRequestType()
		{
		return requestType;
		}

	public static void setRequestType(String requestType)
		{
		variables.requestType = requestType;
		}

	public static String getAxlLogin()
		{
		return axlLogin;
		}

	public static void setAxlLogin(String axlLogin)
		{
		variables.axlLogin = axlLogin;
		}

	public static String getAxlPass()
		{
		return axlPass;
		}

	public static void setAxlPass(String axlPass)
		{
		variables.axlPass = axlPass;
		}

	public static String getAxlPort()
		{
		return axlPort;
		}

	public static void setAxlPort(String axlPort)
		{
		variables.axlPort = axlPort;
		}

	public static String getVersionCCM()
		{
		return versionCCM;
		}

	public static void setVersionCCM(String versionCCM)
		{
		variables.versionCCM = versionCCM;
		}
	
	


	
	
	
	/*****
	 * End of getters and Setters 
	 */
	
	
	
	
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}
