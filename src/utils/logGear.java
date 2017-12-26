package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;




/*************************************
 * Classe qui permet de gérer les log via 
 * des méthodes static
 *************************************/

public class logGear
	{
	/************
	 * Variables
	 ************/
	static String emplacementLogFile;
	static String nomLogFile;
	static File fichierLog;
	static FileWriter monFichierLog;
	static BufferedWriter tampon;
	static Date now;
	static SimpleDateFormat formatSimple = new SimpleDateFormat("dd/MM/yy  HH':'mm':'ss");
	
	/**********************************************
	 * Methode qui définit les paramètres des logs
	 **********************************************/
	public static void setParam(String pathLogFile, String nameLogFile)
		{
		emplacementLogFile = pathLogFile;
		nomLogFile = nameLogFile;
		}
	
	public static void writeLog(String log, Object classe, int ligne)
		{
		variables.getLogger().info(log);
		}
	
	public static void writeLog(String log, Object classe, String ligne)
		{
		variables.getLogger().info(log);
		}
	
	public static void writeLog(Object classe, Exception e)
		{
		variables.getLogger().error(e);
		}
	
	public static void writeLog(String log, Object classe)
		{
		variables.getLogger().info(log);
		}
	
	public static void writeLog(String log, String classe, int ligne)
		{
		variables.getLogger().info(log);
		}
	
	/*2013*//*RATEL Alexandre 8)*/
	}
