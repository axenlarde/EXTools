package Action;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import utils.MethodesUtiles;
import utils.SOAPGear;
import utils.logGear;
import utils.variables;

/*********************************************
 * This class have to manage report generation
 ********************************************/
public class getReport
	{
	/**
	 * Variables
	 */
	private SOAPGear sg;
	private ArrayList<loginReportLine> lrlList;
	private JFileChooser fcSource;
	private String emplacementSource;
	
	private WritableWorkbook monWorkbook;
	private WritableSheet sheet;
	private WritableFont arial10font;
	private WritableFont arial10font2;
	private WritableCellFormat TitleFormat;
	private WritableCellFormat arial10format;
	
	//Request
	private final String req = "select u.userid as userid,p.name as profile,d.name as device from enduser u,device p,device d,extensionmobilitydynamic em where em.fkdevice=d.pkid and em.fkdevice_currentloginprofile=p.pkid and em.fkenduser=u.pkid"; 
	
	/***************
	 * Constructeur
	 ***************/
	public getReport()
		{
		fcSource = new JFileChooser();
		
		try
			{
			//On r�cup�re l'emplacement de destination du rapport
			RecupFichier();
			
			//Initiation de la connexion AXL
			sg = new SOAPGear(variables.getAxlPort(),variables.getIPCCM(),variables.getAxlLogin(),variables.getAxlPass());
			
			//Requete CUCM
			lrlList = getEMList();
			
			//Construction du fichier Excel
			createExcelFile();
			
			//Fermeture de la connexion
			sg.closeCon();
			
			//Rapport r�cup�r� avec succ�s
			variables.getLogger().info("Rapport r�cup�r� avec succ�s");
			JOptionPane.showMessageDialog(null,"Rapport r�cup�r� avec succ�s","Succes",JOptionPane.INFORMATION_MESSAGE);
			//On reset la fen�tre
			variables.getMainW().getContentPane().removeAll();
			variables.getMainW().getContentPane().add(new JPanel());
			variables.getMainW().validate();
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc);
			JOptionPane.showMessageDialog(null,"L'erreur suivante est survenue : "+exc.getMessage()+"\r\n\r\nV�rifiez l'adresse IP et les informations de connexions","Alert",JOptionPane.ERROR_MESSAGE);
			}
		}
	
	
	
	/**********************************************************************
	 * Method used to get the content of extensionmobilitydynamic table in
	 * the CUCM database
	 **********************************************************************/
	private ArrayList<loginReportLine> getEMList() throws SOAPException
		{
		MessageFactory mf = MessageFactory.newInstance();
		SOAPFactory soapFactory = SOAPFactory.newInstance();
	    SOAPMessage soapMessage = mf.createMessage();
	    MimeHeaders headers = soapMessage.getMimeHeaders();
	    headers.addHeader("SOAPAction", " CUCM:DB ver="+variables.getVersionCCM()+".0");
	    SOAPEnvelope envelope = soapMessage.getSOAPPart().getEnvelope();
	    SOAPBody bdy = envelope.getBody();
	    SOAPBodyElement bodyElement = bdy.addBodyElement(soapFactory.createName("executeSQLQuery","axl","http://www.cisco.com/AXL/API/"+variables.getVersionCCM()+".0"));
	    bodyElement.addAttribute(envelope.createName("sequence"), String.valueOf(System.currentTimeMillis()));
	    
	    bodyElement.addChildElement("sql").addTextNode(req);
	    
		SOAPMessage soapAnswer = sg.execute(soapMessage);
		SOAPPart replySP = soapAnswer.getSOAPPart();
	    SOAPEnvelope replySE = replySP.getEnvelope();
	    SOAPBody replySB = replySE.getBody();
	    
	    if (replySB.hasFault())
	    	{
	    	throw new SOAPException(replySB.getFault().getFaultString());
	    	}
	    else
	    	{
	    	ArrayList<loginReportLine> List = new ArrayList<loginReportLine>();
	    	
	    	Iterator iterator = replySB.getChildElements();
	    	SOAPBodyElement bodyEle = (SOAPBodyElement)iterator.next();
	    	//return
	    	Iterator ite = bodyEle.getChildElements();
	    	SOAPBodyElement bodyElem = (SOAPBodyElement)ite.next();
	    	//Element type
	    	Iterator iter = bodyElem.getChildElements();
	    	while(iter.hasNext())
	    		{
	    		SOAPBodyElement bodyEleme = (SOAPBodyElement)iter.next();
	    		Iterator itera = bodyEleme.getChildElements();
	    		
	    		String userId = new String();
	    		String device = new String();
	    		String profile = new String();
	    		
	    		while(itera.hasNext())
	    			{
	    			SOAPBodyElement bodyElemen = (SOAPBodyElement)itera.next();
	    			if(bodyElemen.getNodeName().compareTo("userid") == 0)
	    				{
	    				userId = bodyElemen.getTextContent();
	    				}
	    			else if(bodyElemen.getNodeName().compareTo("device") == 0)
	    				{
	    				device = bodyElemen.getTextContent();
	    				}
	    			else if(bodyElemen.getNodeName().compareTo("profile") == 0)
	    				{
	    				profile = bodyElemen.getTextContent();
	    				}
	    			}
	    		loginReportLine line = new loginReportLine(userId, device, profile);
	    		List.add(line);
	    		}
	    	return List;
	    	}
		}
	
	/****************************************************
	 * M�thode qui r�up�re le r�pertoire de destination via
	 * un JFileChooser
	 ****************************************************/
	public void RecupFichier() throws Exception
		{
		fcSource.setCurrentDirectory(new File(MethodesUtiles.getTargetOption("emplacementbase")));
		fcSource.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int resultat = fcSource.showDialog(fcSource, "R�pertoire de destination du rapport");
		if(resultat == fcSource.APPROVE_OPTION)
			{
			emplacementSource = fcSource.getSelectedFile().toString();
			MethodesUtiles.setTargetOption("emplacementbase", emplacementSource);
			}
		else
			{
			JOptionPane.showMessageDialog(null,"Veuillez s�lectionner un r�pertoire de destination","alert",JOptionPane.INFORMATION_MESSAGE);
			throw new Exception("CancelSelectFichier");
			}
		}
	
	/***********************************************
	 * Methode qui cr�er la feuille excel d'exemple
	 ***********************************************/
	public void createExcelFile()
		{
		try
			{
			logGear.writeLog("Emplacement du fichier Excel : "+emplacementSource,this,143);
			monWorkbook = Workbook.createWorkbook(new File(emplacementSource+"\\LoggedINReport.xls"));
			logGear.writeLog("Fichier Excel correctement initialis�",this,144);
			
			sheet = monWorkbook.createSheet("Rapport", 0);
			
			//D�finition des formats
			arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
			arial10font.setColour(Colour.WHITE);
			arial10font2 = new WritableFont(WritableFont.ARIAL, 10);
			arial10font2.setColour(Colour.BLACK);
			
			TitleFormat = new WritableCellFormat(arial10font);
			TitleFormat.setBackground(Colour.DARK_BLUE);
			
			arial10format = new WritableCellFormat(arial10font2);
			
			/***************
			 * Ecriture
			 ***************/
			//Titre
			sheet.addCell(new Label(0, 0, "MAC", TitleFormat));
			sheet.addCell(new Label(1, 0, "UserID", TitleFormat));
			sheet.addCell(new Label(2, 0, "Profile", TitleFormat));
			
			//Ecriture des donn�es
			for(int i=0; i<lrlList.size(); i++)
				{
				sheet.addCell(new Label(0, i+1, lrlList.get(i).getDevice().replace("SEP", ""), arial10format));
				sheet.addCell(new Label(1, i+1, lrlList.get(i).getUserId(), arial10format));
				sheet.addCell(new Label(2, i+1, lrlList.get(i).getProfile(), arial10format));
				}
			
			sheet.addCell(new Label(0, lrlList.size()+1, "#", arial10format));
			sheet.addCell(new Label(1, lrlList.size()+1, "#", arial10format));
			sheet.addCell(new Label(2, lrlList.size()+1, "#", arial10format));
			
			monWorkbook.write();
			logGear.writeLog("Fichier Excel correctement �crit",this,173);
			}
		catch(Exception e)
			{
			variables.getLogger().error(e);
			e.printStackTrace();
			}
		finally
			{
			try
				{
				monWorkbook.close();
				variables.getLogger().info("fichier "+emplacementSource+"\\LoggedINReport.xls ferm�");
				}
			catch(Exception e)
				{
				variables.getLogger().error("Le fichier ne peut pas �tre ferm� car il n'est pas ou plus ouvert :(");
				e.printStackTrace();
				}
			}
		}
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}
