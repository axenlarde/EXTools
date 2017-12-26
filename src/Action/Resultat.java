package Action;

//Import
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import utils.BasePanel;
import utils.MethodesUtiles;
import utils.logGear;
import utils.variables;

import Fenetre.Principale;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


/*************************************************
 * Classe qui affiche le résultat d'un opération
 *************************************************/
public class Resultat extends BasePanel implements ActionListener
	{
	/***************
	 * Variables
	 ***************/
	int TotalLog;
	int SuccessLog;
	int ErrorLog;
	String ElapseTime;
	ArrayList<String[]> status;
	ArrayList<String[]> ListeDevice;
	
	//Fichier 
	WritableWorkbook monWorkbook;
	WritableSheet sheet;
	WritableFont arial10font;
	WritableFont arial10font2;
	WritableCellFormat TitleFormat;
	WritableCellFormat arial10format;
	
	//Contrôles
	JButton ViewLogFile;
	JButton ExportResultFile;
	JButton valider;
	
	//Disposition
	JPanel disposition;
	JPanel validation;
	
	
	/****************
	 * Constructeur
	 ****************/
	public Resultat(String ElapseTime)
		{
		this.status = variables.getStatus();
		this.ElapseTime = ElapseTime;
		this.ListeDevice = variables.getListeDevice();
		DoStat();
		logGear.writeLog("Stats finales : - Total : "+ListeDevice.size()+" - Succes : "+SuccessLog+" - Erreur : "+ErrorLog,this,75);
		
		//Disposition
		disposition = new JPanel();
		validation = new JPanel();
		disposition.setLayout(new GridLayout(4,2,15,15));
		disposition.setBorder(new TitledBorder("Résultat"));
		validation.setLayout(new BoxLayout(validation,BoxLayout.X_AXIS));
		
		//Contrôles
		valider = new JButton("Terminer");
		ExportResultFile = new JButton("Exporter résultat");
		ViewLogFile = new JButton("Voir le fichier des logs");
		
		//Paramètres
		validation.setBackground(Color.LIGHT_GRAY);
		
		//Assignation
		InfoAndProgress.add(Box.createHorizontalGlue());
		InfoAndProgress.add(new JLabel("Fin de programme"));
		InfoAndProgress.add(Box.createHorizontalGlue());
		
		disposition.add(new JLabel(" Total profiles:"));
		disposition.add(new JLabel(Integer.toString(TotalLog)));
		disposition.add(new JLabel(" Réussite :"));
		disposition.add(new JLabel(Integer.toString(SuccessLog)));
		disposition.add(new JLabel(" Echec :"));
		disposition.add(new JLabel(Integer.toString(ErrorLog)));
		disposition.add(new JLabel("Temps écoulé :"));
		disposition.add(new JLabel(ElapseTime));
		
		validation.add(Box.createHorizontalGlue());
		validation.add(ViewLogFile);
		validation.add(Box.createHorizontalGlue());
		validation.add(ExportResultFile);
		validation.add(Box.createHorizontalGlue());
		validation.add(valider);
		validation.add(Box.createHorizontalGlue());
		
		Principale.add(Box.createVerticalGlue());
		Principale.add(disposition);
		Principale.add(Box.createVerticalGlue());
		Principale.add(validation);
		
		//Events
		valider.addActionListener(this);
		ExportResultFile.addActionListener(this);
		ViewLogFile.addActionListener(this);
		}


	public void actionPerformed(ActionEvent evt)
		{
		if(evt.getSource() == this.valider)
			{
			logGear.writeLog("***Fermeture EX Tools***\r\n", this, 131);
			System.exit(0);
			}
		if(evt.getSource() == this.ExportResultFile)
			{
			logGear.writeLog("Exportation du fichier de résultat",this,134);
			createExcelFile();
			runScript("explorer "+MethodesUtiles.getTargetOption("emplacementbase"));
			}
		if(evt.getSource() == this.ViewLogFile)
			{
			runScript("explorer .\\");
			}
		}
	
	/****************************************************
	 * Méthode qui compte le nombre de succès et d'échec
	 ****************************************************/
	public void DoStat()
		{
		TotalLog = status.size();
		SuccessLog = 0;
		ErrorLog = 0;
		for(int i=0; i<status.size(); i++)
			{
			if(status.get(i)[0].compareTo("success")==0)
				{
				SuccessLog++;
				}
			else if(status.get(i)[0].compareTo("error")==0)
				{
				ErrorLog++;
				}
			}
		}
	
	public void createExcelFile()
		{
		try
			{
			openSheet();
			logGear.writeLog("Fichier correctement initialisé",this,172);
			
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
			sheet.addCell(new Label(2, 0, "Résultat", TitleFormat));
			sheet.addCell(new Label(3, 0, "Description de l'erreur", TitleFormat));
			
			//Ecriture des données
			//IPCCM, login, pass, mac, profile, Line, true, this
			for(int i = 0; i<ListeDevice.size(); i++)
				{
				sheet.addCell(new Label(0, i+1, ListeDevice.get(i)[3], arial10format));
				sheet.addCell(new Label(1, i+1, ListeDevice.get(i)[4], arial10format));
				sheet.addCell(new Label(2, i+1, status.get(i)[0], arial10format));
				if(status.get(i)[0].compareTo("error")==0)
					{
					sheet.addCell(new Label(3, i+1, status.get(i)[1], arial10format));
					}
				else
					{
					sheet.addCell(new Label(3, i+1, "#", arial10format));
					}
				}
			monWorkbook.write();
			logGear.writeLog("Fichier correctement écrit",this,211);
			}
		catch(Exception e)
			{
			logGear.writeLog("ERROR : Ecriture du fichier excel : "+e.getMessage(),this,215);
			e.printStackTrace();
			}
		finally
			{
			try
				{
				monWorkbook.close();
				}
			catch(Exception e)
				{
				logGear.writeLog("Le fichier ne peut pas être fermé car il n'est pas ou plus ouvert :(",this,227);
				}
			}
		}
	
	/******************************************************
	 * Cette méthode exécute la commande passé en paramètre
	 ******************************************************/
	public void runScript(String nomScript)
		{
		try
			{
			Runtime.getRuntime().exec(nomScript);
			}
		catch(Exception e)
			{
			logGear.writeLog("ERROR : Erreur commande : "+e.getMessage(),this,243);
			}
		}
	
	/***********************************************
	 * Méthode qui ouvre une nouvelle feuille excel
	 ***********************************************/
	public void openSheet() throws Exception
		{
		monWorkbook = Workbook.createWorkbook(new File(MethodesUtiles.getTargetOption("emplacementbase")+"\\"+variables.getResultFileName()+".xls"));
		}
	
	/*2013*//*RATEL Alexandre 8)*/
	}