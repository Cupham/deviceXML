package jaist.ac.jp.XMLParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jaist.ac.jp.XMLParser.obj.DeviceObject;
import jaist.ac.jp.XMLParser.obj.EOJ;
import jaist.ac.jp.XMLParser.obj.EPC;

public class Util {
	public static List<String >loadConfigFile(String path, String fileExtension) {
		List<String> rs = new ArrayList<String>();
		final File folder = new File(path);
		for(File f :folder.listFiles()) {
			if(f.isFile()) {
				if (f.getName().matches(fileExtension)) {
					rs.add(f.getAbsolutePath());
                }
			} else if(f.isDirectory()) {
				loadConfigFile(f.getAbsolutePath(), fileExtension);
			}
		}
		return rs;	
	}
	
	public static Document xmlDocumentFromFile(String filePath) {
		File xmlFile = new File(filePath);
		App.IP = xmlFile.getName().replace(".xml", "");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document doc = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(builder != null) {
			try {
				System.out.println("Parsing file: " + filePath);
				doc = (Document) builder.parse(xmlFile);
			} catch (SAXException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Can not open file: " + filePath);
		}
		if(doc != null) {
			return doc;
		} else {
			System.out.println("can not parse document");
			return null;
		}
		
	}
	
	public static DeviceObject deviceObjectsFromXMLDocument(Document doc) {
		DeviceObject dev = new DeviceObject();
		dev.setIP(App.IP);
		NodeList devObj  = doc.getElementsByTagName("object");
		for(int i = 0 ; i<devObj.getLength();i++) {
			Node obj = devObj.item(i);
			if(obj.getNodeType() == Node.ELEMENT_NODE) {
				EOJ eoj = new EOJ();
				Element objElement = (Element) obj;
				String fourdigitEOJ =objElement.getAttribute("ceoj").toString();
				eoj.setEOJ_Code(makeFullEOJ(fourdigitEOJ,i+1));
				NodeList properties = objElement.getElementsByTagName("property");
				for(int j = 0; j< properties.getLength();j++) {
					Node epcNode = properties.item(j);
					if(epcNode.getNodeType() == Node.ELEMENT_NODE) {
						// init EPC
						Element epcElement = (Element) epcNode;
						EPC epc = new EPC();
						epc.setCode(epcElement.getAttribute("epc"));
						
						NodeList files = epcElement.getElementsByTagName("file");
						for(int k =0;k<files.getLength();k++) {
							Node file = files.item(k);
							if(file.getNodeType()==Node.ELEMENT_NODE) {
								Element fileElement = (Element) file;
								
								Node value = fileElement.getElementsByTagName("value").item(0);
								Element defvalue = (Element) value;
								epc.setInitvalue(defvalue.getAttribute("default"));
								if(value != null) {
									epc.appendAFile(value.getTextContent().split("/")[2]);
									String ip = value.getTextContent().split("/")[0];
				
									if(!ip.equals(dev.getIP())) {
										System.out.println("------------errorrrrrr00000000000000 at "+dev.getIP() + " IP from file" + ip);
									}

									String eojCode= value.getTextContent().split("/")[1];
									eoj.setEOJ_Code(eojCode);
									if(!fourdigitEOJ.equals(eojCode.substring(0, 4))) {
										System.out.println("------------errorrrrrr00000000000000 at "+dev.getIP() +fourdigitEOJ + " IP from file" + ip + eojCode);
									}
								}
								Node notify = fileElement.getElementsByTagName("notify").item(0);
								if(notify != null) {
									epc.appendAFile(notify.getTextContent().split("/")[2]);
								}
								Node block = fileElement.getElementsByTagName("block").item(0);
								if(block != null) {
									epc.appendAFile(block.getTextContent().split("/")[2]);
								}
							}
							
						}
						
						eoj.appendAnEPC(epc);
					}
				}
				dev.appendEOJ(eoj);
			}
			
		}
		
		return dev;
		
	}
	
	private static String makeFullEOJ(String shortEOJ, int order) {
		return shortEOJ + 0 +"" +  Integer.toHexString(order);
		
	}
	
	public static boolean createFile(String filePath) {
		 //Create file under new directory path C:\newDirectory\directory
		boolean isCreated= false;
        File newFile = new File(filePath);
        //Create new file under specified directory
        try {
			isCreated = newFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (isCreated) {
            try {
				System.out.printf("2. Successfully created new file, path:%s\n",
				        newFile.getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else { //File may already exist
            System.out.println("2. File existed! nothing to do");
        }
        return isCreated;
	}
	public static boolean writeToFile(String fileURL, String value) {
		boolean rs= false;
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(fileURL);
			rs = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rs  = false;
		}
		if(outputStream!= null) {
		    try {
				outputStream.write(value.getBytes());
				System.out.println("Writing " + value + " to " + fileURL);
				rs = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				rs  = false;
			}
		 
		    try {
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				rs  = false;
			}
		} else {
			rs  = false;
		}
		return rs;
	}
	public static boolean createDirectory(String dirPath) {
		
        File newDirectory = new File(dirPath);
        //Create directory for non existed path.
        boolean isCreated = newDirectory.mkdirs();
        if (isCreated) {
            try {
				System.out.printf("1. Successfully created directories, path:%s \n",
				        newDirectory.getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else if (newDirectory.exists()) {
            try {
				System.out.printf("1. Directory path already exist, path:%s \n",
				        newDirectory.getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else {
            System.out.println("1. Unable to create directory");
        }
        return isCreated;
	}

}
