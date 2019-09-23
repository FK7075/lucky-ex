package xfl.fk.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AOP相关操作的注解，用于定义一个扩展类
 * 	value:标记在类上用于定义这个IOC组件的ID,标记在方法上定义一个扩展方法，
 * 		  "组件ID/方法ID"唯一确定一个扩展方法
 * @author fk-7075
 *
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Expand {
	String value() default "";
}
