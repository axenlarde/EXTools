package Outils;
//Import
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import utils.BasePanel;
import utils.testeur;

import Fenetre.MainWindow;
import Fenetre.Principale;



public class PingWindow extends BasePanel implements ActionListener
	{
	/**************
	 * Variables
	 **************/
	MainWindow MainW;
	String ipPing;
	int rows;
	
	//Contrôles
	JTextField TEXTIpPing;
	JButton valider;
	JSlider nbPing;
	JTextArea Information;
	
	//Disposition
	JPanel disposition;
	JPanel dispo1;
	JPanel validation;
	JScrollPane sp;
	
	/****************
	 * Constructeurs
	 ****************/
	public PingWindow(MainWindow MainW)
		{
		this.MainW = MainW;
		rows = 2;
		
		//Disposition
		disposition = new JPanel();
		dispo1 = new JPanel();
		validation = new JPanel();
		disposition.setLayout(new BoxLayout(disposition,BoxLayout.Y_AXIS));
		dispo1.setLayout(new BoxLayout(dispo1,BoxLayout.X_AXIS));
		validation.setLayout(new BoxLayout(validation,BoxLayout.X_AXIS));
		
		//Contrôles
		valider = new JButton("Lancer");
		TEXTIpPing = new JTextField("127.0.0.1");
		TEXTIpPing.setMaximumSize(new Dimension(300,50));
		nbPing = new JSlider(1,5,1);
		nbPing.setPaintTicks(true);
		nbPing.setMajorTickSpacing(1);
		nbPing.setPaintLabels( true );
		nbPing.setSnapToTicks( true );
		nbPing.setMaximumSize(new Dimension(300,50));
		
		Information = new JTextArea("Résultat :",5,30);
		Information.setPreferredSize(new Dimension(300,350));
		Information.setLineWrap(true);
		Information.setCaretPosition(Information.getDocument().getLength());
		sp = new JScrollPane(Information);
		
		//Paramètres
		validation.setBackground(Color.LIGHT_GRAY);
		TEXTIpPing.setToolTipText("Adresse à pinger");
		nbPing.setToolTipText("Nombre d'occurence");
		
		//Assignation
		InfoAndProgress.add(Box.createHorizontalGlue());
		InfoAndProgress.add(new JLabel("Test d'une adresse IP"));
		InfoAndProgress.add(Box.createHorizontalGlue());
		
		
		disposition.add(Box.createVerticalStrut(10));
		dispo1.add(TEXTIpPing);
		dispo1.add(nbPing);
		disposition.add(dispo1);
		disposition.add(Box.createVerticalStrut(10));
		disposition.add(sp);
		
		validation.add(Box.createHorizontalGlue());
		validation.add(valider);
		validation.add(Box.createHorizontalGlue());
		
		
		Principale.add(disposition);
		Principale.add(validation);
		
		//Events
		valider.addActionListener(this);
		
		
		
		}

	public void actionPerformed(ActionEvent evt)
		{
		if(evt.getSource() == this.valider)
			{
			System.out.println("Valider");
			
			if(testeur.IPValide(TEXTIpPing.getText()))
				{
				runScript("ping -n "+nbPing.getValue()+" "+TEXTIpPing.getText());
				}
			else
				{
				JOptionPane.showMessageDialog(null,"Format d'adresse IP incorrect","Format incorrect",JOptionPane.ERROR_MESSAGE);
				}
			}
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
			BufferedReader in = new BufferedReader(new InputStreamReader(resultPing.getInputStream(),"ibm850"));
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
