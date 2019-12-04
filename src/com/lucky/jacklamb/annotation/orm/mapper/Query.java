package com.lucky.jacklamb.annotation.orm.mapper;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.lucky.jacklamb.enums.JOIN;

/**
 * 
 * ����ʽ�����ܲ�ѯ(�����ѯ���������Ӳ�ѯ����ҳ������ģ����ָ�������к����ط�����...)<br>
 * queryBuilder() �Ƿ�ʹ��queryBuilderģʽ(Ĭ�ϲ�ʹ��)
 * join() ���ӷ�ʽ<br><br>
 * limit() �Ƿ�����ҳ��ѯ���������Ĭ�ϲ����б��е��������int������Ϊ��ҳ����<br>
 * sort() ���������ʽΪ��   {"field:1","field2:-1"},1Ϊ����-1Ϊ����<br>
 * sResults:(showResults)������Ҫ��ѯ���ֶ�<br>
 * hResults:(hiddenResults)����Ҫ���ص��ֶ�<br>ע��sResults��hResults����ͬʱ��ʹ��
 * ģ����ѯ��ģ����ѯ���ֶβ�����Ҫ�ֶ����룬���������޶�ΪString��List[Strirng],����ʹ��ʱ��Ҫ@Like�ı��
 * expression() ���ӱ��ʽ����������<br>
 * 	1.ǿ����  ��ǰ���������֮�����ʹ��ǿ����<br>
 * 		tab1-->tab2 [-->]   
 * 		<br>��ʾtab2����������ڵ�tab1ʹ���������Ϊ����������������<br>
 * 	2.������<br>
 *      tab1-->tab2--tab3 [--] <br>
 *      ��ʾtab3������������ڵ�tab2��tab1��ʹ���������Ϊ����������������<br>
 *  3.ָ������<br>
 *  	tab1-->tab2--tab3&lt2&gttab4 [&ltn&gt] <br>
 *  	��ʾtab4��������ڵ�λ������������2�ű���tab1ʹ���������Ϊ����������������<br>
 *  	--><==>&lt0&gt  --<==>&lt1&gt<br>
 *  ��expressionȱʡʱ���ײ�������·�ʽ�Զ�����һ��expression<br>(queryObjTab1-->queryObjTab2-->...-->queryObjTabn)
 * @author fk-7075
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Query {
	boolean queryBuilder() default false;
	String expression() default "";
	JOIN join() default JOIN.INNER_JOIN;
	boolean limit() default false;
	String[] sort() default {};
	String[] sResults() default {};
	String[] hResults() default {};
}
