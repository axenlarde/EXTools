package utils;


/********************************************************************
 * Classe aux methodes static qui permet de synchroniser la classe
 * progression et Travailleur vi la variable Go
 ********************************************************************/



public class GreenFire
	{
	/************
	 * Variables
	 ************/
	//Pour gérer la synchronisation entre la progression et le travailleur
	static private boolean Go;
	
	//Pour arrêter l'action et passer directement au rapport
	static private boolean Stop;
	
	public static void setGo(boolean value)
		{
		Go = value;
		logGear.writeLog("Go = "+value,"Utils.GreenFire.java",25);
		}
	
	public static boolean getGo()
		{
		return Go;
		}
	
	public static void setStop(boolean value)
		{
		Stop = value;
		logGear.writeLog("Stop = "+value,"Utils.GreenFire.java",36);
		}

	public static boolean getStop()
		{
		return Stop;
		}
	
	/*Fin classe*/
	}
