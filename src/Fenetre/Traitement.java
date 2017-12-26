package Fenetre;

//Import
import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFileChooser;
import utils.MethodesUtiles;
import utils.logGear;
import utils.variables;

import Action.LogMultiple;
import Action.RecupAXLInfoCCM;
import Action.RecupInfoProfile;
import Aide.WindowApropos;
import Outils.IPConfigWindow;
import Outils.PingWindow;
import Outils.optionWindow;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class Traitement implements ActionListener, WindowListener
	{
	/************
	 * Variables
	 ************/
	MainWindow MainW;
	
	//Date
	Date now;
	SimpleDateFormat formatHeure;
	
	//Fichier
	//Fichier source
	JFileChooser fcSource;
	String emplacementSource;
	WritableWorkbook monWorkbook;
	WritableSheet sheet;
	WritableFont arial10font;
	WritableFont arial10font2;
	WritableCellFormat TitleFormat;
	WritableCellFormat arial10format;
	
	/***************
	 * Constructeur
	 ***************/
	public Traitement(MainWindow MainW)
		{
		this.MainW = MainW;
		/*************************************************
		 * Option 1 : Nombre de Thread
		 * Option 6 : Choix du look&feel
		 * Option 7 : Timeout connexion callmanager
		 *************************************************/
		
		fcSource = new JFileChooser();
		}
	
	public void actionPerformed(ActionEvent evt)
		{
		
		if(evt.getSource() == MainW.LogUnique)
			{
			logGear.writeLog("Appuie sur le bouton Log unique", this, 79);
			MainW.getContentPane().removeAll();
			MainW.getContentPane().add(new RecupInfoProfile());
			MainW.validate();
			}
		
		if(evt.getSource() == MainW.Quitter)
			{
			logGear.writeLog("Appuie sur le bouton Quitter",this,87);
			MainW.dispose();
			}
		
		if(evt.getSource() == MainW.LogMultiple)
			{
			logGear.writeLog("Appuie sur le bouton Log multiple",this,93);
			new LogMultiple(false);
			}
		
		if(evt.getSource() == MainW.scheduleTask)
			{
			logGear.writeLog("Appuie sur le bouton tâche planifiée",this,93);
			boolean scheduled = true;
			new LogMultiple(scheduled);
			}
		
		if(evt.getSource() == MainW.Rapport)
			{
			logGear.writeLog("Appuie sur le bouton Rapport",this,93);
			MainW.getContentPane().removeAll();
			MainW.getContentPane().add(new RecupAXLInfoCCM());
			MainW.validate();
			}
		
		if(evt.getSource() == MainW.Help)
			{
			logGear.writeLog("Appuie sur le bouton Aide",this,99);
			}
			
		if(evt.getSource() == MainW.Apropo)
			{
			logGear.writeLog("Appuie sur le bouton A propos",this,104);
			new WindowApropos(MainW);
			}
		if(evt.getSource() == MainW.Option)
			{
			logGear.writeLog("Appuie sur le bouton Options",this,109);
			MainW.getContentPane().removeAll();
			MainW.getContentPane().add(new optionWindow(MainW));
			MainW.validate();
			}
		if(evt.getSource() == MainW.Ping)
			{
			logGear.writeLog("Appuie sur le bouton Ping",this,116);
			MainW.getContentPane().removeAll();
			MainW.getContentPane().add(new PingWindow(MainW));
			MainW.validate();
			}
		if(evt.getSource() == MainW.Ipconfig)
			{
			logGear.writeLog("Appuie sur le bouton Ipconfig",this,123);
			MainW.getContentPane().removeAll();
			MainW.getContentPane().add(new IPConfigWindow(MainW));
			MainW.validate();
			}
		if(evt.getSource() == MainW.Fsource)
			{
			logGear.writeLog("Fichier source",this,130);
			createExcelFile();
			}
		}

	/***********************************************
	 * Methode qui créer la feuille excel d'exemple
	 ***********************************************/
	public void createExcelFile()
		{
		try
			{
			RecupFichier();
			logGear.writeLog("Emplacement du fichier Excel : "+emplacementSource,this,143);
			openSheet();
			logGear.writeLog("Fichier Excel correctement initialisé",this,144);
			
			sheet = monWorkbook.createSheet("First Sheet", 0);
			
			//Définition des formats
			arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
			arial10font.setColour(Colour.WHITE);
			arial10font2 = new WritableFont(WritableFont.ARIAL, 10);
			arial10font2.setColour(Colour.BLACK);
			
			TitleFormat = new WritableCellFormat(arial10font);
			TitleFormat.setBackground(Colour.DARK_BLUE);
			
			arial10format = new WritableCellFormat(arial10font2);
			
			/***************
			 * Ecriture
			 ***************/
			//Titre
			sheet.addCell(new Label(0, 0, "MAC", TitleFormat));
			sheet.addCell(new Label(1, 0, "UserID", TitleFormat));
			sheet.addCell(new Label(2, 0, "Profile", TitleFormat));
			
			//Ecriture des données
			sheet.addCell(new Label(0, 1, "Ecrire ici la MAC", arial10format));
			sheet.addCell(new Label(0, 2, "#", arial10format));
			sheet.addCell(new Label(1, 1, "Ecrire ici le UserID", arial10format));
			sheet.addCell(new Label(1, 2, "#", arial10format));
			sheet.addCell(new Label(2, 1, "Si plusieurs profiles, préciser le profile ici", arial10format));
			sheet.addCell(new Label(2, 2, "#", arial10format));
			
			monWorkbook.write();
			logGear.writeLog("Fichier Excel correctement écrit",this,173);
			}
		catch(Exception e)
			{
			variables.getLogger().error(e);
			e.printStackTrace();
			}
		finally
			{
			try
				{
				monWorkbook.close();
				variables.getLogger().info("fichier "+emplacementSource+"\\FichierSource.xls fermé");
				}
			catch(Exception e)
				{
				variables.getLogger().error("Le fichier ne peut pas être fermé car il n'est pas ou plus ouvert :(");
				e.printStackTrace();
				}
			}
		}
	
	/****************************************************
	 * Méthode qui réupère le répertoire de destination via
	 * un JFileChooser
	 ****************************************************/
	public void RecupFichier() throws Exception
		{
		fcSource.setCurrentDirectory(new File(MethodesUtiles.getTargetOption("emplacementbase")));
		fcSource.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int resultat = fcSource.showDialog(fcSource, "Choisir");
		if(resultat == fcSource.APPROVE_OPTION)
			{
			emplacementSource = fcSource.getSelectedFile().toString();
			MethodesUtiles.setTargetOption("emplacementbase", emplacementSource);
			}
		else
			{
			throw new Exception("CancelSelectFichier");
			}
		}
	
	/***********************************************
	 * Méthode qui ouvre une nouvelle feuille excel
	 ***********************************************/
	public void openSheet() throws Exception
		{
		monWorkbook = Workbook.createWorkbook(new File(emplacementSource+"\\FichierSource.xls"));
		}
	
	
	public void windowClosed(WindowEvent arg0)
		{
		logGear.writeLog("***Fermeture EX Tools***\r\n", this, 115);
		System.exit(0);
		}


	public void windowClosing(WindowEvent arg0)
		{
		logGear.writeLog("***Fermeture EX Tools***\r\n", this, 122);
		System.exit(0);
		}

	
	public void windowActivated(WindowEvent e)
		{
		// TODO Auto-generated method stub
		}

	
	public void windowDeactivated(WindowEvent e)
		{
		// TODO Auto-generated method stub
		}

	
	public void windowDeiconified(WindowEvent e)
		{
		// TODO Auto-generated method stub
		}

	
	public void windowIconified(WindowEvent e)
		{
		// TODO Auto-generated method stub
		}

	
	public void windowOpened(WindowEvent e)
		{
		// TODO Auto-generated method stub
		}	
	
	/*Fin Classe*/
	}
