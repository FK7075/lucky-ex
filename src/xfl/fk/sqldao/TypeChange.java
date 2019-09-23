package xfl.fk.sqldao;

import java.util.Date;

/**
 * 类型转化
 * @author fk-7075
 *
 */
@SuppressWarnings("all")
/**
 * mysql转java
 * @author fk-7075
 *
 */
public class TypeChange {
	public Object toType(String info,String old) {
		Object xfl=old;
		if("int".equals(info)||"class java.lang.Integer".equals(info)) {
			if(!("null".equals(old)))
				xfl= Integer.parseInt(old);
		}
		if("long".equals(info)||"class java.lang.Long".equals(info)) {
			if(!("null".equals(old)))
				xfl=Long.parseLong(old);
		}
		if("double".equals(info)||"class java.lang.Double".equals(info)) {
			if(!("null".equals(old)))
				xfl=Double.parseDouble(old);
		}
		if("float".equals(info)||"".equals("class java.lang.Float")) {
			if(!("null".equals(old)))
				xfl=Float.parseFloat(old);
		}
		if("byte".equals(info)||"class java.lang.Byte".equals(info)) {
			if(!("null".equals(old)))
				xfl=Byte.parseByte(old);
		}
		if("class java.util.Date".equals(info)) {
			if(!("null".equals(old)))
				xfl=new Date(old);
		}
		if("boolean".equals(info)||"class java.lang.Boolean".equals(info)) {
			if(!("null".equals(old)))
				xfl=Boolean.parseBoolean(old);
		}
		return xfl;
	}
	/**
	 * java转mysql
	 * @param info
	 * @return
	 */
	public String toMysql(String info) {
		String fk="";
		if("int".equals(info)||"class java.lang.Integer".equals(info)) {
			fk="int";
		}
		if("long".equals(info)||"class java.lang.Long".equals(info)) {
			fk="longblob";
		}
		if("double".equals(info)||"class java.lang.Double".equals(info)) {
			fk="double";
		}
		if("float".equals(info)||"".equals("class java.lang.Float")) {
			fk="double";
		}
		if("byte".equals(info)||"class java.lang.Byte".equals(info)) {
			fk="varchar";
		}
		if("class java.util.Date".equals(info)) {
			fk="date";
		}
		if("boolean".equals(info)||"class java.lang.Boolean".equals(info)) {
			fk="varchar";
		}
		if("class java.lang.String".equals(info))
			fk="varchar";
		return fk;
	}

}
