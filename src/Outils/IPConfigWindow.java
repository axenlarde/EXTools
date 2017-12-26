package Outils;
//Import
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import utils.BasePanel;

import Fenetre.MainWindow;
import Fenetre.Principale;



public class IPConfigWindow extends BasePanel
	{
	/**************
	 * Variables
	 **************/
	MainWindow MainW;
	int rows;
	
	//Contrôles
	JTextArea Information;
	
	//Disposition
	JPanel disposition;
	JPanel validation;
	JScrollPane sp;
	
	/****************
	 * Constructeurs
	 ****************/
	public IPConfigWindow(MainWindow MainW)
		{
		this.MainW = MainW;
		rows = 2;
		
		//Disposition
		disposition = new JPanel();
		validation = new JPanel();
		disposition.setLayout(new BoxLayout(disposition,BoxLayout.Y_AXIS));
		validation.setLayout(new BoxLayout(validation,BoxLayout.X_AXIS));
		
		//Contrôles
		Information = new JTextArea("Résultat :",5,30);
		Information.setPreferredSize(new Dimension(300,350));
		Information.setLineWrap(true);
		Information.setCaretPosition(Information.getDocument().getLength());
		sp = new JScrollPane(Information);
		
		//Paramètres
		validation.setBackground(Color.LIGHT_GRAY);
		
		//Assignation
		InfoAndProgress.add(Box.createHorizontalGlue());
		InfoAndProgress.add(new JLabel("Config IP du PC"));
		InfoAndProgress.add(Box.createHorizontalGlue());
		
		disposition.add(sp);
		
		validation.add(Box.createHorizontalGlue());
		validation.add(new JLabel(" "));
		validation.add(Box.createHorizontalGlue());
		
		
		Principale.add(disposition);
		Principale.add(validation);
		
		runScript("ipconfig /all");
		}
	
	/******************************************************
	 * Cette méthode exécute la commande passé en paramètre
	 ******************************************************/
	public void runScript(String nomScript)
		{
		Process resultPing;
		String ligne;
		try
			{
			resultPing = Runtime.getRuntime().exec(nomScript);
			
			//Accueil de la réponse
			BufferedReader in = new BufferedReader(new InputStreamReader(resultPing.getInputStream(),"CP1252"));
			while ((ligne = in.readLine()) != null)
	        	{
		    	ajoutInfo(ligne);
	         	}
			}
		catch(Exception e)
			{
			System.out.println("Erreur commande : "+e.getMessage());
			}
		}
	
	/****************************************************
	 * Méthode qui se charge de rajouter des infos dans
	 * le textearea
	 ****************************************************/
	public void ajoutInfo(String information)
		{
		rows++;
		Information.insert(information+"\n",Information.getDocument().getLength());
		Information.setRows(rows);
		}
	
	/*Fin classe*/
	}
