package jaist.ac.jp.XMLParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jaist.ac.jp.XMLParser.obj.DeviceObject;
import jaist.ac.jp.XMLParser.obj.EOJ;
import jaist.ac.jp.XMLParser.obj.EPC;

/**
 * Hello world!
 *
 */
public class App 
{
	public static String filePath = "/Users/cupv/Documents/iHouseSim/xml";
	public static String rootPath = "/Users/cupv/Documents/iHouseSim/data";
	public static String extension = ".*\\.xml";
	public static String IP;
    public static void main( String[] args )
    {
    	
    	Scanner input = new Scanner(System.in);
    	System.out.println("Enter the absolute path of the folder containing xml files: ");
    	filePath = input.nextLine();
    	System.out.println("Enter the absolute path of the folder to store config files: ");
    	rootPath = input.nextLine()+"/data";
    	
    	List<DeviceObject> devs = new ArrayList<DeviceObject>();
    	IP = "";
    	List<String> files = Util.loadConfigFile(filePath, extension);
    	for(int i = 0; i< files.size(); i++) {
    		//Util.xmlDocumentFromFile(files.get(i));
    		devs.add(Util.deviceObjectsFromXMLDocument(Util.xmlDocumentFromFile(files.get(i))));
    	}
    	
    	for(DeviceObject dev : devs) {
	    	String rootDir= rootPath + File.separator + dev.getIP();
	    	Util.createDirectory(rootDir);
	    	for(int i = 0; i <dev.getProperties().size(); i++) {
	    		EOJ eoj = dev.getProperties().get(i);
	    		String eojDir = rootDir + File.separator + eoj.getEOJ_Code();
	    		Util.createDirectory(eojDir);
	    		for(int j = 0; j<eoj.getEpcList().size(); j++ ) {		
	    			EPC epc  = eoj.getEpcList().get(j);
	    			String EPCDir = eojDir +File.separator+ epc.getCode();
	    			Util.createDirectory(EPCDir);
	    			for(int k =0; k< epc.getFilename().size(); k++) {
	    				String filepath = EPCDir + File.separator + epc.getFilename().get(k).substring(1);
	    				Util.createFile(filepath);
	    				Util.writeToFile(filepath, epc.getInitvalue());
	    			}
	    		}
	    	}
	    }
    	
    	System.out.println("Task completed: created config file for " +devs.size() + " ip");
    	input.close();
    }
    
}
