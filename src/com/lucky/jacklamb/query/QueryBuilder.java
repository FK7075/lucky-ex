package com.lucky.jacklamb.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lucky.jacklamb.enums.JOIN;
import com.lucky.jacklamb.enums.Sort;
import com.lucky.jacklamb.sqlcore.abstractionlayer.abstcore.SqlGroup;

public class QueryBuilder {

    /**
     * ���Ӳ��������ӷ�ʽ
     */
    private JOIN join = JOIN.INNER_JOIN;

    /**
     * ��Ҫ�����Ķ���
     */
    private List<Object> objects;

    /**
     * ���ò�ѯ������
     */
    private QFilter qFilter;

    /**
     * ������Ϣ
     */
    private List<SortSet> sortSets ;

    /**
     * ģ����ѯ��Ϣ
     */
    private String like="";

    private Integer page;

    private Integer rows;

    private SqlGroup sqlGroup;

    public QueryBuilder(){
        objects=new ArrayList<>();
        sortSets=new ArrayList<>();
    }

    public SqlGroup getWheresql() {
        return sqlGroup;
    }

    public void setWheresql(SqlGroup sqlGroup) {
        this.sqlGroup = sqlGroup;
        this.sqlGroup.setPage(page);
        this.sqlGroup.setRows(rows);
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getLike() {
        return this.like;
    }

    public void addLike(String...likeFile) {
        this.like=qFilter.like(likeFile);
    }

    /**
     * ���ز�ѯ���������
     *
     * @return
     */
    public Object[] getObjectArray() {
        return objects.toArray();
    }

    /**
     * �����Ҫ����ʵ�������
     * @param obj
     * @return
     */
    public void addObject(Object...obj){
        objects.addAll(Arrays.asList(obj));
        qFilter=new QFilter(obj);
    }

    /**
     * �õ���ѯָ����Ҫ���ص���
     * @return
     */
    public String getResult() {
        return qFilter.lines();
    }

    /**
     * ���ò�ѯָ�������У�������hiddenResult()����ͬʱʹ��
     * @param column
     * @return
     */
    public void addResult(String...column) {
        for(String col:column)
            qFilter.show(col);
    }

    /**
     * ���ò�ѯָ�����صķ�����,������addResult()����ͬʱʹ��
     * @param column
     * @return
     */
    public void hiddenResult(String...column) {
        for(String col:column)
            qFilter.hidden(col);
    }

    public JOIN getJoin() {
        return join;
    }

    public void setJoin(JOIN join) {
        this.join = join;
    }


    public String getSort() {
        return qFilter.sort(sortSets);
    }

    public QueryBuilder addSort(String field, Sort sortenum) {
        sortSets.add(new SortSet(field,sortenum));
        return this;
    }

    public void limit( int page, int rows) {
        this.page=page;
        this.rows=rows;
    }


}
