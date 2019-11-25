package com.lucky.jacklamb.annotation.mvc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 用于处理跨域的问题
 * 前端页面代码里我们在ajax请求里面带上 xhrFields: {withCredentials:true}这个属性，表示提供cookie信息,即可实现跨域的session共享问题.
 * origins:允许可访问的域列表(默认为所有域)
 * maxAge:准备响应前的缓存持续的最大时间（以秒为单位，默认为1800s）
 * allowCredentials:是否允许cookie随请求发送(默认为true)
 * method:请求支持的方法(默认：POST, GET, OPTIONS, DELETE)
 * allowedHeaders:允许请求头重的header，默认都支持
 * exposedHeaders:响应头中允许访问的header,默认为空
 * @author fk-7075
 *
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CrossOrigin {
	
	String[] value() default {};

	String[] origins() default {};
	
	String exposedHeaders() default "";
	
	String allowedHeaders() default "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token";
	
	int maxAge() default 1800;
	
	String method() default "POST, GET, OPTIONS, DELETE";
	
	boolean allowCredentials() default true;
	
}
