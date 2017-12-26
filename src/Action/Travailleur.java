package Action;

//Import
import java.util.ArrayList;

import utils.GreenFire;
import utils.MethodesUtiles;
import utils.logGear;
import utils.variables;


/*********************************************************
 * Classe qui s'occupe de lancer les Threads de log/delog 
 *********************************************************/


public class Travailleur extends Thread 
	{
	/************
	 * Variables
	 ************/
	int NbrThreadSimultane;
	ArrayList<String[]> status;
	ArrayList<String[]> ListeDevice;
	
	/***************
	 * Constructeur
	 ***************/
	public Travailleur()
		{
		this.status = variables.getStatus();
		this.ListeDevice = variables.getListeDevice();
		NbrThreadSimultane = Integer.parseInt(MethodesUtiles.getTargetOption("threadLevel"));
		start();
		}
	
	public void run()
		{
		int i = 0;
		while(ListeDevice.size()>i && GreenFire.getStop())
			{
			if(processing() && GreenFire.getGo())
				{
				String[] str = new String[]{"processing",""};
				status.set(i, str);
				//new LogPlusRep(ListeDevice.get(i)[0], ListeDevice.get(i)[1], ListeDevice.get(i)[2], ListeDevice.get(i)[3], ListeDevice.get(i)[4], ListeDevice.get(i)[5], ListeDevice.get(i)[6], status, i);
				new LogPlusRep(ListeDevice.get(i)[3], ListeDevice.get(i)[4], ListeDevice.get(i)[5], ListeDevice.get(i)[6], i);
				i++;
				logGear.writeLog("Thread pour loguer le device "+(i+1)+" lancé", this, 44);
				}
			try
				{
				sleep(250);
				}
			catch(Exception e)
				{
				logGear.writeLog("Erreur de sleep",this,55);
				}
			}
		
		if(GreenFire.getStop())
			{
			logGear.writeLog("Travailleur terminé", this, 62);
			}
		else
			{
			logGear.writeLog("Travailleur stopé", this, 58);
			}
		}

	public boolean processing()
		{
		int compteur = 0;
		for(int i=0; i<status.size(); i++)
			{
			if(status.get(i)[0].compareTo("processing")==0)
				{
				compteur++;
				}
			}
		if(compteur < NbrThreadSimultane)
			{
			return true;
			}
		else
			{
			return false;
			}
		}
	
	/*2013*//*RATEL Alexandre 8)*/
	}
