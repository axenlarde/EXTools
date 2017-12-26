package utils;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
//Import



public abstract class BasePanel extends JPanel
	{
	/************
	 * Variables
	 ************/
	private static final long serialVersionUID = 1L;
	
	//Disposition
	public JPanel InfoAndProgress;
	public JPanel Principale;
	
	/***************
	 * Constructeur
	 ***************/
	public BasePanel()
		{
		//Disposition
		InfoAndProgress = new JPanel();
		Principale = new JPanel();
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		InfoAndProgress.setLayout(new BoxLayout(InfoAndProgress,BoxLayout.X_AXIS));
		Principale.setLayout(new BoxLayout(Principale,BoxLayout.Y_AXIS));
		
		//Paramètres
		InfoAndProgress.setPreferredSize(new Dimension(600,40));
		InfoAndProgress.setBackground(Color.LIGHT_GRAY);
		
		//Assignation
		this.add(InfoAndProgress);
		this.add(Principale);
		}
	
	
	/*Fin classe*/
	}
