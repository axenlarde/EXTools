package Action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**********************************
 * Class aims to start a task on time
 * 
 * @author RATEL Alexandre
 **********************************/
public class Scheduler extends Thread
	{
	/**
	 * Variables
	 */
	private boolean done;
	private Date now;
	private SimpleDateFormat dateFormat;
	private String currentHour;
	private String scheduledDate;
	private String scheduledTime;
	
	/**
	 * Constructor
	 */
	public Scheduler(String scheduledDate, String scheduledTime)
		{
		done = false;
		this.scheduledDate = scheduledDate;
		this.scheduledTime = scheduledTime;
		start();
		}
	
	
	
	public void run()
		{
		/**
		 * We now want to know if it is time to start the task
		 */
		while(!done)
			{
			try
				{
				now = new Date();
				dateFormat = new SimpleDateFormat("dd/MM/yyyy HH':'mm",Locale.FRANCE);
				currentHour = dateFormat.format(now);
				
				String launchDate = scheduledDate+" "+scheduledTime;
				
				if(currentHour.equals(launchDate))
					{
					utils.variables.getLogger().info("Time to start");
					new Logueur();
					done = true;
					}
				else
					{
					this.sleep(1000);//We sleep 1 second
					}
				}
			catch (Exception e)
				{
				utils.variables.getLogger().error(e.getMessage(),e);
				}
			}
		utils.variables.getLogger().info("Scheduler stopped");
		}
	
	/**
	 * Method used to stop the thread
	 */
	public void interrupt()
		{
		utils.variables.getLogger().info("Scheduler stopping");
		this.done = true;
		}
	
	/*2016*//*RATEL Alexandre 8)*/
	}

