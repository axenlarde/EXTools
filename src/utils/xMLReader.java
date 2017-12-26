package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class xMLReader
	{
	/****************
	 * Variables
	 ****************/
	
	/**************************************************
	 * Methode qui lit le fichier et place son contenu
	 * dans un string
	 **************************************************/
	public static String fileRead(String fileName) throws IOException, FileNotFoundException
		{
		String template = new String("");
		variables.getLogger().info("Source = "+fileName);
		FileReader monFichier = new FileReader(fileName);
		BufferedReader tampon = new BufferedReader(monFichier);
		
		//lecture du fichier
		while(true)
			{
			String ligne = tampon.readLine();
			if(ligne == null)
				{
				break;
				}
			template += ligne;
			}
		variables.getLogger().info(template);
		variables.getLogger().info("Lecture terminée");
		return template;
		}
	
	/*2010*//*AR ;)*/
	}
