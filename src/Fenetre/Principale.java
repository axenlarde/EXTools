package Fenetre;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Level;
import utils.GreenFire;
import utils.MethodesUtiles;
import utils.initLogging;
import utils.variables;


public class Principale
	{
	//Variables
	String version;
	String nom;
	Date now;
	SimpleDateFormat formatHeure;
	
	//Constructeur
	public Principale()
		{
		//version du logiciel
		utils.variables.setVersion("3.3");
		//Nom du logiciel
		utils.variables.setNomProg("EXTools");
		
		/**********************
		 * Initialisation de la journalisation
		 ************/
		variables.setLogger(initLogging.init());
		variables.getLogger().info("Entering application");
		variables.getLogger().info("## Welcome to : "+variables.getNomProg()+" : "+variables.getVersion()+" ##");
		variables.getLogger().info("## Author : RATEL Alexandre ##");
		/**************/
		
		/***********
		 * Initialisation des variables
		 */
		new utils.variables();
		/************/
		
		/********************
		 * Check if java version is compatible
		 ********************/
		MethodesUtiles.checkJavaVersion();
		/*********************/
		
		/*************************
		 * Set the logging level
		 *************************/
		String level = MethodesUtiles.getTargetOption("log4j");
		if(level.compareTo("DEBUG")==0)
			{
			variables.getLogger().setLevel(Level.DEBUG);
			}
		else if (level.compareTo("INFO")==0)
			{
			variables.getLogger().setLevel(Level.INFO);
			}
		
		variables.getLogger().info("Niveau de log d'après le fichier de préférence : "+variables.getLogger().getLevel().toString());
		/*************************/
		
		/****************************
		 * Initialisation du feu vert :
		 * * Permet de gérer la pause 
		 ****************************/
		GreenFire.setGo(true);
		GreenFire.setStop(true);
		/****************************/
		
		/*********************************
		 * Lancement du Thread principal
		 *********************************/
		variables.setMainW(new MainWindow());
		/*********************************/
		}
	
	public static void main(String[] args)
		{
		new Principale();
		}
	
	/*Fin Classe*/
	}
