package xfl.fk.table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import xfl.fk.annotation.AnnotationCgf;
import xfl.fk.annotation.Lucky;
import xfl.fk.sqldao.ClassInfoFactory;
import xfl.fk.sqldao.TypeChange;
import xfl.fk.utils.LuckyManager;
import xfl.fk.utils.ProperConfig;

/**
 * 生成建表语句的类
 * 
 * @author fk-7075
 *
 */
@SuppressWarnings("all")
public class CreateTableSql {
	private static TypeChange tych = new TypeChange();
	private static AnnotationCgf ann=AnnotationCgf.getAnnCfg();
	private static ProperConfig propCfg=LuckyManager.getPropCfg();

	/**
	 * 根据类的Class信息生成建表语句
	 * @param clzz 目标类的Class
	 * @return
	 */
	public static String getCreateTable(Class clzz) {
		String sql = "create table if not exists " + ann.getTableName(clzz) + " (";
		Field[] fields = clzz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (i < fields.length - 1) {
				if (ClassInfoFactory.getTableField(fields[i]).equals(AnnotationCgf.getAnnCfg().getId(clzz)))
					sql += ClassInfoFactory.getTableField(fields[i]) + " " + tych.toMysql(fields[i].getType().toString()) + "("+propCfg.getFieldlength()+") "
							+ "not null "+ann.isAuto(clzz)+" primary key,";
				else if (!("double".equals(tych.toMysql(fields[i].getType().toString()))
						|| "datetime".equals(tych.toMysql(fields[i].getType().toString()))
						|| "date".equals(tych.toMysql(fields[i].getType().toString())))) {
					sql += ClassInfoFactory.getTableField(fields[i]) + " " + tych.toMysql(fields[i].getType().toString()) + "("+propCfg.getFieldlength()+") "
							+ " DEFAULT NULL,";
				} else {
					sql += ClassInfoFactory.getTableField(fields[i]) + " " + tych.toMysql(fields[i].getType().toString()) + "  DEFAULT NULL,";
				}
			} else {
				if (ClassInfoFactory.getTableField(fields[i]).equals(AnnotationCgf.getAnnCfg().getId(clzz)))
					sql += ClassInfoFactory.getTableField(fields[i]) + " " + tych.toMysql(fields[i].getType().toString()) + "("+propCfg.getFieldlength()+") "
							+ "not null auto_increment primary key";
				else if (!("double".equals(tych.toMysql(fields[i].getType().toString()))
						|| "datetime".equals(tych.toMysql(fields[i].getType().toString()))
						|| "date".equals(tych.toMysql(fields[i].getType().toString())))) {
					sql += ClassInfoFactory.getTableField(fields[i]) + " " + tych.toMysql(fields[i].getType().toString()) + "("+propCfg.getFieldlength()+") "
							+ " DEFAULT NULL";
				} else {
					sql += ClassInfoFactory.getTableField(fields[i]) + " " + tych.toMysql(fields[i].getType().toString()) + "  DEFAULT NULL";
				}
			}
		}
		sql += ") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		return sql;
	}

	/**
	 * 生成添加外键的sql语句集合
	 * @param clzz
	 * 目标类的Class
	 * @return
	 */
	public static List<String> getForeignKey(Class clzz) {
		List<String> stlist = new ArrayList<String>();
		String[] keys = AnnotationCgf.getAnnCfg().getkey(clzz);
		if (keys == null) {
			return null;
		} else {
			Lucky lucy=(Lucky) clzz.getAnnotation(Lucky.class);
			Class[] cs = lucy.url();
			for (int i = 0; i < cs.length; i++) {
				String sql = "alter table " + ann.getTableName(clzz) + " add constraint " + getRandomStr()
						+ " foreign key (" + keys[i] + ") references " + ann.getTableName(cs[i]) + "("
						+ AnnotationCgf.getAnnCfg().getId(cs[i]) + ")"+ann.isCascadeDel(clzz)+ann.isCascadeUpd(clzz);
				stlist.add(sql);
			}
			return stlist;
		}
	}

	/**
	 * 生成外键名
	 * @return
	 */
	private static String getRandomStr() {
		String[] st = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
				"t", "u", "v", "w", "x", "y", "z" };
		int[] i = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, };
		int a = (int) (Math.random() * ((st.length - 1) - 0 + 1));
		int b = (int) (Math.random() * ((st.length - 1) - 0 + 1));
		int c = (int) (Math.random() * ((i.length - 1) - 0 + 1));
		int x = (int) (Math.random() * ((st.length - 1) - 0 + 1));
		int y = (int) (Math.random() * ((st.length - 1) - 0 + 1));
		int z = (int) (Math.random() * ((i.length - 1) - 0 + 1));
		String m = st[a] + st[b] + st[x] + st[y] + i[z] + i[c];
		return m;
	}
}
