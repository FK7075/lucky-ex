package com.lucky.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.lucky.enums.JoinWay;

/**
 * 
 * 对象方式的多表连接操作<br>
 * value() 指定返回的列
 * join() 连接方式
 * expression() 连接表达式，解释如下
 * 	1.强链接  最前面的两个表之间必须使用强连接<br>
 * 		tab1-->tab2 [-->]   
 * 		<br>表示tab2表与左边相邻的tab1使用主外键作为连接条件进行连接<br>
 * 	2.弱连接<br>
 *      tab1-->tab2--tab3 [--] <br>
 *      表示tab2表跳过左边相邻的tab1与tab1表使用主外键作为连接条件进行连接<br>
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
public @interface Join {
	String expression() default "";
	JoinWay join() default JoinWay.INNER_JOIN;
	String[] value() default{};
}
