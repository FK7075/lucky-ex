package com.lucky.jacklamb.query;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.lucky.jacklamb.annotation.orm.mapper.Page;
import com.lucky.jacklamb.enums.JOIN;
import com.lucky.jacklamb.sqlcore.abstractionlayer.abstcore.SqlCore;
import com.lucky.jacklamb.sqlcore.abstractionlayer.util.SqlCoreFactory;
import com.lucky.jacklamb.utils.LuckyUtils;

public class Paging <T>{
	
	private List<?> list=null;
	private int recordnum;//�ܼ�¼��
	private int pagenum;//��ҳ��
	private int currentpagenum;//��ǰҳ��
	private int pagesize;//��ҳ��
	private int index;//��ǰҳ�ĵ�һ����¼�ı��
	private SqlCore sqlCore;
	private Object pagObect;//��ҳ�Ĳ��Զ���
	private Method pagenumMethod;//��ҳ���Զ����еõ���ҳ���ķ���
	private Method pagingMethod;//��ҳ���Զ����еõ���ǰҳ���ݵķ���
	private String countSql;//��ѯ��ҳ����sql���
	private String limitSql;//��ҳ��ѯ�����
	private PageCount count;
	private PageLimit<T> limit;
	
	


	public List<?> getList() {
		return list;
	}

	/**
	 * �ܼ�¼��
	 * @return
	 */
	public int getRecordnum() {
		return recordnum;
	}

	/**
	 * ��ҳ����
	 * @return
	 */
	public int getPagenum() {
		return pagenum;
	}

	/**
	 * ��ǰҳ��ҳ��
	 * @return
	 */
	public int getCurrentpagenum() {
		return currentpagenum;
	}

	/**
	 * ��ҳ��
	 * @return
	 */
	public int getPagesize() {
		return pagesize;
	}

	/**
	 * ��ǰҳ��һ�����������ű��е�λ��
	 * @return
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * ��ʼ����ҳ������,ʹ��Lambda���ʽȷ���ܼ�¼���͵�ǰҳ(���ҳ)
	 * @param count ��ȡ��¼�����ķ���
	 * @param limit ��ȡ��ǰҳ���ݵķ���
	 */
	public Paging(PageCount count,PageLimit<T> limit) {
		this.count=count;
		this.limit=limit;
	}
	
	/**
	 * ��ʼ����ҳ������,ʹ��Lambda���ʽȷ�����Է���(α��ҳ)
	 * @param pagelist
	 */
	public Paging(PageList<T> pagelist) {
		list=pagelist.getPageList();
		recordnum=list.size();
	}
	
	/**
	 * ��ʼ����ҳ����������������ʱ����ʹ��ʹ�ã�ʹ�ô˹��췽��
	 * ����ʹ��getLimitList(Class<T> clzz,Object...params)�����������
	 * @param sqlStr SQl���(COUNT����LIMIT���)
	 */ 
	public Paging(String sqlStr) {
		if(sqlStr.toUpperCase().contains("COUNT")) {
			countSql=sqlStr;
			limitSql=countSqlToLimitSql(sqlStr);
		}else if(sqlStr.toUpperCase().contains("LIMIT")) {
			limitSql=sqlStr;
			countSql=limitSqlToCountSql(sqlStr);
		}
	}

	/**
	 * ��ʼ����ҳ����������������ʱ����ʹ��ʹ�ã����@Pageע��һ��ʹ�ã�,ʹ�ô˹��췽��
	 * ����ʹ��getLimitList(Object...params)�����������
	 * @param pageObject ��ҳ���Զ���
	 * @param pagenumFunctionID �õ���ҳ���ķ�����ID
	 * @param pagingFunctionID ִ�з�ҳ�ķ�����ID
	 */
	public Paging(Object pageObject,String pagenumFunctionID,String pagingFunctionID) {
		this.pagObect=pageObject;
		Class<?> clzz=pagObect.getClass();
		Method[] methods=clzz.getDeclaredMethods();
		for(Method method:methods) {
			if(method.isAnnotationPresent(Page.class)) {
				Page page=method.getAnnotation(Page.class);
				if(pagenumFunctionID.equals(page.value())) {
					pagenumMethod=method;
				}else if(pagingFunctionID.equals(page.value())) {
					pagingMethod=method;
				}
			}
		}
	}
	
	/**
	 * Paging��ʼ��(������Сʱ����ʹ��)
	 * 
	 * --��ҳ���Զ�����һ������������Java�࣬��ҳ���Զ������������������ķ�����Ϊ��ҳ���Է�����
	 * ----1.�����ķ���ֵ���ͱ���ΪList<?>
	 * ----2.��������Ϊ�޲η���
	 * ----4.�������������Ϊ�˵õ�#Ҫ��ҳչʾ������#��������ɵ�List����
	 * 
	 * @param pageObject ��ҳ���Զ���
	 * @param pagemethod �����õĵķ�ҳ���ԣ���ҳ�����еķ�������
	 */
	@SuppressWarnings("unchecked")
	public Paging(Object pageObject,String pagemethod) {
		List<T> pagelist=new ArrayList<>();
		Class<?> clzz=pageObject.getClass();
		try {
			Method merhod=clzz.getDeclaredMethod(pagemethod);
			pagelist=(List<T>) merhod.invoke(pageObject);
		} catch (Exception e) {
			System.err.println("xflfk__:��ҳ���Զ���ķ�ҳ���Է��������Ϲ淶��");
			e.printStackTrace();
		}
		list=pagelist;
		recordnum=list.size();
	}

	/**
	 * ��ʼ����ҳ����
	 * @param dbname ����Դ����
	 * @param initializePojo ��ʼ����Ϣ����
	 */
	public Paging(String dbname,InitializePoJo initializePojo) {
		sqlCore=SqlCoreFactory.createSqlCore(dbname);
		if(initializePojo.getPojos().isEmpty()) {
			this.list=sqlCore.getList(initializePojo.getClzz(), initializePojo.getSql(), initializePojo.getSqlobj().toArray());
			this.recordnum=list.size();
		}else {
			QueryBuilder query=new QueryBuilder();
			for(Object po:initializePojo.getPojos().toArray())
				query.addObject(po);
			query.setJoin(JOIN.INNER_JOIN);
			ObjectToJoinSql join=new ObjectToJoinSql(query);
			String sql=join.getJoinSql();
			Object[] object=join.getJoinObject();
			this.list=sqlCore.getList(initializePojo.getClzz(), sql,object);
			this.recordnum=list.size();
		}
	}
	
	/**
	 * ��ʼ����ҳ����
	 * @param packBokClass ���ܷ�ҳ����İ�װ��
	 * @param sqlStr ��ѯ��Ԥ����SQL
	 * @param sqlObjs ���ռλ���Ķ���
	 */
	public Paging(Class<?> packBokClass,String sqlStr,Object...sqlObjs) {
		this.list=sqlCore.getList(packBokClass, sqlStr, sqlObjs);
		this.recordnum=list.size();
	}
	
	/**
	 * ��ʼ����ҳ����
	 * @param dbname ����Դ����
	 * @param packBokClass ���ܷ�ҳ����İ�װ��
	 * @param pojos ������ѯ��Ϣ��pojo����
	 */
	public Paging(String dbname,Class<T> packBokClass,Object... pojos) {
		sqlCore=SqlCoreFactory.createSqlCore(dbname);
		QueryBuilder query=new QueryBuilder();
		for(Object po:pojos)
			query.addObject(po);
		query.setJoin(JOIN.INNER_JOIN);
		ObjectToJoinSql join=new ObjectToJoinSql(query);
		String sql=join.getJoinSql();
		Object[] object=join.getJoinObject();
		this.list=sqlCore.getList(packBokClass, sql,object);
		recordnum=list.size();
	}
	
	
	
	/**
	 * ��List���ϵ���ʽ���ص�ǰҳ����������
	 * @param currentpagenum ��ǰҳ��
	 * @param pagesize ��ҳ��
	 * @return ��List���ϵ���ʽ���ص�ǰҳ����������
	 */
	public  List<T> getPageList(int currentpagenum,int pagesize){
		List<T> page=new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<T> list_t=(List<T>) list;
		this.pagesize=pagesize;
		if(recordnum%pagesize==0) {
			pagenum=recordnum/pagesize;
		}else {
			pagenum=recordnum/pagesize+1;
		}
		if(currentpagenum>=pagenum) {
			this.currentpagenum=pagenum;
		}else if(currentpagenum<=1) {
			this.currentpagenum=1;
		}else {
			this.currentpagenum=currentpagenum;
		}
		index=(this.currentpagenum-1)*this.pagesize;
		int end=index+this.pagesize;
		if(end>recordnum) {
			end=recordnum;
		}
		for(int j=index;j<end;j++) {
			page.add(list_t.get(j));
		}
		return page;
	}
	

	/**
	 * �õ���ǰҳ���������ݵ�List<T>����(���@Pageע��ʹ�ã���������ʱ����ʹ��)
	 * @param params ִ�з�ҳ�߼��ķ����Ĳ����б�˳�������ѭ��ҳ�����Ĳ���˳��
	 *  ����Ҫʹ��@Page("currnum")��Ǵ���ǰҳ�Ĳ�����@Page("size")��Ǵ����ҳ���Ĳ������˷����������
	 *  ������Paging(Object pageObject,String pagenumFunctionID,String pagingFunctionID)ʹ��
	 * @return
	 */
	@SuppressWarnings("unchecked") 
	public List<T> getLimitList(Object...params){
		List<T> list=new ArrayList<>();
		try {
			pagenumMethod.setAccessible(true);
			recordnum=(int) pagenumMethod.invoke(pagObect);
			init(recordnum,params);
			pagingMethod.setAccessible(true);
			list= (List<T>) pagingMethod.invoke(pagObect, params);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * ��ȡ��ǰҳ����������
	 * @param currentpagenum ��ǰҳ��ҳ��
	 * @param pagesize ��ҳ��
	 * @return
	 */
	public List<T> getLimitList(int currentpagenum,int pagesize){
		recordnum=count.getCount();
		this.pagesize=pagesize;
		if(recordnum%pagesize==0) {
			pagenum=recordnum/pagesize;
		}else {
			pagenum=recordnum/pagesize+1;
		}
		if(currentpagenum>=pagenum) {
			this.currentpagenum=pagenum;
		}else if(currentpagenum<=1) {
			this.currentpagenum=1;
		}else {
			this.currentpagenum=currentpagenum;
		}
		index=(this.currentpagenum-1)*this.pagesize;
		return limit.limit(index,this.pagesize);
	}
	
	/**
	 * �õ���ǰҳ���������ݵ�List<T>���ϣ�ʹ��SQL���ʵ��
	 * @param dbname ����Դ����
	 * @param clzz ��װ������Ķ����Class
	 * @param params ִ�з�ҳ������������в�����ʹ�õ�ǰҳ�����index���˷�����Ҫ���
	 * ������Paging(String sqlStr,boolean isCountSql)ʹ��
	 * @return
	 */
	public List<T> getLimitList(String dbname,Class<T> clzz,Object...params){
		int len=params.length;
		SqlCore sql=SqlCoreFactory.createSqlCore(dbname);
		Object[] countParams=new Object[len-2];
		for(int i=0;i<countParams.length;i++) {
			countParams[i]=params[i];
		}
		recordnum=sql.getObject(int.class, countSql, countParams);
		init(recordnum,params);
		return sql.getList(clzz, limitSql, params);
	}
	
	//ʹ���ܼ�¼������ʼ���������Ե�ֵ
	private void init(int recordnum,Object...params) {
		int len=params.length;
		this.pagesize=(int) params[len-1];
		int currentPage=(int) params[len-2];
		if(recordnum%pagesize==0)
			this.pagenum=recordnum/pagesize;
		else
			this.pagenum=recordnum/pagesize+1;
		if(currentPage<=1)
			currentpagenum=1;
		else if(currentPage>=pagenum)
			currentpagenum=pagenum;
		else
			currentpagenum=currentPage;
		index=(currentpagenum-1)*this.pagesize;
		params[len-2]=index;
	}
	
	//��LIMIT���ת��ΪCOUNT���
	private String limitSqlToCountSql(String limitStr) {
		int start=limitStr.toUpperCase().indexOf("LIMIT");
		int end =limitStr.length();
		int st=limitStr.toUpperCase().indexOf("SELECT")+6;
		int ed=limitStr.toUpperCase().indexOf("FROM");
		limitStr=limitStr.replace(limitStr.substring(start, end),"");
		limitStr=limitStr.replace(limitStr.substring(st, ed)," COUNT(*) ");
		return limitStr;
	}

	//��COUNT���ת��ΪLIMIT���
	private String countSqlToLimitSql(String countStr) {
		String limitSql=null;
		if(countStr.replaceAll(" ", "").toUpperCase().contains("COUNT(*)")) {
			countStr+=" LIMIT ?,?";
			int start=countStr.toUpperCase().indexOf("SELECT")+6;
			int end=countStr.toUpperCase().indexOf("FROM");
			String str=countStr.substring(start,end);
			limitSql=countStr.replace(str, " * ");
		}else {
			String sqlof=countStr.replaceAll(" ", "");
			int st=sqlof.indexOf("(")+1;
			int ed=sqlof.indexOf(")");
			String param=sqlof.substring(st, ed);
			List<String> paramlist=LuckyUtils.strToArray(param);
			if(countStr.toUpperCase().contains("WHERE")) {
				for (String string : paramlist) {
					countStr+=" AND "+string+" IS NOT NULL";
				}
			}else {
				for (int i=0;i<paramlist.size();i++) {
					if(i==0) {
						countStr+=" WHERE "+paramlist.get(i)+" IS NOT NULL";
					}else {
						countStr+=" AND "+paramlist.get(i)+" IS NOT NULL";
					}
				}
			}
			countStr+=" LIMIT ?,?";
			int start=countStr.toUpperCase().indexOf("SELECT")+6;
			int end=countStr.toUpperCase().indexOf("FROM");
			String str=countStr.substring(start,end);
			limitSql=countStr.replace(str, " * ");
		}
		return limitSql;
	}
	

}
