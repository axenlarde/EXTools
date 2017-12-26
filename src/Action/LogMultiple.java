package Action;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import utils.MethodesUtiles;
import utils.testeur;
import utils.variables;

import Fenetre.MainWindow;

import jxl.Sheet;
import jxl.Workbook;

//Import



/***************************************************
 * Classe qui récupère le fichier source et extrait
 * les devices qu'il contient pour les mettre dans
 * une Collection
 ***************************************************/
public class LogMultiple
	{
	/************
	 * Variables
	 ************/
	MainWindow MainW;
	ArrayList<String[]> ListeDevice;
	ArrayList<String> ListeErreur;
	boolean erreurSource;
	boolean done;
	String[] options;
	
	//Fichier source
	JFileChooser fcSource;
	Workbook monWorkbook;
	Sheet maSheet;
	String emplacementSource;
	
	//Misc
	private boolean scheduled;
	
	
	
	/***************
	 * Constructeur
	 ***************/
	public LogMultiple(boolean scheduled)
		{
		this.MainW = variables.getMainW();
		ListeDevice = new ArrayList<String[]>();
		ListeErreur = new ArrayList<String>();
		erreurSource = false;
		done = true;
		fcSource = new JFileChooser();
		this.scheduled = scheduled;
		
		try
			{
			RecupFichier();
			openSheet();
			RemplissageListe();
			if(erreurSource)
				{
				String err = new String();
				for(int i=0; i<ListeErreur.size(); i++)
					{
					err += ListeErreur.get(i);
					err += "\n";
					}
				JOptionPane.showMessageDialog(null,"Le fichier comporte des erreurs : \n"+err,"Erreur dans le fichier",JOptionPane.ERROR_MESSAGE);
				}
			else
				{
				MainW.getContentPane().removeAll();
				MainW.getContentPane().add(new RecupInfoCCM(scheduled));
				MainW.validate();
				}
			}
		catch(Exception e)
			{
			if(e.getMessage().compareTo("CancelSelectFichier")==0)
				{
				variables.getLogger().error("Veuillez sélectionner un fichier");
				JOptionPane.showMessageDialog(null,"Veuillez sélectionner un fichier","alert",JOptionPane.INFORMATION_MESSAGE);
				}
			else
				{
				JOptionPane.showMessageDialog(null,"Le fichier sélectionné n'est pas au format .xls,\nn'est pas compatible avec excel ou est erroné","Erreur",JOptionPane.ERROR_MESSAGE);
				variables.getLogger().error("Problème de log multiple ="+e.getMessage());
				}
			e.printStackTrace();
			variables.getLogger().error(e);
			}
		
		}
	
	/****************************************************
	 * Méthode qui réupère le nom du fichier source via
	 * un JFileChooser
	 ****************************************************/
	public void RecupFichier() throws Exception
		{
		fcSource.setCurrentDirectory(new File(MethodesUtiles.getTargetOption("emplacementbase")));
		
		int resultat = fcSource.showDialog(fcSource, "Ouvrir");
		if(resultat == fcSource.APPROVE_OPTION)
			{
			emplacementSource = fcSource.getSelectedFile().toString();
			File mFile = new File(fcSource.getSelectedFile().toString());
			MethodesUtiles.setTargetOption("emplacementbase",mFile.getParent());
			variables.getLogger().info("Fichier source : "+fcSource.getSelectedFile().toString());
			}
		else
			{
			throw new Exception("CancelSelectFichier");
			}
		}
	
	/************************************************
	 * Méthode qui ouvre simplement un fichier excel
	 * et renvoi une exception si cela n'est pas
	 * possible
	 ************************************************/
	public void openSheet() throws Exception
		{
		monWorkbook = Workbook.getWorkbook(new File(emplacementSource));
		}
	
	/***************************************************
	 * Methode qui remplis une arrayliste avec la liste
	 * des devices contenu dans le fichier source
	 ***************************************************/
	public void RemplissageListe() throws Exception
		{
		maSheet = monWorkbook.getSheet(0);
		int i = 1;
		
		while(done)
			{
			if((maSheet.getCell(0,i).getContents().compareTo("#")==0) || (maSheet.getCell(1,i).getContents().compareTo("#")==0) || (maSheet.getCell(0,i).getContents().compareTo("")==0) || (maSheet.getCell(1,i).getContents().compareTo("")==0))
				{
				done = false;
				}
			else
				{
				String monTab[] = new String[7];
				monTab[0] = "";
				monTab[1] = "";
				monTab[2] = "";
				if(testeur.macValide(maSheet.getCell(0,i).getContents()))
					{
					monTab[3] = maSheet.getCell(0,i).getContents(); //Mac
					}
				else
					{
					erreurSource = true;
					ListeErreur.add("Ligne "+i+" adresse MAC incorrect");
					}
				monTab[4] = maSheet.getCell(1,i).getContents(); //UserID
				monTab[5] = maSheet.getCell(2,i).getContents(); //Profile
				monTab[6] = null;
				ListeDevice.add(monTab);
				i++;
				}
			}
		if((ListeDevice.size() == 0) && !erreurSource)
			{
			erreurSource = true;
			ListeErreur.add("Le fichier source a été détecté comme vide.\r\nVeuillez le vérifier.");
			variables.setListeErreur(ListeErreur);
			}
		else
			{
			variables.setListeDevice(ListeDevice);
			}
		}
	
	/*Fin classe*/
	}
