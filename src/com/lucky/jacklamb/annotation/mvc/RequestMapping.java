package com.lucky.jacklamb.annotation.mvc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.lucky.jacklamb.enums.RequestMethod;
import com.lucky.jacklamb.enums.Rest;

/**
 * 与@Controller注解一起使用配置URL映射(支持Rest风格的URL) value:标注在类上定义一层虚拟文件结构
 * 标注在方法上定义一个响应方法，类上的value与方法上的value唯一标识一个响应方法 eg:
 * Rest风格的URL:http://127.0.0.1:8080/lucky/server/query/fk/12 +@Controller
 * +@RequestMapping("/server") public class Controller{
 * 
 * +@RequestMapping("/query->username,aga") public String
 * query(@RestParam("age")int uAge,@RestParam("username")String uName){ ...
 * uName=fk uAge=12 }
 * 
 * } Lucky根据请求的url唯一定位到一个Controller方法 method[]：对请求的类型进行限制，默认接受GET POST PUT
 * DELETE 四组请求
 * 
 * @author fk-7075
 *
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
	
	String value() default "";
	String[] ip() default {};
	String ipSection() default "";
	Rest rest() default Rest.NO;
	RequestMethod[] method() default { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE };

}
