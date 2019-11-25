package com.lucky.jacklamb.annotation.ioc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DI相关的注解
 * value：ID为value的组件将会被装配到属性上，
 * 		    当value为默认值时Lucky采用类型检查的方式进行装配
 * 		    如果需要为Controller注入Request Response Session和Model对象对象的属性时，将value设置为"request" "response" "session"和"model"即可！
 * @author fk-7075
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
	String value() default "";
}
