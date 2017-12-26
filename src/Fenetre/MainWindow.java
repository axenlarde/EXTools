package Fenetre;

//Import
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

import utils.Aspect;
import utils.Centrer;
import utils.MethodesUtiles;
import utils.logGear;
import utils.variables;





public class MainWindow extends JFrame
	{
	//Variables
	private static final long serialVersionUID = 1L;
	
	//Positionnement
	Centrer position;
	
	//Contrôle
	public JMenuBar maBarre;
	public JMenu Action;
	public JMenuItem scheduleTask;
	public JMenuItem LogUnique;
	public JMenuItem LogMultiple;
	public JMenuItem Rapport;
	public JMenuItem Quitter;
	public JMenu Outils;
	public JMenuItem Option;
	public JMenuItem Ping;
	public JMenuItem Ipconfig;
	public JMenuItem Fsource;
	public JMenu Aide;
	public JMenuItem Help;
	public JMenuItem Apropo;
	public JLabel picture;
	
	
	//Disposition
	JPanel principale;
	
	//Evenement
	Traitement trt;
	
	//Fenetre
	int haut;
	int larg;
	
	//Constructeur
	public MainWindow()
		{
		super(variables.getNomProg()+" - v"+variables.getVersion());
		setResizable(false);
		haut = 450;
		larg = 600;
		
		try
			{
			//Look and feel
			lookAndFeel(MethodesUtiles.getTargetOption("lookandfeel"));
			Aspect.load(this, getClass().getResource("/Art/icone1.gif"));
			
			//Image d'accueil
			picture = new JLabel(new ImageIcon(getClass().getResource("/Art/Accueil3.jpg")));
			}
		catch(Exception e)
			{
			logGear.writeLog("ERROR : Problème de chargement de l'image d'accueil : "+e.getMessage(),this,71);
			logGear.writeLog("ERROR : Chargement d'une image vide a la place",this,72);
			picture = new JLabel();
			e.printStackTrace();
			}
		
		//Contrôle
		maBarre = new JMenuBar();
		Action = new JMenu("Action");
		scheduleTask = new JMenuItem("Planifier une tâche");
		LogUnique = new JMenuItem("Log/Logout unique");
		LogMultiple = new JMenuItem("Log/Logout multiple");
		Rapport = new JMenuItem("Rapport");
		Quitter = new JMenuItem("Quitter");
		Outils = new JMenu("Outils");
		Option = new JMenuItem("Options");
		Ping = new JMenuItem("Ping");
		Ipconfig = new JMenuItem("Ipconfig");
		Fsource = new JMenuItem("Fichier source");
		Aide = new JMenu("Aide");
		Help = new JMenuItem("Aide");
		Apropo = new JMenuItem("A propos");
		
		//Disposition
		principale = new JPanel();
		principale.setLayout(new BoxLayout(principale,BoxLayout.X_AXIS));
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		
		//Paramètres
		principale.setBackground(Color.WHITE);
		Quitter.setMnemonic('Q');
		Quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,InputEvent.CTRL_MASK));
		
		//Assignation
		maBarre.add(Action);
		maBarre.add(Outils);
		maBarre.add(Aide);
		Action.add(scheduleTask);
		Action.add(LogUnique);
		Action.add(LogMultiple);
		Action.add(Rapport);
		Action.add(Quitter);
		Outils.add(Option);
		Outils.add(Ping);
		Outils.add(Ipconfig);
		Outils.add(Fsource);
		Aide.add(Help);
		Aide.add(Apropo);
		setJMenuBar(maBarre);
		
		principale.add(picture);
		
		this.getContentPane().add(principale);
		
		//Positionnement
		setSize(larg,haut);
		new Centrer(this);
		
		//Event
		trt = new Traitement(this);
		scheduleTask.addActionListener(trt);
		LogUnique.addActionListener(trt);
		LogMultiple.addActionListener(trt);
		Rapport.addActionListener(trt);
		Quitter.addActionListener(trt);
		Aide.addActionListener(trt);
		Apropo.addActionListener(trt);
		Option.addActionListener(trt);
		Ping.addActionListener(trt);
		Ipconfig.addActionListener(trt);
		Fsource.addActionListener(trt);
		this.addWindowListener(trt);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setVisible(true);
		}
	
	/****************************************************
	 * Methode qui permet de définir le look and feel de
	 * la fenêtre
	 ****************************************************/
	public void lookAndFeel(String lookAndFeel)
		{
		try
			{
			UIManager.setLookAndFeel(lookAndFeel);
			SwingUtilities.updateComponentTreeUI(this);
			}
		catch(Exception e)
			{
			e.printStackTrace();
			}
		}
	
	/*Fin Classe*/
	}
