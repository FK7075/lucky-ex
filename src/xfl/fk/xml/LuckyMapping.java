package xfl.fk.xml;

import java.util.ArrayList;
import java.util.List;

public class LuckyMapping {
	
	private String id;//IOC beanΨһ��ʶ
	private String methodid; //MethodΨһ��ʶ
	private String methodname; //Method��
	private List<String> paramlist=new ArrayList<>();//�����б�

	
	public String getMethodid() {
		return methodid;
	}
	public void setMethodid(String methodid) {
		this.methodid = methodid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMethodname() {
		return methodname;
	}
	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}
	public List<String> getParamlist() {
		return paramlist;
	}
	public void setParamlist(List<String> paramlist) {
		this.paramlist = paramlist;
	}
	
	@Override
	public String toString() {
		return "LuckyMapping [id=" + id + ", methodid=" + methodid + ", methodname=" + methodname + ", paramlist="
				+ paramlist + "]";
	}


}
