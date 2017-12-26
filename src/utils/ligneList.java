package utils;
//Import
import javax.swing.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.awt.*;


public class ligneList extends JPanel
	{
	/************
	 * variables
	 ************/
	int step;
	
	//Contrôle
	JLabel SEP;
	JLabel MacEnCours;
	JLabel Progress;
	JLabel Result;
	
	/***************
	 * Constructeur
	 ***************/
	public ligneList(String Mac, String profile)
		{
		step = 0;
		SEP = new JLabel(" SEP");
		MacEnCours = new JLabel(Mac+"  +>  "+profile);
		Progress = new JLabel(".");
		Result = new JLabel("waiting");
		
		//Disposition
		setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		
		//Paramètres
		
		//Assignation
		add(SEP);
		add(MacEnCours);
		add(Box.createHorizontalGlue());
		add(Result);
		add(Progress);
		//setSize(600,40);
		}
	
	public void nextStep()
		{
		switch(step)
			{
			case 0:
				Progress.setText(".");
				step = 1;
				break;
				
			case 1:
				Progress.setText("..");
				step = 2;
				break;
				
			case 2:
				Progress.setText("...");
				step = 0;
				break;
			}
		}
	
	public void setFond(Color couleur)
		{
		setBackground(couleur);
		}
	
	public void setProgress(String prog)
		{
		Progress.setText(prog);
		}

	public String getResult()
		{
		return Result.getText();
		}

	public void setResult(String result)
		{
		Result.setText(result);
		}
	
	/*Fin classe*/
	}
