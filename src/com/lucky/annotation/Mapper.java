package com.lucky.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注在Mapper接口上，注入一个java类型的mapper配置文件 java配置文件的SQl配置规则：
 * 每个没有使用注解的mapper接口方法都可以在配置类中绑定一组执行SQL，形式为一个String类型变量名+"特定SQL"
 * eg：
 * 普通模式：正常SQL->[SELECT * FROM book WHERE bid=?]
 * 开启基于非空检查的动态sql模式：C:SQL->[C:SELECT * FROM book WHERE bid=? AND bprice=?]
 * 
 * @author fk-7075
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Mapper {
	Class<?> value() default Void.class;
}
