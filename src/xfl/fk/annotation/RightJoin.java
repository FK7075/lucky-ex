package xfl.fk.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启对象模式下的 RIGHT JOIN 级联操作
 * Lucky会扫描传入对象的主外键关系，并使用此关系将两表进行连接
 * 如果子表的外键字段名与主表的主键字段名相同，则会被Lucky视为存在主外键关系
 * 	value:设置所要查询的字段
 * @author fk-7075
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RightJoin {
	String value() default "";
}
