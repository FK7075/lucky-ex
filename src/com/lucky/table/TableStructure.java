package com.lucky.table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lucky.utils.LuckyUtils;

/**
 * �������Ϣ��װ�������� ������ �������� toString������
 * @author fk-7075
 *
 */
public class TableStructure {
	private String tableName;//����
	private List<String> fields=new ArrayList<String>();//�ֶ�����
	private List<String> types=new ArrayList<String>();//��Ӧ�����ͼ�
	private String pri;//����
	private List<String> muls=new ArrayList<String>();//���
	
	
	@Override
	public String toString() {
		return "TableStructure [tableName=" + tableName + ", fields=" + fields + ", types=" + types + ", pri=" + pri
				+ ", muls=" + muls + "]";
	}
	
	

	public String getTableName() {
		return tableName;
	}



	public void setTableName(String tableName) {
		this.tableName = tableName;
	}



	public List<String> getFields() {
		return fields;
	}



	public void setFields(List<String> fields) {
		this.fields = fields;
	}



	public List<String> getTypes() {
		return types;
	}



	public void setTypes(List<String> types) {
		this.types = types;
	}



	public String getPri() {
		return pri;
	}



	public void setPri(String pri) {
		this.pri = pri;
	}



	public List<String> getMuls() {
		return muls;
	}



	public void setMuls(String mul) {
		this.muls.add(mul);
	}



	/**
	 * ���tableName���Ӧ�ı�Ľṹ
	 * @param tableName ����
	 */
	public TableStructure(String tableName) {
		this.tableName=LuckyUtils.TableToClass(tableName);
		ResultSet rs=LuckyUtils.getResultSet("DESCRIBE "+tableName);
		try {
			while(rs.next()) {
				this.getFields().add(rs.getString("Field"));
				if("PRI".equalsIgnoreCase(rs.getString("Key")))
					this.setPri(rs.getString("Field"));
				if("MUL".equalsIgnoreCase(rs.getString("Key")))
					this.setMuls(rs.getString("Field"));
				this.getTypes().add(LuckyUtils.mysqlToJava(LuckyUtils.getMySqlType(rs.getString("Type"))));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ����ָ����Ľṹ
	 * @param tables ����
	 * @return
	 */
	public static List<TableStructure> getAssignTableStructure(String...tables){
		List<TableStructure> list=new ArrayList<TableStructure>();
		for (String tablename : tables) {
			TableStructure table=new TableStructure(tablename);
			list.add(table);
		}
		return list;
	}
	/**
	 * ������ݿ������б�Ľṹ
	 * @param tables Tables����
	 * @return 
	 */
	public static List<TableStructure> getMoreTableStructure(Tables tables){
		List<TableStructure> list=new ArrayList<TableStructure>();
		for (String t_name : tables.getTablenames()) {
			TableStructure table=new TableStructure(t_name);
			list.add(table);
		}
		return list;
	}
	/**
	 * �õ��ñ��Ӧ���toString������Դ���� 
	 * @return
	 */
	public String getToString() {
		String tostr="\n\t@Override\n\tpublic String toString() {\n\t\treturn \""+tableName+" [";
		for(int i=0;i<fields.size();i++) {
			if(i!=fields.size()-1) {
				tostr+=fields.get(i)+"=\" + "+fields.get(i)+" + \", ";
			}else{
				tostr+=fields.get(i)+"=\" + "+fields.get(i)+" + \"]\";\n\t}\n}";
			}
		}
		return tostr;
	}
	/**
	 * �޲ι�������Դ����
	 * @return
	 */
	public String getConstructor() {
		String ctsrc="\tpublic "+tableName+"(){}";
		return ctsrc;
	}
	/**
	 * ���ذ����ñ�ȫ�����Ե��вι�������Դ����
	 * @return
	 */
	public String getParameterConstructor() {
		String pctsrc="\tpublic "+tableName+"(";
		for(int i=0;i<fields.size();i++) {
			if(i!=fields.size()-1)
				pctsrc+=types.get(i)+" "+fields.get(i)+", ";
			else
				pctsrc+=types.get(i)+" "+fields.get(i)+"){\n";
		}
		for(int i=0;i<fields.size();i++) {
			if(i!=fields.size()-1)
				pctsrc+="\t\tthis."+fields.get(i)+"="+fields.get(i)+";\n";
			else
				pctsrc+="\t\tthis."+fields.get(i)+"="+fields.get(i)+";\n\t}";
		}
		return pctsrc;
	}
}
