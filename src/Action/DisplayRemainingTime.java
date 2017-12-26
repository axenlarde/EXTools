package Action;

import java.text.SimpleDateFormat;
import java.util.Date;

import utils.variables;

import Fenetre.MainWindow;

/**********************************
 * Class used to display the remaining time
 * 
 * @author RATEL Alexandre
 **********************************/
public class DisplayRemainingTime extends Thread
	{
	/**
	 * Variables
	 */
	private MainWindow MainW;
	private Date scheduledDate;
	private String remainingTime;
	private Date now;
	private Date remain;
	private SimpleDateFormat formatTime;
	
	/***************
	 * Constructor
	 ***************/
	public DisplayRemainingTime(Date scheduledDate, String remainingTime)
		{
		super();
		this.scheduledDate = scheduledDate;
		this.remainingTime = remainingTime;
		this.MainW = variables.getMainW();
		
		now = new Date();
		//remain = scheduledDate.;
		
		formatTime = new SimpleDateFormat("HH':'mm");
		
		
		
		start();
		}
	
	public void run()
		{
		
		}
	
	
	
	
	
	/*2016*//*RATEL Alexandre 8)*/
	}

