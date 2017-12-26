package Action;

/*********************************************
 * Class used to store one report line
 ********************************************/
public class loginReportLine
	{
	/**
	 * Variables
	 */
	private String userId;
	private String device;
	private String profile;
	
	/***************
	 * Constructeur
	 ***************/
	public loginReportLine(String userId, String device, String profile)
		{
		this.userId = userId;
		this.device = device;
		this.profile = profile;
		}

	public String getUserId()
		{
		return userId;
		}

	public void setUserId(String userid)
		{
		this.userId = userid;
		}

	public String getDevice()
		{
		return device;
		}

	public void setDevice(String device)
		{
		this.device = device;
		}

	public String getProfile()
		{
		return profile;
		}

	public void setProfile(String profile)
		{
		this.profile = profile;
		}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}
