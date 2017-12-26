package Aide;
//Import
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.border.TitledBorder;

import utils.Centrer;
import utils.variables;

import Fenetre.MainWindow;

public class WindowApropos extends JWindow implements MouseListener
	{
	/**************
	 * Variables
	 **************/
	int larg;
	int haut;
	
	//Disposition
	JPanel principale;
	JPanel logo;
	JPanel texte;
	
	/***************
	 * Constructeur
	 ***************/
	public WindowApropos(MainWindow MainW)
		{
		super(MainW);
		
		larg = 500;
		haut = 160;
		
		//Disposition
		principale = new JPanel();
		logo = new JPanel();
		texte = new JPanel();
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		principale.setLayout(new BoxLayout(principale,BoxLayout.X_AXIS));
		logo.setLayout(new BoxLayout(logo,BoxLayout.Y_AXIS));
		texte.setLayout(new BoxLayout(texte,BoxLayout.Y_AXIS));
		
		//Paramètres
		principale.setBackground(Color.white);
		texte.setBackground(Color.white);
		logo.setBackground(Color.white);
		principale.setBorder(new TitledBorder("A propos de : EX Tools"));
		
		//Assignation
		texte.add(new JLabel(" "));
		texte.add(new JLabel("  "+variables.getNomProg()));
		texte.add(new JLabel(" "));
		texte.add(new JLabel("  Version : "+variables.getVersion()));
		texte.add(new JLabel(" "));
		texte.add(new JLabel("  Auteur : Alexandre RATEL"));
		texte.add(new JLabel(" "));
		texte.add(new JLabel("  Contact : alexandre.ratel@gmail.com"));
		texte.add(new JLabel(" "));
		
		logo.add(new JLabel(" "));
		logo.add(Box.createVerticalGlue());
		logo.add(new JLabel(new ImageIcon(getClass().getResource("/Art/logo1.png"))));
		logo.add(Box.createVerticalGlue());
		logo.add(new JLabel(" "));
		
		principale.add(texte);
		principale.add(Box.createHorizontalGlue());
		principale.add(Box.createRigidArea(new Dimension(60,100)));
		principale.add(Box.createHorizontalGlue());
		principale.add(logo);
		
		this.getContentPane().add(principale);
		
		//setSize(larg,haut);
		this.pack();
		
		new Centrer(this);
		
		addMouseListener(this);
		
		this.setVisible(true);
		}

	public void mouseClicked(MouseEvent evt)
		{
		this.dispose();
		}

	public void mouseEntered(MouseEvent arg0)
		{
		// TODO Auto-generated method stub
		}

	public void mouseExited(MouseEvent arg0)
		{
		// TODO Auto-generated method stub
		}

	public void mousePressed(MouseEvent arg0)
		{
		// TODO Auto-generated method stub
		}

	public void mouseReleased(MouseEvent arg0)
		{
		// TODO Auto-generated method stub
		}
	
	/*Fin classe*/
	}
