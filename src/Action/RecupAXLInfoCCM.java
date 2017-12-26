package Action;

/*********
 * Import
 *********/
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import utils.BasePanel;
import utils.MethodesUtiles;
import utils.logGear;
import utils.testeur;
import utils.variables;

import Fenetre.MainWindow;



public class RecupAXLInfoCCM extends BasePanel implements ActionListener
	{
	/************
	 * Variables
	 ************/
	MainWindow MainW;
	ArrayList<String[]> ListeDevice;
	
	//Contrôles
	JTextField TEXTipccm;
	JTextField TEXTlogin;
	JPasswordField TEXTPass;
	JButton getReportButton;
	
	//Disposition
	JPanel disposition;
	JPanel dispo1;
	JPanel dispo2;
	JPanel dispo3;
	JPanel validation;
	
	/***************
	 * Constructeur
	 ***************/
	public RecupAXLInfoCCM()
		{
		this.MainW = variables.getMainW();
		this.ListeDevice = variables.getListeDevice();
		
		//Disposition
		disposition = new JPanel();
		dispo1 = new JPanel();
		dispo2 = new JPanel();
		dispo3 = new JPanel();
		validation = new JPanel();
		disposition.setLayout(new BoxLayout(disposition,BoxLayout.Y_AXIS));
		dispo1.setLayout(new BoxLayout(dispo1,BoxLayout.X_AXIS));
		dispo2.setLayout(new BoxLayout(dispo2,BoxLayout.X_AXIS));
		dispo3.setLayout(new BoxLayout(dispo3,BoxLayout.X_AXIS));
		validation.setLayout(new BoxLayout(validation,BoxLayout.X_AXIS));
		disposition.setBorder(new TitledBorder("Informations CallManager"));
		
		//Contrôles
		getReportButton = new JButton("Obtenir le rapport");
		TEXTipccm = new JTextField("",40);
		TEXTlogin = new JTextField("",40);
		TEXTPass = new JPasswordField("",40);
		
		TEXTipccm.setMaximumSize(new Dimension(50,25));
		TEXTlogin.setMaximumSize(new Dimension(50,25));
		TEXTPass.setMaximumSize(new Dimension(50,25));
		
		//Paramètres
		validation.setBackground(Color.LIGHT_GRAY);
		
		//Assignation
		InfoAndProgress.add(Box.createHorizontalGlue());
		InfoAndProgress.add(new JLabel("Etape 1 : Informations de connexion AXL au CallManager"));
		InfoAndProgress.add(Box.createHorizontalGlue());
		
		disposition.add(Box.createVerticalGlue());
		dispo1.add(new JLabel("Adresse IP du CCM"));
		dispo1.add(Box.createHorizontalGlue());
		dispo1.add(TEXTipccm);
		disposition.add(dispo1);
		disposition.add(Box.createVerticalGlue());
		dispo2.add(new JLabel("Login"));
		dispo2.add(Box.createHorizontalGlue());
		dispo2.add(TEXTlogin);
		disposition.add(dispo2);
		disposition.add(Box.createVerticalGlue());
		dispo3.add(new JLabel("Password"));
		dispo3.add(Box.createHorizontalGlue());
		dispo3.add(TEXTPass);
		disposition.add(dispo3);
		disposition.add(Box.createVerticalGlue());
		
		validation.add(Box.createHorizontalGlue());
		validation.add(getReportButton);
		validation.add(Box.createHorizontalGlue());
		
		Principale.add(Box.createVerticalGlue());
		Principale.add(disposition);
		Principale.add(Box.createVerticalGlue());
		Principale.add(validation);
		
		//Events
		getReportButton.addActionListener(this);
		
		/*
		 * Provisoire
		 */
		TEXTipccm.setText(MethodesUtiles.getTargetOption("ipccm"));
		TEXTlogin.setText(MethodesUtiles.getTargetOption("axlloginccm"));
		TEXTPass.setText(MethodesUtiles.getTargetOption("axlpassccm"));
		}

	public void actionPerformed(ActionEvent actev)
		{
		if(recupValeur())
			{
			MethodesUtiles.setTargetOption("ipccm",TEXTipccm.getText());
			MethodesUtiles.setTargetOption("axlloginccm",TEXTlogin.getText());
			MethodesUtiles.setTargetOption("axlpassccm",TEXTPass.getText());
			
			logGear.writeLog("IP callmanager : "+variables.getIPCCM()+" - AXL login : "+variables.getAxlLogin()+" - AXL pass : "+variables.getAxlPass(),this,146);
			
			if(actev.getSource() == this.getReportButton)
				{
				//Get report
				new getReport();
				}
			}
		}
		
	/*******************************************************
	 * Méthode qui récupère les valeurs et les placent dans
	 * leurs variables 
	 *******************************************************/
	public boolean recupValeur()
		{
		variables.setIPCCM(TEXTipccm.getText());
		variables.setAxlLogin(TEXTlogin.getText());
		variables.setAxlPass(TEXTPass.getText());
		
		if((testeur.IPValide(variables.getIPCCM())) && (variables.getAxlLogin().compareTo("") != 0) && (variables.getAxlPass().compareTo("") != 0))
			{
			return true;
			}
		else
			{
			JOptionPane.showMessageDialog(null,"Veuillez remplir correctement\nles champs","alert",JOptionPane.INFORMATION_MESSAGE);
			}
		return false;
		}
	
	/*2013*//*RATEL Alexandre 8)*/
	}
