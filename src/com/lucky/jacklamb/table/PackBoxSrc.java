package com.lucky.jacklamb.table;

import java.util.List;

import com.lucky.jacklamb.sqldao.ReadProperties;
import com.lucky.jacklamb.utils.LuckyUtils;

/**
 * ���ܰ�װ��
 * 
 * @author fk-7075
 *
 */
public class PackBoxSrc {

	private String className;// ����
	private String pack;// ���ڰ�
	private String impor;// ����İ�
	private String field;
	private String getset;
	private String end;

	public String getImpor() {
		return impor;
	}

	public void setImpor(String impor) {
		this.impor = impor;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getGetset() {
		return getset;
	}

	public void setGetset(String getset) {
		this.getset = getset;
	}

	
	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public static PackBoxSrc getPackBoxSrc(String dbname,String... classnames) {
		PackBoxSrc pack = new PackBoxSrc();
		pack.setClassName("PackBox");
		pack.setPack("package " + ReadProperties.getDataSource(dbname).getCaeateTable() + ";\n\n/**\n * ���ܴ�������κ����ݿ�Ĳ����������ɴ�������װ\n * @author FK7075\n */");
		pack.setImpor("\npublic class PackBox {\n\n");
		pack.setField("");
		pack.setGetset("");
		pack.setEnd("\n}");
		if (classnames.length == 0) {
			Tables table=new Tables(dbname);
			List<String> list=table.getTablenames();
			String[] tabs=new String[list.size()];
			tabs=list.toArray(tabs);
			for (int i=0;i<tabs.length;i++) {
				tabs[i]=LuckyUtils.TableToClass1(tabs[i]);
			}
			classnames=tabs;
		}
		for (String name : classnames) {
			StringBuilder sb1=new StringBuilder();
			String Name=LuckyUtils.TableToClass(name);
			sb1.append("\t").append("private ").append(Name+" ").append(name+";\n");
			StringBuilder sb2=new StringBuilder();
			sb2.append("\n\n\tpublic ").
			append(Name+" get"+Name+"() {\n\t\treturn this."+name+";\n\t}\n\n\tpublic void set"+Name+"("+Name+" "+name+") {\n"
					+ "\t\tthis."+name+" = "+name+";\n\t}");
			pack.setField(pack.getField()+sb1.toString());
			pack.setGetset(pack.getGetset()+sb2.toString());
		}
		return pack;
	}

}
