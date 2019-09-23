package xfl.fk.sqldao;

import java.util.ArrayList;
import java.util.List;
/**
 * 类信息包装类
 * @author fk-7075
 *
 */
public class ClassInfo {
	private Class<?> clzz;
	private String className;
	private List<String> names=new ArrayList<String>();
	private List<Object> values=new ArrayList<Object>();
	
	public Class<?> getClzz() {
		return clzz;
	}
	public void setClzz(Class<?> clzz) {
		this.clzz = clzz;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public List<String> getNames() {
		return names;
	}
	public void setNames(List<String> names) {
		this.names = names;
	}
	public List<Object> getValues() {
		return values;
	}
	public void setValues(List<Object> values) {
		this.values = values;
	}
	
}
