package utils;

import java.util.StringTokenizer;
import java.util.regex.Pattern;




public class testeur
	{
	/************
	 * Variables
	 ************/
	
	public static boolean testSuccess(String texte)
		{
		texte.replace("\n", "");
		System.out.println(texte);
		
		if(Pattern.matches(".*<success/>.*", texte))
			{
			return true;
			}
		return false;
		}
	
	
	/*******************************************************
	 * Methode qui v�rifie une adresse IP
	 *******************************************************/
	public static boolean IPValide(String IP)
		{
		boolean done = true;
		int a=0;
		try
			{
			StringTokenizer st = new StringTokenizer(IP,".");
			while(st.hasMoreTokens() && done)
				{
				String tok = st.nextToken();
				if(Pattern.matches("\\d*", tok))
					{
					int nb = Integer.parseInt(tok);
					if((nb>=0)&&(nb<=255))
						{
						done = true;
						}
					else
						{
						done = false;
						}
					}
				else
					{
					done = false;
					}
				a++;
				}
			if(done && (a == 4))
				{
				return true;
				}
			}
		catch(Exception e)
			{
			System.out.println("Probl�me de verif IP");
			e.printStackTrace();
			}
		return false;
		}
	
	/*************************************
	 * Methode qui v�rfie une adresse MAC
	 *************************************/
	public static boolean macValide(String mac)
		{
		try
			{
			if((mac.compareTo("") != 0) && (mac.length() == 12) && Pattern.matches("\\p{XDigit}*", mac))
				{
				return true;
				}
			else
				{
				return false;
				}
			}
		catch(Exception e)
			{
			System.out.println("Probl�me de verif MAC");
			e.printStackTrace();
			}
		return false;
		}
	
	
	
	/*Fin classe*/
	}
