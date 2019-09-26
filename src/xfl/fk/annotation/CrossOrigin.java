package xfl.fk.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于处理跨域的问题
 * origins:允许可访问的域列表
 * maxAge:准备响应前的缓存持续的最大时间（以秒为单位）
 * @author fk-7075
 *
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CrossOrigin {

	String origins() default "*";
	
	String maxAge() default "1800";
	
	String allowCredentials() default "false";
	
}
