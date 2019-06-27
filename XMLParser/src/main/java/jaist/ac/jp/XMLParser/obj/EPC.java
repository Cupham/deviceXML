package jaist.ac.jp.XMLParser.obj;

import java.util.ArrayList;
import java.util.List;

public class EPC {
	private String code;
	private String initvalue;
	private List<String> filename;
	public EPC() {
		filename = new ArrayList<String>();
	};
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getInitvalue() {
		return initvalue;
	}
	public void setInitvalue(String initvalue) {
		this.initvalue = initvalue;
	}
	public List<String> getFilename() {
		return filename;
	}
	public void setFilename(List<String> filename) {
		this.filename = filename;
	}
	public void appendAFile(String aname) {
		filename.add(aname);
	}

}
