package xfl.fk.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mapper模式下的分页操作
 * 	value：COUNT类型SQL[SELECT COUNT(*) FROM book]或 LIMIT类型SQL[SELECT * FROM book LIMIT ?,?]
 *  的互补SQL分页，此种模式下mapper接口的方法固定为(int 当前页页码,int 分页数)
 * 	strategy：策略对象方式分页，定义一个策略对象(支持ref和Class)
 * 	method:策略对象方式分页，设置所使用的策略方法
 * @author fk-7075
 *
 */
@Target({ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Page {
	String value() default "";
	String strategy() default "";
	String method() default "";
}
