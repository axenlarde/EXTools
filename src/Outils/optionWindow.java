package Outils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.LookupTable;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.UIManager;
//Import
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import utils.BasePanel;
import utils.MethodesUtiles;
import utils.logGear;
import utils.variables;

import Fenetre.MainWindow;
import Fenetre.Principale;


public class optionWindow extends BasePanel implements ActionListener
	{
	/****************
	 * Variables
	 ****************/
	MainWindow MainW;
	UIManager.LookAndFeelInfo[] infoLook;
	
	//Controles
	JButton valider;
	JSlider threadSlide;
	JSlider timeoutSlide;
	JComboBox lookAndFeel;
	
	//Disposition
	JPanel disposition;
	JPanel dispo1;
	JPanel dispo2;
	JPanel dispo3;
	JPanel dispo4;
	JPanel dispo5;
	JPanel dispo6;
	JPanel dispo7;
	JPanel validation;
	
	//Date
	Date now;
	SimpleDateFormat formatHeure;
	
	/***************
	 * Constructeur
	 ****************/
	public optionWindow(MainWindow MainW)
		{
		this.MainW = MainW;
		infoLook = UIManager.getInstalledLookAndFeels();
		
		//Disposition
		disposition = new JPanel();
		dispo1 = new JPanel();
		dispo2 = new JPanel();
		dispo3 = new JPanel();
		dispo4 = new JPanel();
		dispo5 = new JPanel();
		dispo6 = new JPanel();
		dispo7 = new JPanel();
		validation = new JPanel();
		disposition.setLayout(new BoxLayout(disposition,BoxLayout.Y_AXIS));
		dispo1.setLayout(new BoxLayout(dispo1,BoxLayout.X_AXIS));
		dispo2.setLayout(new BoxLayout(dispo2,BoxLayout.X_AXIS));
		dispo3.setLayout(new BoxLayout(dispo3,BoxLayout.X_AXIS));
		dispo4.setLayout(new BoxLayout(dispo4,BoxLayout.X_AXIS));
		dispo5.setLayout(new BoxLayout(dispo5,BoxLayout.X_AXIS));
		dispo6.setLayout(new BoxLayout(dispo6,BoxLayout.X_AXIS));
		dispo7.setLayout(new BoxLayout(dispo7,BoxLayout.X_AXIS));
		disposition.setBorder(new TitledBorder("Options"));
		validation.setLayout(new BoxLayout(validation,BoxLayout.X_AXIS));
		
		
		/************
		 * Contrôles
		 **/
		valider = new JButton("Valider");
		
		//slider
		threadSlide = new JSlider(1,5,2);
		threadSlide.setPaintTicks(true);
		threadSlide.setMajorTickSpacing(1);
		threadSlide.setPaintLabels( true );
		threadSlide.setSnapToTicks( true );
		
		timeoutSlide = new JSlider(0,20,10);
		timeoutSlide.setPaintTicks(true);
		timeoutSlide.setMajorTickSpacing(5);
		timeoutSlide.setMinorTickSpacing(1);
		timeoutSlide.setPaintLabels(true);
		timeoutSlide.setSnapToTicks(true);
		
		String[] look = new String[infoLook.length];
		for(int i=0; i<infoLook.length; i++)
			{
			look[i] = infoLook[i].getName();
			}
		lookAndFeel = new JComboBox(look);
		
		/**
		 * Fin contrôle
		 ***************/
		
		//Paramètres
		validation.setBackground(Color.LIGHT_GRAY);
		lookAndFeel.setMaximumSize(new Dimension(50,25));
		
		//Assignation
		InfoAndProgress.add(Box.createHorizontalGlue());
		InfoAndProgress.add(new JLabel("Options"));
		InfoAndProgress.add(Box.createHorizontalGlue());
		
		dispo1.add(new JLabel("Nombre de Thread simultané :"));
		dispo1.add(Box.createHorizontalGlue());
		dispo1.add(threadSlide);
		disposition.add(dispo1);
		dispo2.add(new JLabel("Timeout de connexion :"));
		dispo2.add(Box.createHorizontalGlue());
		dispo2.add(timeoutSlide);
		disposition.add(Box.createVerticalGlue());
		disposition.add(dispo2);
		dispo7.add(new JLabel("Choix du Look & Feel :"));
		dispo7.add(Box.createHorizontalGlue());
		dispo7.add(lookAndFeel);
		disposition.add(Box.createVerticalGlue());
		disposition.add(dispo7);
		
		validation.add(Box.createHorizontalGlue());
		validation.add(valider);
		validation.add(Box.createHorizontalGlue());
		
		Principale.add(Box.createVerticalGlue());
		Principale.add(disposition);
		Principale.add(Box.createVerticalGlue());
		Principale.add(validation);
		
		//Events
		valider.addActionListener(this);
		
		recupCurrentOptions();
		}

	
	public void actionPerformed(ActionEvent evt)
		{
		if(evt.getSource() == this.valider)
			{
			MethodesUtiles.setTargetOption("threadLevel", Integer.toString(threadSlide.getValue()));
			MethodesUtiles.setTargetOption("timeout", Integer.toString(timeoutSlide.getValue()));
			MethodesUtiles.setTargetOption("lookandfeel", infoLook[lookAndFeel.getSelectedIndex()].getClassName());
			
			//Permet d'appliquer le changement de look and feel immédiatement
			MainW.getContentPane().removeAll();
			MainW.getContentPane().add(new JPanel());
			MainW.lookAndFeel(MethodesUtiles.getTargetOption("lookandfeel"));
			MainW.validate();
			}
		}
	
	/******************************************************
	 * Méthode qui assigne les valeurs actuels des options
	 * aux contôles correspondants
	 ******************************************************/
	public void recupCurrentOptions()
		{
		threadSlide.setValue(Integer.parseInt(MethodesUtiles.getTargetOption("threadLevel")));
		timeoutSlide.setValue(Integer.parseInt(MethodesUtiles.getTargetOption("timeout")));
		lookAndFeel.setSelectedItem(UIManager.getLookAndFeel().getName());
		}
	
	/*2013*//*RATEL Alexandre 8)*/
	}
