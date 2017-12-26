package Action;

//Import
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import utils.GreenFire;
import utils.ligneList;
import utils.logGear;
import utils.variables;

import Fenetre.MainWindow;




/****************************************************
 * Classe qui g�re la mise � jour de la fen�tre pour
 * afficher graphiquement l'�tat d'avancement
 ****************************************************/
public class Progression extends Thread
	{
	/************
	 * Variables
	 ************/
	MainWindow MainW;
	ArrayList<String[]> status;
	boolean done;
	ArrayList<ligneList> listeLigne;
	long debut;
	long TEcoule;
	float progress;
	int count;
	FenetreProgress maFenetreProgress;
	ArrayList<String[]> ListeDevice;
	
	//Calcul du temps d'ex�cution
	Date maDate;
	SimpleDateFormat formatHeure;
	
	//Gestion des erreurs de d�parts
	String errorType;
	boolean errorDone;
	
	/***************
	 * Constructeur
	 ***************/
	public Progression(ArrayList<ligneList> listeLigne, FenetreProgress maFenetreProgress)
		{
		this.MainW = variables.getMainW();
		this.listeLigne = listeLigne;
		this.status = variables.getStatus();
		debut = System.currentTimeMillis();
		this.maFenetreProgress = maFenetreProgress;
		count = 0;
		this.ListeDevice = variables.getListeDevice();
		done = true;
		errorType = new String();
		errorDone = false;
		
		formatHeure = new SimpleDateFormat("mm'mn ':ss's ':SSS'ms'");
		maDate = new Date();
		
		//Freeze de l'interface
		MainW.Action.setEnabled(false);
		MainW.Outils.setEnabled(false);
		
		start();
		}
	
	public void run()
		{
		while(done)
			{
			for(int i=0; i<status.size(); i++)
				{
				if(status.get(i)[0].compareTo("waiting")==0)
					{
					//On ne fait rien
					}
				else if(status.get(i)[0].compareTo("processing")==0)
					{
					listeLigne.get(i).setResult(status.get(i)[0]);
					listeLigne.get(i).nextStep();
					}
				else if((status.get(i)[0].compareTo("success")==0) || (status.get(i)[0].compareTo("error")==0))
					{
					if(!errorDone && (firstError()))
						{
						//On freeze le lancement des threads
						GreenFire.setGo(false);
						
						int decision = 1;
						
						if(errorType.compareTo("Authenticate") == 0)
							{
							//Premier type d'erreur : Autentification incorrect
							//Demande de validation � l'utilisateur : continue or not
							logGear.writeLog("Le premier Thread a relev� une erreur d'autentification",this,103);
							String message = "La premi�re tentative de log renvoi une erreur d'authentification.\nLemot de passe est peut �tre erron�.\nVoulez vous continuer ?";
							String[] options = new String[]{"Oui","Non"}; 
							decision = JOptionPane.showOptionDialog(MainW, message, "Attention", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
							}
						else if(errorType.compareTo("TimeOut") == 0)
							{
							//Deuxi�me type d'erreur : Serveur injoignable
							logGear.writeLog("Le premier Thread n'a pas r�ussi � joindre le serveur",this,111);
							String message = "Le serveur semble injoignable.\nContinuer quand m�me ?";
							String[] options = new String[]{"Oui","Non"}; 
							decision = JOptionPane.showOptionDialog(MainW, message, "Attention", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
							}
						
						if(decision == 1)
							{
							//Fin d'execution et affichage du r�sultat
							done = false;
							GreenFire.setGo(false);
							GreenFire.setStop(false);
							logGear.writeLog("L'utilisateur � d�cid� d'arreter l'ex�cution",this,127);
							}
						else
							{
							//On relance les threads
							GreenFire.setGo(true);
							logGear.writeLog("L'utilisateur � d�cid� de continuer",this,127);
							}
						}
					listeLigne.get(i).setResult(status.get(i)[0]);
					listeLigne.get(i).setProgress(" - Done");
					count ++;
					}
				}
			
			MAJProgress();
			
			if(finish() || !GreenFire.getStop())
				{
				done = false;
				}
			
			try
				{
				sleep(500);
				}
			catch(Exception e)
				{
				logGear.writeLog("ERROR : Erreur de sleep : "+e.getMessage(),this,149);
				e.printStackTrace();
				}
			}
		TEcoule = System.currentTimeMillis() - debut;
		logGear.writeLog("Fin de progression",this,154);
		logGear.writeLog("Affichage du r�sultat",this,155);
		maDate.setTime(TEcoule);
		logGear.writeLog("Temps �coul� : "+formatHeure.format(maDate),this,157);
		
		//Degel de l'interface
		MainW.Action.setEnabled(true);
		MainW.Outils.setEnabled(true);
		
		//Lancement de la fen�tre de r�sultat
		MainW.getContentPane().removeAll();
		MainW.getContentPane().add(new Resultat(formatHeure.format(maDate)));
		MainW.validate();
		
		//Reset du feu vert
		GreenFire.setGo(true);
		GreenFire.setStop(true);
		}
	
	public boolean finish()
		{
		boolean isFinish = true;
		for(int i=0; i< status.size(); i++)
			{
			if(((status.get(i)[0].compareTo("success")==0) || (status.get(i)[0].compareTo("error")==0)) && isFinish)
				{
				isFinish = true;
				}
			else
				{
				isFinish = false;
				}
			}
		return isFinish;
		}
	
	public void MAJProgress()
		{
		if(count == 0)
			{
			progress = 0;
			}
		else
			{
			progress = (((float)count)/(float)status.size())*100F;
			}
		maFenetreProgress.setInfo(Integer.toString((int)progress),Integer.toString(count)+"/"+Integer.toString(status.size()));
		count = 0;
		}
	
	public boolean firstError()
		{
		if(Pattern.matches(".*Could not authenticate.*", status.get(0)[1]))
			{
			errorDone = true;
			errorType = "Authenticate";
			return true;
			}
		else if(Pattern.matches(".*Serveur.*", status.get(0)[1]))
			{
			errorDone = true;
			errorType = "TimeOut";
			return true;
			}
		else
			{
			return false;
			}
		}
	
	/*Fin classe*/
	}
