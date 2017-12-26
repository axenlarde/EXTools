package Action;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import Fenetre.MainWindow;

import utils.BasePanel;
import utils.variables;

/**********************************
 * Class used to set the date and time
 * used to plan a task
 * 
 * @author RATEL Alexandre
 **********************************/
public class ScheduleWindow extends BasePanel implements ActionListener
	{
	/**
	 * Variables
	 */
	private MainWindow MainW;
	private ArrayList<String[]> ListeDevice;
	
	//Contrôles
	private JTextField TEXTDate;
	private JTextField TEXTTime;
	private JButton validate;
	
	//Disposition
	private JPanel disposition;
	private JPanel dispo1;
	private JPanel dispo2;
	private JPanel validation;
	
	//Misc
	private Date now;
	private SimpleDateFormat formatDate;
	private SimpleDateFormat formatTime;
	
	
	/***************
	 * Constructor
	 ***************/
	public ScheduleWindow()
		{
		super();
		this.MainW = variables.getMainW();
		this.ListeDevice = variables.getListeDevice();
		now = new Date();
		formatDate = new SimpleDateFormat("dd/MM/yyyy");
		formatTime = new SimpleDateFormat("HH':'mm");
		
		//Disposition
		disposition = new JPanel();
		dispo1 = new JPanel();
		dispo2 = new JPanel();
		validation = new JPanel();
		disposition.setLayout(new BoxLayout(disposition,BoxLayout.Y_AXIS));
		dispo1.setLayout(new BoxLayout(dispo1,BoxLayout.X_AXIS));
		dispo2.setLayout(new BoxLayout(dispo2,BoxLayout.X_AXIS));
		validation.setLayout(new BoxLayout(validation,BoxLayout.X_AXIS));
		disposition.setBorder(new TitledBorder("Date et heure de lancement"));
		
		//Contrôles
		TEXTDate = new JTextField(formatDate.format(now),40);
		TEXTTime = new JTextField(formatTime.format(now),40);
		TEXTDate.setMaximumSize(new Dimension(50,25));
		TEXTTime.setMaximumSize(new Dimension(50,25));
		validate = new JButton("Planifier !");
		
		//Paramètres
		validation.setBackground(Color.LIGHT_GRAY);
		
		//Assignation
		InfoAndProgress.add(Box.createHorizontalGlue());
		InfoAndProgress.add(new JLabel("Etape 3 : planifier la tâche"));
		InfoAndProgress.add(Box.createHorizontalGlue());
		InfoAndProgress.add(new JLabel("Nombre de profiles : "+ListeDevice.size()));
		InfoAndProgress.add(Box.createHorizontalGlue());
		
		disposition.add(Box.createVerticalGlue());
		dispo1.add(new JLabel("Date : "));
		dispo1.add(Box.createHorizontalGlue());
		dispo1.add(TEXTDate);
		dispo1.add(Box.createHorizontalGlue());
		dispo1.add(new JLabel("(JJ/MM/AAAA)"));
		dispo1.add(Box.createHorizontalGlue());
		disposition.add(dispo1);
		disposition.add(Box.createVerticalGlue());
		dispo2.add(new JLabel("Heure : "));
		dispo2.add(Box.createHorizontalGlue());
		dispo2.add(TEXTTime);
		dispo2.add(Box.createHorizontalGlue());
		dispo2.add(new JLabel("(HH:MM 24h)"));
		dispo2.add(Box.createHorizontalGlue());
		disposition.add(dispo2);
		disposition.add(Box.createVerticalGlue());
		
		validation.add(Box.createHorizontalGlue());
		validation.add(validate);
		validation.add(Box.createHorizontalGlue());
		
		Principale.add(Box.createVerticalGlue());
		Principale.add(disposition);
		Principale.add(Box.createVerticalGlue());
		Principale.add(validation);
		
		//Events
		validate.addActionListener(this);
		}
	
	
	
	
	
	
	public void actionPerformed(ActionEvent evt)
		{
		if(evt.getSource() == this.validate)
			{
			utils.variables.getLogger().debug("validate button pressed");
			MainW.getContentPane().removeAll();
			MainW.getContentPane().add(new ManageSchedule(TEXTDate.getText(), TEXTTime.getText()));
			MainW.validate();
			}
		}
	
	/*2016*//*RATEL Alexandre 8)*/
	}

