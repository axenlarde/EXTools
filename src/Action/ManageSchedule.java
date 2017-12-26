package Action;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Fenetre.MainWindow;

import utils.BasePanel;
import utils.variables;

/**********************************
 * Class used to manage a task
 * 
 * @author RATEL Alexandre
 **********************************/
public class ManageSchedule extends BasePanel implements ActionListener
	{
	/**
	 * Variables
	 */
	private MainWindow MainW;
	private ArrayList<String[]> ListeDevice;
	private String scheduledDate;
	private String scheduledTime;
	public String remainingTime;
	private Scheduler mySchedule;
	
	//Control
	private JButton cancelTask;
	private JButton startTaskNow;
	
	//Disposition
	private JPanel disposition;
	private JPanel dispo1;
	private JPanel dispo2;
	private JPanel validation;
	
	
	/***************
	 * Constructor
	 ***************/
	public ManageSchedule(String scheduledDate, String scheduledTime)
		{
		super();
		this.MainW = variables.getMainW();
		this.ListeDevice = variables.getListeDevice();
		this.scheduledDate = scheduledDate;
		this.scheduledTime = scheduledTime;
		this.remainingTime = new String("");
		
		/**
		 * First We launch the scheduler aims to start
		 * the task once time is over
		 */
		mySchedule = new Scheduler(scheduledDate, scheduledTime);
		
		/**
		 * Then we display the current time remaining
		 */
		//Disposition
		disposition = new JPanel();
		dispo1 = new JPanel();
		validation = new JPanel();
		disposition.setLayout(new BoxLayout(disposition,BoxLayout.Y_AXIS));
		dispo1.setLayout(new BoxLayout(dispo1,BoxLayout.Y_AXIS));
		validation.setLayout(new BoxLayout(validation,BoxLayout.X_AXIS));
		disposition.setBorder(new TitledBorder("Résumé de la tâche planifiée"));
		
		cancelTask = new JButton("Annuler la tâche");
		startTaskNow = new JButton("Lancer maintenant");
		
		validation.setBackground(Color.LIGHT_GRAY);
		
		InfoAndProgress.add(Box.createHorizontalGlue());
		InfoAndProgress.add(new JLabel("Tâche actuelle"));
		InfoAndProgress.add(Box.createHorizontalGlue());
		
		disposition.add(Box.createVerticalGlue());
		dispo1.add(new JLabel("Nombre de profiles : "+ListeDevice.size()));
		dispo1.add(new JLabel("Heure de lancement : "+this.scheduledDate+" "+this.scheduledTime));
		dispo1.add(new JLabel("Temps restant avant lancement : "+remainingTime));
		dispo1.add(Box.createVerticalGlue());
		disposition.add(dispo1);
		disposition.add(Box.createVerticalGlue());
		
		validation.add(Box.createHorizontalGlue());
		validation.add(startTaskNow);
		validation.add(Box.createHorizontalGlue());
		validation.add(cancelTask);
		validation.add(Box.createHorizontalGlue());
		
		Principale.add(Box.createVerticalGlue());
		Principale.add(disposition);
		Principale.add(Box.createVerticalGlue());
		Principale.add(validation);
		
		//Events
		startTaskNow.addActionListener(this);
		cancelTask.addActionListener(this);
		}


	public void actionPerformed(ActionEvent evt)
		{
		if(evt.getSource() == this.startTaskNow)
			{
			mySchedule.interrupt();
			new Logueur();
			}
		else if(evt.getSource() == this.cancelTask)
			{
			System.exit(0);
			}
		}
	
	/*2016*//*RATEL Alexandre 8)*/
	}

