package Action;

//Import
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

import utils.BasePanel;
import utils.GreenFire;
import utils.ligneList;
import utils.logGear;

import Fenetre.Principale;



public class FenetreProgress extends BasePanel implements ActionListener 
	{
	/************
	 * Variables
	 ************/
	String IPCCM;
	
	//Contrôles
	JLabel Info;
	JProgressBar avancement;
	JButton pause;
	JButton stop;
	
	//Disposition
	JPanel listTel;
	JScrollPane scrollbar ;
	
	/***************
	 * Constructeur
	 ***************/
	public FenetreProgress(String IPCCM)
		{
		this.IPCCM = IPCCM;
		
		//Contrôles
		Info = new JLabel(" 0%");
		avancement = new JProgressBar(0,100);
		pause = new JButton(new ImageIcon(getClass().getResource("/Art/pause.png")));
		stop = new JButton(new ImageIcon(getClass().getResource("/Art/stop.png")));
		
		//Disposition
		listTel = new JPanel();
		listTel.setLayout(new BoxLayout(listTel,BoxLayout.Y_AXIS));
		scrollbar = new JScrollPane(listTel);
		
		//Assignation
		InfoAndProgress.add(Box.createHorizontalGlue());
		InfoAndProgress.add(new JLabel("Adresse CCM : "+this.IPCCM));
		InfoAndProgress.add(Box.createHorizontalGlue());
		InfoAndProgress.add(avancement);
		InfoAndProgress.add(Info);
		InfoAndProgress.add(Box.createHorizontalGlue());
		InfoAndProgress.add(pause);
		InfoAndProgress.add(Box.createHorizontalGlue());
		InfoAndProgress.add(stop);
		InfoAndProgress.add(Box.createHorizontalGlue());
		
		//Events
		pause.addActionListener(this);
		stop.addActionListener(this);
		
		Principale.add(scrollbar);
		}
	
	/***********************************************
	 * Methode qui remplie le centre de la fenetre
	 * avec une ligne pour chaque device
	 ***********************************************/
	public void remplissage(ArrayList<ligneList> listeLigne)
		{
		listTel.removeAll();
		for(int i=0; i<listeLigne.size(); i++)
			{
			listTel.add(listeLigne.get(i));
			}
		}
	
	public void setInfo(String info, String reste)
		{
		Info.setText(" "+info+"% "+reste);
		avancement.setValue(Integer.parseInt(info));
		}

	public void actionPerformed(ActionEvent evt)
		{
		if(evt.getSource() == this.pause)
			{
			if(GreenFire.getGo())
				{
				GreenFire.setGo(false);
				pause.setIcon(new ImageIcon(getClass().getResource("/Art/play.png")));
				logGear.writeLog("Mise en pause",this,107);
				}
			else
				{
				GreenFire.setGo(true);
				pause.setIcon(new ImageIcon(getClass().getResource("/Art/pause.png")));
				logGear.writeLog("Mise en marche (play)",this,113);
				}
			}
		if(evt.getSource() == this.stop)
			{
			GreenFire.setGo(false);
			GreenFire.setStop(false);
			logGear.writeLog("Arret (stop)",this,120);
			}
		}
	
	/*Fin classe*/
	}
