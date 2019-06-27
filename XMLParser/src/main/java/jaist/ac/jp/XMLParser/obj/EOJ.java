package jaist.ac.jp.XMLParser.obj;

import java.util.ArrayList;
import java.util.List;

public class EOJ {
	private List<EPC> epcList;
	private String EOJ_Code;
	public EOJ() {
		epcList = new ArrayList<EPC>();
		
	}
	
	@Override
	public String toString() {
		return  EOJ_Code;
	}

	public List<EPC> getEpcList() {
		return epcList;
	}

	public void setEpcList(List<EPC> epcList) {
		this.epcList = epcList;
	}

	public String getEOJ_Code() {
		return EOJ_Code;
	}

	public void setEOJ_Code(String eOJ_Code) {
		EOJ_Code = eOJ_Code;
	}
	public void appendAnEPC(EPC aEPC) {
		this.epcList.add(aEPC);
	}
}
