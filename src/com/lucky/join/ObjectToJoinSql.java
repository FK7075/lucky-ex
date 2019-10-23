package com.lucky.join;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.lucky.sqldao.PojoManage;

public class ObjectToJoinSql {

	private String join;
	private Object[] obj;
	private String result="*";

	public ObjectToJoinSql(JoinQuery query) {
		this.join = query.getJoin().getJoin();
		this.obj = query.getJoinObjectes();
		if(!"".equals(query.getResult()))
			result=query.getResult();
	}

	/**
	 * �õ�AND ���ֵ�SQL
	 * @return
	 */
	private String andFragment() {
		String sql = "";
		int p = 0;
		for (int i = 0; i < obj.length; i++) {
			Class<?> clzz = obj[i].getClass();
			Field[] fields = clzz.getDeclaredFields();
			for (int j = 0; j < fields.length; j++) {
				fields[j].setAccessible(true);
				Object fk;
				try {
					fk = fields[j].get(obj[i]);
					if (fk != null) {
						if (p == 0) {
							sql += " WHERE " + PojoManage.getTable(clzz) + "." + PojoManage.getTableField(fields[j])
									+ "=?";
							p++;
						} else {
							sql += " AND " + PojoManage.getTable(clzz) + "." + PojoManage.getTableField(fields[j])
									+ "=?";

						}
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return sql;
	}

	/**
	 * �õ����Ӳ����Ĳ�ѯ����
	 * @return
	 */
	public Object[] getJoinObject() {
		List<Object> list = new ArrayList<>();
		for (int i = 0; i < obj.length; i++) {
			Field[] fields = obj[i].getClass().getDeclaredFields();
			try {
				for (int j = 0; j < fields.length; j++) {
					fields[j].setAccessible(true);
					Object object = fields[j].get(obj[i]);
					if (object != null) {
						list.add(object);
					}
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list.toArray();
	}

	/**
	 * �õ����Ӳ�����SQL
	 * 
	 * @return
	 */
	public String getJoinSql(String...expression) {
		return "SELECT "+result+" FROM " + getOnSql(expression) + andFragment();
	}




	
	/**
	 * �������ӱ��ʽȷ�����ӷ�ʽ
	 * @param expression
	 * @return
	 */
	public String getOnSql(String...expression) {
		String expre="";
		if(expression.length==0||"".equals(expression[0])) {
			for(Object object:obj) {
				expre+=PojoManage.getTable(object.getClass())+"-->";
			}
			expre=expre.substring(0,expre.length()-3);
		}else {
			expre=expression[0];
		}
		String onsql="";
		List<ClassControl> parsExpression = parsExpression(expre);
		for(int i=0;i<parsExpression.size();i++) {
			if(i==0) {
				onsql+=PojoManage.getTable(parsExpression.get(0).getClzz());
			}else {
				onsql+=" "+join+" "+PojoManage.getTable(parsExpression.get(i).getClzz())+" ON "+getEquation(parsExpression.get(i).getClzz(),parsExpression.get(i-1-parsExpression.get(i).getSite()).getClzz());
			}
		}
		return onsql;
	}

	/**
	 * ����Classȷ�����ӵ�ʽ
	 * @param clax
	 * @param clay
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getEquation(Class<?> clax,Class<?> clay) {
		try {
			List<Class<?>> claxKeyClasss = (List<Class<?>>) PojoManage.getKeyFields(clax, false);
			if(claxKeyClasss.contains(clay))
				return PojoManage.getTable(clax)+"."+PojoManage.getTableField(PojoManage.classToField(clax, clay))+"="+PojoManage.getTable(clay)+"."+PojoManage.getIdString(clay);
			return PojoManage.getTable(clay)+"."+PojoManage.getTableField(PojoManage.classToField(clay, clax))+"="+PojoManage.getTable(clax)+"."+PojoManage.getIdString(clax);			
		}catch(Exception e) {
			System.err.println("xfl_fk :"+clax.getName()+" ��  "+clay.getName()+"������'�������ϵ',���������������(@Key,@Id�����Ӳ�ѯ���ʽ['->' '--' '<?>'] )....");
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * �������ʽ
	 * @param expression
	 * @return
	 */
	public List<ClassControl> parsExpression(String expression){
		 List<ClassControl> cctlist=new ArrayList<>();
		String order=expression.replaceAll("\\b<\\S*?>\\b", ",").replaceAll("-->", ",").replaceAll("--", ",").toLowerCase();
		String symbol=expression.toLowerCase();
		String[] splitName = order.split(",");
		for(String name:splitName) {
			symbol=symbol.replaceAll(name, ",");
			ClassControl cctl=new ClassControl();
			Stream.of(obj).map(obj->obj.getClass()).filter(c->name.equals(PojoManage.getTable(c))).forEach(cctl::setClzz);
			cctlist.add(cctl);
		}
		String[] symArr=symbol.split(",");
		for(int i=0;i<symArr.length;i++) {
			cctlist.get(i).setSite(symbolToInt(symArr[i]));
		}
		return cctlist;
	}
	
	public int symbolToInt(String symbol) {
		if("".equals(symbol)) {
			return -1;
		}else if("--".equals(symbol)) {
			return 1;
		}else if("-->".equals(symbol)) {
			return 0;
		}else if(symbol.startsWith("<")&&symbol.endsWith(">")) {
			symbol=symbol.replaceAll("<", "").replaceAll(">", "");
			return Integer.parseInt(symbol);
		}else {
			return -2;
		}
		
	}
}

class ClassControl{
	
	private Class<?> clzz;
	
	private int site=-1;

	public Class<?> getClzz() {
		return clzz;
	}

	public void setClzz(Class<?> clzz) {
		this.clzz = clzz;
	}

	public int getSite() {
		return site;
	}

	public void setSite(int site) {
		this.site = site;
	}

	@Override
	public String toString() {
		return "ClassControl [clzz=" + clzz + ", site=" + site + "]";
	}
	
	
}
