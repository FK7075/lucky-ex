package com.lucky.jacklamb.sqlcore.abstractionlayer.dynamiccoreImpl;

import java.util.List;

import com.lucky.jacklamb.query.ObjectToJoinSql;
import com.lucky.jacklamb.query.QueryBuilder;
import com.lucky.jacklamb.sqlcore.abstractionlayer.abstcore.SqlCore;
import com.lucky.jacklamb.sqlcore.abstractionlayer.abstcore.SqlGroup;
import com.lucky.jacklamb.sqlcore.abstractionlayer.util.BatchInsert;
import com.lucky.jacklamb.tcconversion.createtable.CreateTable;
import com.lucky.jacklamb.tcconversion.reverse.TableToJava;
import com.lucky.jacklamb.utils.LuckyUtils;

@SuppressWarnings("unchecked")
public final class MySqlCore extends SqlCore {
	
	private String dbname;
	
	private TableToJava tableToJava;

	public MySqlCore(String dbname) {
		super(dbname);
		dbname=super.dataSource.getName();	
		tableToJava=new TableToJava(dbname);
	}

	@Override
	public void createJavaBean() {
		tableToJava.generateJavaSrc();
	}

	@Override
	public void createJavaBean(String srcPath) {
		tableToJava.generateJavaSrc(srcPath);
	}

	@Override
	public void createJavaBean(String... tables) {
		tableToJava.b_generateJavaSrc(tables);
	}

	@Override
	public void createJavaBean(String srcPath, String... tables) {
		tableToJava.a_generateJavaSrc(srcPath, tables);
		
	}

	@Override
	public void createTable() {
		CreateTable ct = new CreateTable(dbname);
		ct.creatTable();
		
	}

	@Override
	public <T> List<T> getPageList(T t, int page, int size) {
		QueryBuilder queryBuilder=new QueryBuilder();
		queryBuilder.limit(page,size);
		queryBuilder.setWheresql(new MySqlGroup());
		queryBuilder.addObject(t);
		return (List<T>) query(queryBuilder,t.getClass());
	}

	@Override
	public <T> List<T> query(QueryBuilder queryBuilder, Class<T> resultClass, String... expression) {
		queryBuilder.setWheresql(new MySqlGroup());
		ObjectToJoinSql join = new ObjectToJoinSql(queryBuilder);
		String sql = join.getJoinSql(expression);
		Object[] obj = join.getJoinObject();
		return getList(resultClass, sql, obj);
	}

	@Override
	public <T> boolean insert(T t, boolean... addId) {
		if(addId!=null&&addId.length!=0&&addId[0])
			LuckyUtils.pojoSetId(dbname,t);
		return generalObjectCore.insert(t);
	}

	@Override
	public boolean insertBatchByArray(boolean addId, Object... obj) {
		for(Object pojo:obj) {
			insert(pojo,addId);
		}
		return true;
	}

	@Override
	public <T> boolean insertBatchByList(List<T> list) {
		BatchInsert bbi=new BatchInsert(list);
		return statementCore.update(bbi.getInsertSql(), bbi.getInsertObject());
	}
}

class MySqlGroup extends SqlGroup{

	@Override
	public String sqlGroup(String res, String onsql, String andsql, String like, String sort) {
		if(!andsql.contains("WHERE")&&!"".equals(like)) {
			like=" WHERE "+like;
		}
		if(page==null&&rows==null) {
			return "SELECT "+res+" FROM " + onsql + andsql+like+sort;
		}else {
			return "SELECT "+res+" FROM " + onsql + andsql+like+sort+" LIMIT "+(page-1)*rows+","+rows;
		}
	}
	
}

