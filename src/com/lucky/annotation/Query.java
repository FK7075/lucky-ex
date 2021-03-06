package com.lucky.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.lucky.enums.JOIN;

/**
 * 
 * 对象方式的万能查询(单表查询、多表的连接查询、分页、排序、指定返回列...)<br>
 * value() 指定返回的列<br>
 * join() 连接方式<br>
 * limit() 是否开启分页查询，开启后会默认参数列表中的最后两个int参数作为分页条件<br>
 * sort() 设置排序格式为：   {"field:1","field2:-1"},1为升序-1为降序<br>
 * expression() 连接表达式，解释如下<br>
 * 	1.强链接  最前面的两个表之间必须使用强连接<br>
 * 		tab1-->tab2 [-->]   
 * 		<br>表示tab2表与左边相邻的tab1使用主外键作为连接条件进行连接<br>
 * 	2.弱连接<br>
 *      tab1-->tab2--tab3 [--] <br>
 *      表示tab3表跳过左边相邻的tab2与tab1表使用主外键作为连接条件进行连接<br>
 *  3.指定连接<br>
 *  	tab1-->tab2--tab3&lt2&gttab4 [&ltn&gt] <br>
 *  	表示tab4从左边相邻的位置起，向左跳过2张表与tab1使用主外键作为连接条件进行连接<br>
 *  	--><==>&lt0&gt  --<==>&lt1&gt<br>
 *  当expression缺省时，底层会以如下方式自动生成一个expression<br>(queryObjTab1-->queryObjTab2-->...-->queryObjTabn)
 * @author fk-7075
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Query {
	String[] value() default {};
	String expression() default "";
	JOIN join() default JOIN.INNER_JOIN;
	boolean limit() default false;
	String[] sort() default {};
}
