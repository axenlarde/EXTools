package Action;

import java.awt.Color;
import java.util.ArrayList;

import utils.MethodesUtiles;
import utils.ligneList;
import utils.logGear;
import utils.variables;

import Fenetre.MainWindow;

//Imports

/********************************************************************
 * Classe qui s'occupe de lancer le processus de log/delog une fois
 * que toutes les informations sont saisies
 ********************************************************************/


public class Logueur
	{
	/************
	 * Variables
	 ************/
	ArrayList<String[]> ListeDevice;
	ArrayList<ligneList> listeLigne;
	ArrayList<String[]> status;
	MainWindow MainW;
	boolean done;
	FenetreProgress maFenetreProgress;
	int NbrThreadSimultane;
	
	/***************
	 * Constructeur
	 ***************/
	public Logueur()
		{
		NbrThreadSimultane = 2;
		done = true;
		this.ListeDevice = variables.getListeDevice();
		this.MainW = variables.getMainW();
		listeLigne = new ArrayList<ligneList>();
		status = new ArrayList<String[]>();
		variables.setStatus(status);
		
		preparation();
		
		//Affichage de l'interface de progression
		MainW.getContentPane().removeAll();
		maFenetreProgress = new FenetreProgress(ListeDevice.get(0)[0]);
		maFenetreProgress.remplissage(listeLigne);
		MainW.getContentPane().add(maFenetreProgress);
		MainW.validate();
		logGear.writeLog("Interface de progression affiché",this,53);
		
		//Lancement de la classe qui gère la progression
		new Progression(listeLigne,maFenetreProgress);
		logGear.writeLog("Progression lancé",this,57);
		
		//Lancement de la classe qui gère le lancements des logs
		new Travailleur();
		logGear.writeLog("Travailleur lancé",this,61);
		}
	
	
	/************************************
	 * Methode qui prépare les ArrayList
	 ************************************/
	public void preparation()
		{
		String[] wait = new String[]{"waiting",""};
		for(int i=0; i<ListeDevice.size(); i++)
			{
			listeLigne.add(new ligneList(ListeDevice.get(i)[3],ListeDevice.get(i)[4]));
			if(Integer.toString(i%2).compareTo("0") == 0)
				{
				listeLigne.get(i).setFond(Color.WHITE);
				}
			else
				{
				listeLigne.get(i).setFond(Color.LIGHT_GRAY);
				}
			variables.getStatus().add(wait);
			}
		}
	
	/*2013*//*RATEL Alexandre 8)*/
	}
