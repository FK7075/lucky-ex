package xfl.fk.annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取注解信息类（DL）
 * 
 * @author fk-7075
 *
 */
@SuppressWarnings("all")
public class AnnotationCgf {
	private static AnnotationCgf cfg = null;

	private AnnotationCgf() {
	};

	public static AnnotationCgf getAnnCfg() {
		if (cfg == null)
			cfg = new AnnotationCgf();
		return cfg;
	}

	/**
	 * 获得主键(id)注解
	 * @param clzz 要操作的类的Class对象
	 * @return 注解上的id
	 */
	public String getId(Class clzz) {
		Annotation annotations = clzz.getAnnotation(Lucky.class);
		if (annotations != null) {
			if ("".equals(((Lucky) annotations).id()))
				return null;
			else
				return ((Lucky) annotations).id();
		} else {
			return null;
		}
	}
	/**
	 * 获取表名注解(存在表名注解则返回注解表名，否则返回class类名)
	 * @param clzz 要操作的类的Class对象
	 * @return 注解上的表名
	 */
	public String getTableName(Class clzz) {
		Annotation annotations = clzz.getAnnotation(Lucky.class);
		if(annotations!=null) {
			if("".equals(((Lucky) annotations).table()))
				return clzz.getSimpleName();
			else
				return ((Lucky) annotations).table();
		}else {
			return clzz.getSimpleName();
		}
	}

	/**
	 * 获得外键注解
	 * @param clzz 要操作的类的Class对象
	 * @return 注解上的外键集合
	 */
	public String[] getkey(Class clzz) {
		Annotation annotations = clzz.getAnnotation(Lucky.class);
		if (annotations != null) {
			if ("".equals(((Lucky) annotations).key()[0]))
				return null;
			else
				return ((Lucky) annotations).key();
		} else
			return null;
	}


	/**
	 * 根据注解信息为某张表设置级联删除外键
	 * @param clzz 要操作的类的Class对象
	 * @return 返回有关外键级联的SQl语句
	 */
	public String isCascadeDel(Class clzz) {
		Annotation annotation = clzz.getAnnotation(Cascade.class);
		if (annotation != null) {
			if (((Cascade) annotation).delete())
				return " ON DELETE CASCADE";
			else
				return "";
		} else
			return "";
	}
	/**
	 * 跟注解信息为某张表设置级联更新的外键
	 * @param clzz 要操作的类的Class对象
	 * @return 返回有关外键级联的SQl语句
	 */
	public String isCascadeUpd(Class clzz) {
		Annotation annotation = clzz.getAnnotation(Cascade.class);
		if(annotation!=null) {
		if (((Cascade) annotation).update())
			return " ON UPDATE CASCADE";
		else
			return "";
		}else
			return "";
	}
	/**
	 * 主键是否自动增长
	 * @param clzz
	 * @return
	 */
	public String isAuto(Class clzz) {
		Annotation annotation = clzz.getAnnotation(Lucky.class);
		if(annotation!=null) {
			if(((Lucky) annotation).auto())
				return "AUTO_INCREMENT";
			else
				return "";
		}else
			return "";
	}
}
