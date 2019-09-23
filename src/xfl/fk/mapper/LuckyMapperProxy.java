package xfl.fk.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import xfl.fk.annotation.Change;
import xfl.fk.annotation.Delete;
import xfl.fk.annotation.Insert;
import xfl.fk.annotation.Join;
import xfl.fk.annotation.LeftJoin;
import xfl.fk.annotation.Mapper;
import xfl.fk.annotation.Page;
import xfl.fk.annotation.RightJoin;
import xfl.fk.annotation.Select;
import xfl.fk.annotation.Update;
import xfl.fk.join.Paging;
import xfl.fk.join.SqlAndObject;
import xfl.fk.join.SqlFragProce;
import xfl.fk.mapping.ApplicationBeans;
import xfl.fk.sqldao.SqlCore;
import xfl.fk.utils.LuckyUtils;

@SuppressWarnings("all")
public class LuckyMapperProxy {

	private SqlCore sqlCore;
	private Map<String,String> sqlMap;

	public LuckyMapperProxy(SqlCore sql) {
		sqlCore = sql;
		sqlMap=new HashMap<>();
	}
	

	public <T> T getMapperProxyObject(Class<T> clazz) throws InstantiationException, IllegalAccessException {
		if(clazz.isAnnotationPresent(Mapper.class)) {
			Mapper map=clazz.getAnnotation(Mapper.class);
			Class<?> sqlClass=map.value();
			Object sqlPo=sqlClass.newInstance();
			Field[] fields=sqlClass.getDeclaredFields();
			for(Field fi:fields) {
				fi.setAccessible(true);
				sqlMap.put(fi.getName().toUpperCase(), (String)fi.get(sqlPo));
			}
		}
		InvocationHandler handler = (proxy, method, args) -> {
			SqlFragProce sql_fp = new SqlFragProce();
			if (method.isAnnotationPresent(Select.class)) {
				Class<?> c = method.getReturnType();
				Select sel = method.getAnnotation(Select.class);
				if(sel.byid()) {
					if(args.length==2) {
						return sqlCore.getOne((Class<?>)args[0], args[1]);
					}else if(args.length==1) {
						return sqlCore.getOne(method.getReturnType(), args[0]);
					}else {
						return false;
					}
				}else {
					String sql = sel.value();
					if ("".equals(sql)) {
						if ("".equals(sel.columns())) {
							if (c.isAssignableFrom(List.class)) {
								return sqlCore.getList(args[0]);
							} else {
								return sqlCore.getObject(args[0]);
							}
						} else {// 有指定列的标注
							if (c.isAssignableFrom(List.class)) {
								ParameterizedType type = (ParameterizedType) method.getGenericReturnType();
								Type[] entry = type.getActualTypeArguments();
								Class<?> cla = (Class<?>) entry[0];
								return sqlCore.getListJoinLeftResult(cla, sel.columns(), args);
							} else {
								List<?> list = sqlCore.getListJoinLeftResult(c, sel.columns(), args);
								if (list == null || list.isEmpty()) {
									return null;
								} else {
									return list.get(0);
								}
							}
						}
					} else {
						if (sql.contains("#{")) {
							List<String> fieldname = LuckyUtils.getSqlField(sql);
							sql = LuckyUtils.getSqlStatem(sql);
							Class<?> obc = args[0].getClass();
							List<Object> fields = new ArrayList<>();
							for (String field : fieldname) {
								Field fie = obc.getDeclaredField(field);
								fie.setAccessible(true);
								fields.add(fie.get(args[0]));
							}
							if (c.isAssignableFrom(List.class)) {
								ParameterizedType type = (ParameterizedType) method.getGenericReturnType();
								Type[] entry = type.getActualTypeArguments();
								Class<?> cla = (Class<?>) entry[0];
								if(method.isAnnotationPresent(Change.class)) {
									SqlAndObject so = sql_fp.filterSql(sql, fields.toArray());
									return sqlCore.getList(cla, so.getSqlStr(), so.getObjects());
								}else {
									return sqlCore.getList(cla, sql, fields.toArray());
								}
							} else {
								List<T> list=new ArrayList<>();
								if(method.isAnnotationPresent(Change.class)) {
									SqlAndObject so = sql_fp.filterSql(sql, fields.toArray());
									list = (List<T>) sqlCore.getList(c, so.getSqlStr(), so.getObjects());
								}else {
									list = (List<T>) sqlCore.getList(c, sql, fields.toArray());
								}
								if (list == null || list.isEmpty())
									return null;
								else
									return list.get(0);
							}
						} else {
							if (c.isAssignableFrom(List.class)) {
								ParameterizedType type = (ParameterizedType) method.getGenericReturnType();
								Type[] entry = type.getActualTypeArguments();
								Class<?> cla = (Class<?>) entry[0];
								if(method.isAnnotationPresent(Change.class)) {
									SqlAndObject so = sql_fp.filterSql(sql, args);
									return sqlCore.getList(cla, so.getSqlStr(), so.getObjects());
								}else {
									return sqlCore.getList(cla, sql, args);
								}
							} else {
								List<T> list=new ArrayList<>();
								if(method.isAnnotationPresent(Change.class)) {
									SqlAndObject so = sql_fp.filterSql(sql, args);
									list = (List<T>) sqlCore.getList(c, so.getSqlStr(), so.getObjects());
								}else {
									list = (List<T>) sqlCore.getList(c, sql, args);
								}
								if (list == null || list.isEmpty())
									return null;
								else
									return list.get(0);
							}
						}
					}
				}
			} else if (method.isAnnotationPresent(Update.class)) {
				Update upd = method.getAnnotation(Update.class);
				String sql = upd.value();
				if ("".equals(sql)) {
					return sqlCore.update(args[0]);
				} else if (sql.contains("#{")) {
					List<String> fieldname = LuckyUtils.getSqlField(sql);
					sql = LuckyUtils.getSqlStatem(sql);
					Class<?> obc = args[0].getClass();
					List<Object> fields = new ArrayList<>();
					for (String field : fieldname) {
						Field fie = obc.getDeclaredField(field);
						fie.setAccessible(true);
						fields.add(fie.get(args[0]));
					}
					if(method.isAnnotationPresent(Change.class)) {
						SqlAndObject so = sql_fp.filterSql(sql, fields.toArray());
						return sqlCore.update(so.getSqlStr(), so.getObjects());
					}else {
						return sqlCore.update(sql, fields.toArray());
					}
				} else {
					if(method.isAnnotationPresent(Change.class)) {
						SqlAndObject so = sql_fp.filterSql(sql, args);
						return sqlCore.update(so.getSqlStr(), so.getObjects());
					}else {
						return sqlCore.update(sql, args);
					}
				}
			} else if (method.isAnnotationPresent(Delete.class)) {
				Delete del = method.getAnnotation(Delete.class);
				if(del.byid()) {
					return sqlCore.delete((Class<?>)args[0], args[1]);
				}else {
					String sql = del.value();
					if ("".equals(sql)) {
						return sqlCore.delete(args[0]);
					} else if (sql.contains("#{")) {
						List<String> fieldname = LuckyUtils.getSqlField(sql);
						sql = LuckyUtils.getSqlStatem(sql);
						Class<?> obc = args[0].getClass();
						List<Object> fields = new ArrayList<>();
						for (String field : fieldname) {
							Field fie = obc.getDeclaredField(field);
							fie.setAccessible(true);
							fields.add(fie.get(args[0]));
						}
						if(method.isAnnotationPresent(Change.class)) {
							SqlAndObject so = sql_fp.filterSql(sql, fields.toArray());
							return sqlCore.delete(so.getSqlStr(), so.getObjects());
						}else {
							return sqlCore.delete(sql, fields.toArray());
						}
						
					} else {
						if(method.isAnnotationPresent(Change.class)) {
							SqlAndObject so = sql_fp.filterSql(sql, args);
							return sqlCore.delete(so.getSqlStr(), so.getObjects());
						}else {
							return sqlCore.delete(sql, args);
						}
					}
				}
			} else if (method.isAnnotationPresent(Insert.class)) {
				Insert ins = method.getAnnotation(Insert.class);
				String sql = ins.value();
				if ("".equals(sql)) {
					if(ins.batch()) {
						return sqlCore.saveListBatch((List<T>) args[0]);
					}else {
						return sqlCore.save(args[0]);
					}
				} else if (sql.contains("#{")) {
					List<String> fieldname = LuckyUtils.getSqlField(sql);
					sql = LuckyUtils.getSqlStatem(sql);
					Class<?> obc = args[0].getClass();
					List<Object> fields = new ArrayList<>();
					for (String field : fieldname) {
						Field fie = obc.getDeclaredField(field);
						fie.setAccessible(true);
						fields.add(fie.get(args[0]));
					}
					return sqlCore.save(sql, fields.toArray());
				} else {
					SqlAndObject so = sql_fp.filterSql(sql, args);
					return sqlCore.save(so.getSqlStr(), so.getObjects());
				}
			} else if (method.isAnnotationPresent(Join.class)) {
				Join join = method.getAnnotation(Join.class);
				ParameterizedType type = (ParameterizedType) method.getGenericReturnType();
				Type[] entry = type.getActualTypeArguments();
				Class<?> cla = (Class<?>) entry[0];
				if ("".equals(join.value()))
					return sqlCore.getListJoin(cla, args);
				else
					return sqlCore.getListJoinResult(cla, join.value(), args);
			} else if (method.isAnnotationPresent(LeftJoin.class)) {
				LeftJoin join = method.getAnnotation(LeftJoin.class);
				ParameterizedType type = (ParameterizedType) method.getGenericReturnType();
				Type[] entry = type.getActualTypeArguments();
				Class<?> cla = (Class<?>) entry[0];
				if ("".equals(join.value()))
					return sqlCore.getListJoinLeft(cla, args);
				else
					return sqlCore.getListJoinLeftResult(cla, join.value(), args);
			} else if (method.isAnnotationPresent(RightJoin.class)) {
				RightJoin join = method.getAnnotation(RightJoin.class);
				ParameterizedType type = (ParameterizedType) method.getGenericReturnType();
				Type[] entry = type.getActualTypeArguments();
				Class<?> cla = (Class<?>) entry[0];
				if ("".equals(join.value()))
					return sqlCore.getListJoinRight(cla, args);
				else
					return sqlCore.getListJoinRightResult(cla, join.value(), args);
			} else if (method.isAnnotationPresent(Page.class)) {
				Page page = method.getAnnotation(Page.class);
				if (!"".equals(page.value())) {
					ParameterizedType type = (ParameterizedType) method.getGenericReturnType();
					Type[] entry = type.getActualTypeArguments();
					Paging paging=new Paging(page.value());
					Class<?> cla = (Class<?>) entry[0];
					return paging.getLimitList(cla, args);
				} else {
					String strategy = page.strategy();
					String strategymtthod = page.method();
					Paging pageing = null;
					Object strategyobj = null;
					if (strategy.contains(".")) {
						Class<?> clzz = Class.forName(strategy);
						strategyobj = clzz.newInstance();
					} else {
						ApplicationBeans apps = ApplicationBeans.getApplicationBeans();
						strategyobj = apps.getBean(strategy);
					}
					pageing = new Paging(strategyobj, strategymtthod);
					int currentpagenum = (int) args[0];
					int pagesize = (int) args[1];
					return pageing.getPageList(currentpagenum, pagesize);
				}
			} else {
				if(sqlMap.containsKey(method.getName().toUpperCase())) {
					String methodName=method.getName().toUpperCase();
					String sqlStr=sqlMap.get(methodName);
					String sqlCopy=sqlStr.toUpperCase();
					if(sqlCopy.contains("SELECT")) {
						if("C:".equalsIgnoreCase(sqlCopy.substring(0, 2))) {
							sqlStr=sqlStr.substring(2,sqlStr.length());
							SqlAndObject so = sql_fp.filterSql(sqlStr, args);
							if(List.class.isAssignableFrom(method.getReturnType())) {
								ParameterizedType type = (ParameterizedType) method.getGenericReturnType();
								Type[] entry = type.getActualTypeArguments();
								Class<?> cla = (Class<?>) entry[0];
								return sqlCore.getList(cla, so.getSqlStr(), so.getObjects());
							}else {
								return sqlCore.getObject(method.getReturnType(), so.getSqlStr(), so.getObjects());
							}
						}else {
							if(List.class.isAssignableFrom(method.getReturnType())) {
								ParameterizedType type = (ParameterizedType) method.getGenericReturnType();
								Type[] entry = type.getActualTypeArguments();
								Class<?> cla = (Class<?>) entry[0];
								return sqlCore.getList(cla, sqlStr,args);
							}else {
								return sqlCore.getObject(method.getReturnType(), sqlStr,args);
							}
						}
					}else {
						if("C:".equalsIgnoreCase(sqlCopy.substring(0, 2))) {
							sqlStr=sqlStr.substring(2,sqlStr.length());
							SqlAndObject so = sql_fp.filterSql(sqlStr, args);
							return sqlCore.delete(so.getSqlStr(), so.getObjects());
						}else {
							return sqlCore.delete(sqlStr,args);
						}
					}
				}else {
					return null;
				}
			}

		};
		return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { clazz }, handler);
	}

}
