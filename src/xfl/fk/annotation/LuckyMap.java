package xfl.fk.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
  * DI相关注解，为组件装配一个Map集合
 * 	key_type:泛型中key的类型
 * 	value_type：泛型中value的类型
 * 	keys:设置key的值
 * 	values：设置对应value的值
 * 	ref_keys：设置引用类型的key值
 * 	ref_values：设置引用类型的value值
 * @author fk-7075
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LuckyMap {
	String key_type() default "";
	String value_type() default "";
	String[] keys() default {};
	String[] values() default {};
	String[] ref_keys() default {};
	String[] ref_values() default {};

}
