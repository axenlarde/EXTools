
package utils;

import Fenetre.Principale;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

/*********************************************
 * Class use to initiate logging
 *********************************************/
public class initLogging
	{
	
	public static Logger init()
		{
		Logger logger = Logger.getLogger(Principale.class);
		PatternLayout myPattern = new PatternLayout("%d{dd/MM/yyyy - HH:mm:ss} - [%p] (%C:%L) - %m%n");
		
		//On paramètre l'appender qui va écrire dans un fichier
		RollingFileAppender myR = new RollingFileAppender();
		myR.setName("myFileAppender");
		myR.setFile(variables.getNomProg()+"_LogFile.txt");
		myR.setMaxFileSize("1000KB");
		myR.setMaxBackupIndex(2);
		myR.setLayout(myPattern);
		myR.activateOptions();
		
		//On paramètre l'appender qui va écrire dans la console
		ConsoleAppender myC = new ConsoleAppender();
		myC.setLayout(myPattern);
		myC.activateOptions();
		
		//On ajoute les appender à notre logger
		BasicConfigurator.configure(myC);
		logger.addAppender(myR);
		
		return logger;
		}
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}
