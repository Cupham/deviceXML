package jaist.ac.jp.XMLParser.obj;

import java.util.ArrayList;
import java.util.List;

public class DeviceObject {
	public DeviceObject() {
		properties = new ArrayList<EOJ>();
	};
	public List<EOJ> getProperties() {
		return properties;
	}
	public void setProperties(List<EOJ> epcs) {
		this.properties = epcs;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public void appendEOJ(EOJ e) {
		this.properties.add(e);
	}
	private String IP;
	private List<EOJ> properties;
	@Override
	public String toString() {
		
		return null;
	}
	
	
}
