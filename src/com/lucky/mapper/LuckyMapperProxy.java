package com.lucky.mapper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Stream;

import com.lucky.annotation.Alias;
import com.lucky.annotation.AutoId;
import com.lucky.annotation.Change;
import com.lucky.annotation.Delete;
import com.lucky.annotation.Id;
import com.lucky.annotation.Insert;
import com.lucky.annotation.Join;
import com.lucky.annotation.Mapper;
import com.lucky.annotation.Page;
import com.lucky.annotation.Select;
import com.lucky.annotation.Update;
import com.lucky.enums.JoinWay;
import com.lucky.join.JoinQuery;
import com.lucky.join.Paging;
import com.lucky.join.SqlAndObject;
import com.lucky.join.SqlFragProce;
import com.lucky.mapping.ApplicationBeans;
import com.lucky.sqldao.PojoManage;
import com.lucky.sqldao.SqlCore;
import com.lucky.utils.LuckyUtils;

@SuppressWarnings("all")
public class LuckyMapperProxy {

	private SqlCore sqlCore;
	private Map<String,String> sqlMap;

	public LuckyMapperProxy(SqlCore sql) {
		sqlCore = sql;
		sqlMap=new HashMap<>();
	}
	
	/**
	 * 初始化sqlMap,将配置类中的SQl语句加载到sqlMap中
	 * @param clazz 配置类的Class
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private <T> void initSqlMap(Class<T> clazz) throws InstantiationException, IllegalAccessException {
		if(clazz.isAnnotationPresent(Mapper.class)) {
			Mapper map=clazz.getAnnotation(Mapper.class);
			Class<?> sqlClass=map.value();
			if(!sqlClass.isAssignableFrom(Void.class)) {
				Object sqlPo=sqlClass.newInstance();
				Field[] fields=sqlClass.getDeclaredFields();
				for(Field fi:fields) {
					fi.setAccessible(true);
					sqlMap.put(fi.getName().toUpperCase(), (String)fi.get(sqlPo));
				}
			}
		}
	}
	
	/**
	 * 加载写在.properties配置文件中Sql语句
	 * @param clzz
	 */
	private <T> void initSqlMapProperty(Class<T> clzz){
		if(clzz.isAnnotationPresent(Mapper.class)) {
			Mapper mapper=clzz.getAnnotation(Mapper.class);
			String[] propertys=mapper.properties();
			String coding=mapper.codedformat();
			for(String path:propertys) {
				String propertyPath=this.getClass().getClassLoader().getResource(path).getPath();
				loadProperty(clzz,propertyPath,coding);

			}
		}
	}
	
	/**
	 * 加载写在.properties配置文件中Sql语句
	 * @param clzz
	 * @param propertyPath
	 * @param coding
	 */
	private void loadProperty(Class<?> clzz,String propertyPath,String coding){
		try{
			Properties p=new Properties();
			p.load(new BufferedReader(new InputStreamReader(new FileInputStream(propertyPath),coding)));
			Method[] methods=clzz.getDeclaredMethods();
			for(Method method:methods) {
				if(!method.isAnnotationPresent(Select.class)&&!method.isAnnotationPresent(Insert.class)
				   &&!method.isAnnotationPresent(Update.class)&&!method.isAnnotationPresent(Delete.class)	
				   &&!method.isAnnotationPresent(Join.class)&&!method.isAnnotationPresent(Page.class)) {
					String key=method.getName();
					String value=p.getProperty(key);
					if(value!=null)
						sqlMap.put(key.toUpperCase(), value);
				}
			}
		} catch (UnsupportedEncodingException e) {
			System.err.println("找不到文件->"+propertyPath);
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.err.println("找不到文件->"+propertyPath);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("找不到文件->"+propertyPath);
			e.printStackTrace();
		}

		
	}

	/**
	 * 执行带有SQL的接口方法
	 * @param method 接口方法
	 * @param args 参数列表
	 * @param sql_fp SQl片段化
	 * @param sql sql语句
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private boolean updateSql(Method method,Object[] args,SqlFragProce sql_fp,String sql) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		if(sql.contains("#{")) {
			Class<?> obc = args[0].getClass();
			SqlAndArray sqlArr = noSqlTo(obc,sql);
			if(method.isAnnotationPresent(Change.class)) {
				return dynamicUpdateSql(sql_fp,sqlArr.getSql(),sqlArr.getArray());
			}else {
				return sqlCore.update(sqlArr.getSql(),sqlArr.getArray());
			}
		}else {
			if(method.isAnnotationPresent(Change.class)) {
				return dynamicUpdateSql(sql_fp,sql,args);
			}else {
				return sqlCore.update(sql, args);
			}
		}
	}
	
	/**
	 * 将含有#{}的sql转化为预编译的sql
	 * @param obj 上下文对象
	 * @param noSql 包含#{}的sql
	 * @return SqlAndArray对象包含预编译sql和执行参数
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private SqlAndArray noSqlTo(Object obj,String noSql) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		SqlAndArray sqlArr=new SqlAndArray();
		List<String> fieldname = LuckyUtils.getSqlField(noSql);
		noSql = LuckyUtils.getSqlStatem(noSql);
		List<Object> fields = new ArrayList<>();
		for (String field : fieldname) {
			Field fie = obj.getClass().getDeclaredField(field);
			fie.setAccessible(true);
			fields.add(fie.get(obj));
		}
		sqlArr.setSql(noSql);
		sqlArr.setArray(fields.toArray());
		return sqlArr;
	}
	

	/**
	 * 基于非空检查的SQL语句的执行
	 * @param sql_fp SQL片段化
	 * @param sql sql语句(预编译)
	 * @param args	执行参数
	 * @return true/false
	 */
	private boolean dynamicUpdateSql(SqlFragProce sql_fp,String sql,Object[] args) {
		SqlAndObject so = sql_fp.filterSql(sql, args);
		return sqlCore.update(so.getSqlStr(), so.getObjects());
	}
	
	/**
	 * 得到List的泛型的Class
	 * @param method 接口方法
	 * @return List的泛型类型的Class
	 */
	private Class<?> getListGeneric(Method method){
		ParameterizedType type = (ParameterizedType) method.getGenericReturnType();
		Type[] entry = type.getActualTypeArguments();
		Class<?> cla = (Class<?>) entry[0];
		return cla;
	}

	/**
	 * 处理被@Select注解标注的接口方法
	 * @param method 接口方法
	 * @param args 参数列表
	 * @param sql_fp SQl片段化类
	 * @return Object
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private <T> Object select(Method method, Object[] args, SqlFragProce sql_fp) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
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
					Parameter[] parameters = method.getParameters();
					JoinQuery query=new JoinQuery();
					for(int i=0;i<parameters.length;i++)
						query.addObject(args[i], getAlias(parameters[i]));
					query.setJoin(JoinWay.INNER_JOIN);
					for(String coum:sel.columns())
						query.resultAppend(coum);
					if (c.isAssignableFrom(List.class)) {
						Class<?> listGeneric = getListGeneric(method);
						return sqlCore.getListJoin(query,listGeneric);
					} else {
						List<?> list = sqlCore.getListJoin(query,c);
						if (list == null || list.isEmpty()) {
							return null;
						} else {
							return list.get(0);
						}
					}
				}
			} else {
				if (sql.contains("#{")) {
					SqlAndArray sqlArr = noSqlTo(args[0],sql);
					if (c.isAssignableFrom(List.class)) {
						Class<?> listGeneric = getListGeneric(method);
						if(method.isAnnotationPresent(Change.class)) {
							SqlAndObject so = sql_fp.filterSql(sqlArr.getSql(),sqlArr.getArray());
							return (List<T>) sqlCore.getList(listGeneric, so.getSqlStr(), so.getObjects());
						}else {
							return (List<T>) sqlCore.getList(listGeneric, sqlArr.getSql(), sqlArr.getArray());
						}
					} else {
						List<T> list=new ArrayList<>();
						if(method.isAnnotationPresent(Change.class)) {
							SqlAndObject so = sql_fp.filterSql(sqlArr.getSql(), sqlArr.getArray());
							return (T) sqlCore.getObject(c, so.getSqlStr(), so.getObjects());
						}else {
							return (T) sqlCore.getObject(c, sqlArr.getSql(), sqlArr.getArray());
						}
					}
				} else {
					if (c.isAssignableFrom(List.class)) {
						Class<?> listGeneric = getListGeneric(method);
						if(method.isAnnotationPresent(Change.class)) {
							SqlAndObject so = sql_fp.filterSql(sql, args);
							return (List<T>) sqlCore.getList(listGeneric, so.getSqlStr(), so.getObjects());
						}else {
							return (List<T>) sqlCore.getList(listGeneric, sql, args);
						}
					} else {
						List<T> list=new ArrayList<>();
						if(method.isAnnotationPresent(Change.class)) {
							SqlAndObject so = sql_fp.filterSql(sql, args);
							return (T) sqlCore.getObject(c, so.getSqlStr(), so.getObjects());
						}else {
							return (T) sqlCore.getObject(c, sql, args);
						}
					}
				}
			}
		}
	}
	

	/**
	 * 处理被@Update注解标注的接口方法
	 * @param method 接口方法
	 * @param args 参数列表
	 * @param sql_fp SQl片段化类
	 * @return true/false
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private boolean update(Method method, Object[] args,SqlFragProce sql_fp) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Update upd = method.getAnnotation(Update.class);
		String sql = upd.value();
		if ("".equals(sql)) 
			return sqlCore.update(args[0]);
		else 
			return updateSql(method,args,sql_fp,sql);
	}
	
	/**
	 * 处理被@Delete注解标注的接口方法
	 * @param method 接口方法
	 * @param args 参数列表
	 * @param sql_fp SQl片段化类
	 * @return true/false
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private boolean delete(Method method, Object[] args,SqlFragProce sql_fp) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Delete del = method.getAnnotation(Delete.class);
		if(del.byid()) {
			return sqlCore.delete((Class<?>)args[0], args[1]);
		}else {
			String sql = del.value();
			if ("".equals(sql))
				return sqlCore.delete(args[0]);
			else
				return updateSql(method,args,sql_fp,sql);
		}
	}
	
	
	/**
	 * 处理被@Insert注解标注的接口方法
	 * @param method 接口方法
	 * @param args 参数列表
	 * @param sql_fp SQl片段化类
	 * @return true/false
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private <T> boolean insert(Method method, Object[] args,SqlFragProce sql_fp) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Insert ins = method.getAnnotation(Insert.class);
		String sql = ins.value();
		if ("".equals(sql)) {
			if(ins.batch()) {
				return sqlCore.saveListBatch((List<T>) args[0]);
			}else {
				return sqlCore.save(args[0],ins.setautoId());
			}
		} else {
			return updateSql(method,args,sql_fp,sql);
		}
	}
	
	/**
	 * 处理被@Join注解标注的接口方法
	 * @param method 接口方法
	 * @param args 参数列表
	 * @return Object
	 */
	private Object join(Method method, Object[] args){
		Join join = method.getAnnotation(Join.class);
		Parameter[] parameters = method.getParameters();
		JoinQuery query=new JoinQuery();
		for(int i=0;i<parameters.length;i++)
			query.addObject(args[i], getAlias(parameters[i]));
		query.setJoin(join.join());
		String[] columns = join.value();
		for(String colum:columns)
			query.resultAppend(colum);
		ParameterizedType type = (ParameterizedType) method.getGenericReturnType();
		Type[] entry = type.getActualTypeArguments();
		Class<?> cla = (Class<?>) entry[0];
		return sqlCore.getListJoin(query, cla, join.expression());
	}
	/**
	 * 处理被@Page注解标注的接口方法
	 * @param method 接口方法
	 * @param args 参数列表
	 * @return Object
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private Object page(Method method, Object[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
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
	}

	/**
	 * 处理被没有被注解标注的接口方法
	 * @param method 接口方法
	 * @param args 参数列表
	 * @param sql_fp SQl片段化类
	 * @return Object
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	private Object notHave(Method method, Object[] args, SqlFragProce sql_fp) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		if(sqlMap.containsKey(method.getName().toUpperCase())) {
			String methodName=method.getName().toUpperCase();
			String sqlStr=sqlMap.get(methodName);
			String sqlCopy=sqlStr.toUpperCase();
			if(sqlCopy.contains("#{")) {
				if(method.isAnnotationPresent(AutoId.class)) {
					Field idField = PojoManage.getIdField(args[0].getClass());
					Id id=idField.getAnnotation(Id.class);
					if(id.type()==com.lucky.enums.Type.AUTO_INT)
						LuckyUtils.pojoSetId(args[0]);
					else if(id.type()==com.lucky.enums.Type.AUTO_UUID){
						idField.setAccessible(true);
						idField.set(args[0], UUID.randomUUID().toString().replaceAll("-", ""));
					}
				}
				SqlAndArray sqlArr = noSqlTo(args[0],sqlStr);
				sqlStr=sqlArr.getSql();
				args=sqlArr.getArray();
			}
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
					return dynamicUpdateSql(sql_fp,sqlStr,args);
				}else {
					return sqlCore.delete(sqlStr,args);
				}
			}
		}else {
			return null;
		}
	}

	/**
	 * 返回接口的代理对象
	 * @param clazz 接口的Class
	 * @return T
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public <T> T getMapperProxyObject(Class<T> clazz) throws InstantiationException, IllegalAccessException {
		initSqlMap(clazz);
		
		initSqlMapProperty(clazz);
		InvocationHandler handler = (proxy, method, args) -> {
			SqlFragProce sql_fp = SqlFragProce.getSqlFP();
			if (method.isAnnotationPresent(Select.class))
				return select(method,args,sql_fp);
			else if (method.isAnnotationPresent(Update.class))
				return update(method,args,sql_fp);
			else if (method.isAnnotationPresent(Delete.class))
				return delete(method,args,sql_fp);
			else if (method.isAnnotationPresent(Insert.class))
				return insert(method,args,sql_fp);
			else if (method.isAnnotationPresent(Join.class))
				return join(method,args);
			else if (method.isAnnotationPresent(Page.class))
				return page(method,args);
			else 
				return notHave(method,args,sql_fp);
		};
		return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { clazz }, handler);
	}
	
	public String getAlias(Parameter param) {
		if(param.isAnnotationPresent(Alias.class))
			return param.getAnnotation(Alias.class).value();
		return param.getName();
	}

}

class SqlAndArray{
	
	private String sql;
	
	private Object[] array;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Object[] getArray() {
		return array;
	}

	public void setArray(Object[] array) {
		this.array = array;
	}

	@Override
	public String toString() {
		return "SqlAndArray [sql=" + sql + ", array=" + Arrays.toString(array) + "]";
	}
	
}
