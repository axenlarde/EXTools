package Action;

/*********
 * Import
 *********/
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import utils.BasePanel;
import utils.MethodesUtiles;
import utils.logGear;
import utils.testeur;
import utils.variables;

import Fenetre.MainWindow;
import Fenetre.Principale;

public class RecupInfoProfile extends BasePanel implements ActionListener
	{
	/**************
	 * Variables
	 **************/
	MainWindow MainW;
	String mac;
	String userID;
	String profile;
	String[] InfoDevice;
	ArrayList<String[]> ListeDevice;
	
	
	//Contrôles
	JTextField TEXTmac;
	JTextField TEXTUserID;
	JTextField TEXTProfile;
	JButton valider;
	
	//Disposition
	JPanel disposition;
	JPanel dispo1;
	JPanel dispo2;
	JPanel dispo3;
	JPanel validation;
	
	/***************
	 * Constructeur
	 ***************/
	public RecupInfoProfile()
		{
		this.MainW = variables.getMainW();
		InfoDevice = new String[7];
		ListeDevice = new ArrayList<String[]>();
		
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
		disposition.setBorder(new TitledBorder("Log/Logout simple")); 
		
		//Contrôles
		valider = new JButton("Valider");
		TEXTmac = new JTextField("",40);
		TEXTmac.setMaximumSize(new Dimension(50,25));
		TEXTUserID = new JTextField("",40);
		TEXTUserID.setMaximumSize(new Dimension(50,25));
		TEXTProfile = new JTextField("",40);
		TEXTProfile.setMaximumSize(new Dimension(50,25));
		
		//Paramètres
		validation.setBackground(Color.LIGHT_GRAY);
		
		//Assignation
		InfoAndProgress.add(Box.createHorizontalGlue());
		InfoAndProgress.add(new JLabel("Etape 1 : Information sur le profile"));
		InfoAndProgress.add(Box.createHorizontalGlue());
		
		disposition.add(Box.createVerticalGlue());
		dispo1.add(new JLabel(" Adresse Mac du téléphone :"));
		dispo1.add(Box.createHorizontalGlue());
		dispo1.add(TEXTmac);
		disposition.add(dispo1);
		disposition.add(Box.createVerticalGlue());
		dispo2.add(new JLabel(" UserID à connecter :"));
		dispo2.add(Box.createHorizontalGlue());
		dispo2.add(TEXTUserID);
		disposition.add(dispo2);
		disposition.add(Box.createVerticalGlue());
		dispo3.add(new JLabel(" Profil à connecter (optionnel) :"));
		dispo3.add(Box.createHorizontalGlue());
		dispo3.add(TEXTProfile);
		disposition.add(dispo3);
		disposition.add(Box.createVerticalGlue());
		
		
		validation.add(Box.createHorizontalGlue());
		validation.add(valider);
		validation.add(Box.createHorizontalGlue());
		
		Principale.add(Box.createVerticalGlue());
		Principale.add(disposition);
		Principale.add(Box.createVerticalGlue());
		Principale.add(validation);
		
		//Events
		valider.addActionListener(this);
		
		/*
		 * Provisoire
		 */
		TEXTmac.setText(MethodesUtiles.getTargetOption("maclogunique"));
		TEXTUserID.setText(MethodesUtiles.getTargetOption("useridlogunique"));
		TEXTProfile.setText(MethodesUtiles.getTargetOption("profilelogunique"));
		}
		
		
	public void actionPerformed(ActionEvent actev)
		{
		if(recupValeur())
			{
			MethodesUtiles.setTargetOption("maclogunique",TEXTmac.getText());
			MethodesUtiles.setTargetOption("useridlogunique",TEXTUserID.getText());
			MethodesUtiles.setTargetOption("profilelogunique",TEXTProfile.getText());
			
			variables.setListeDevice(ListeDevice);
			logGear.writeLog("MAC : "+mac+" - UserID : "+userID+" - Profile : "+profile,this,126);
			MainW.getContentPane().removeAll();
			MainW.getContentPane().add(new RecupInfoCCM(false));
			MainW.validate();
			}
		}
	
	/*******************************************************
	 * Méthode qui récupère les valeurs et les placent dans
	 * leurs variables 
	 *******************************************************/
	public boolean recupValeur()
		{
		mac = TEXTmac.getText();
		userID = TEXTUserID.getText();
		profile = TEXTProfile.getText();
		
		if(testeur.macValide(mac))
			{
			if(userID.compareTo("") != 0)
				{
				InfoDevice[0] = "";
				InfoDevice[1] = "";
				InfoDevice[2] = "";
				InfoDevice[3] = mac;
				InfoDevice[4] = userID;
				InfoDevice[5] = profile;
				InfoDevice[6] = null;
				ListeDevice.add(InfoDevice);
				return true;
				}
			else
				{
				JOptionPane.showMessageDialog(null,"Profile incorrect","alert",JOptionPane.ERROR_MESSAGE);
				}
			}
		else
			{
			JOptionPane.showMessageDialog(null,"Adresse MAC incorrect","alert",JOptionPane.ERROR_MESSAGE);
			}
		return false;
		}
	
	}
