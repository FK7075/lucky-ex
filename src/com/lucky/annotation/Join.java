package com.lucky.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.lucky.enums.JoinWay;

/**
 * 
 * ����ʽ�Ķ�����Ӳ���<br>
 * value() ָ�����ص���
 * join() ���ӷ�ʽ
 * expression() ���ӱ��ʽ����������
 * 	1.ǿ����  ��ǰ���������֮�����ʹ��ǿ����<br>
 * 		tab1-->tab2 [-->]   
 * 		<br>��ʾtab2����������ڵ�tab1ʹ���������Ϊ����������������<br>
 * 	2.������<br>
 *      tab1-->tab2--tab3 [--] <br>
 *      ��ʾtab2������������ڵ�tab1��tab1��ʹ���������Ϊ����������������<br>
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
public @interface Join {
	String expression() default "";
	JoinWay join() default JoinWay.INNER_JOIN;
	String[] value() default{};
}
