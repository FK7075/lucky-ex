package com.lucky.jacklamb.annotation.mvc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.lucky.jacklamb.enums.RequestMethod;
import com.lucky.jacklamb.enums.Rest;

/**
 * ��@Controllerע��һ��ʹ������URLӳ��(֧��Rest����URL) value:��ע�����϶���һ�������ļ��ṹ
 * ��ע�ڷ����϶���һ����Ӧ���������ϵ�value�뷽���ϵ�valueΨһ��ʶһ����Ӧ���� eg:
 * Rest����URL:http://127.0.0.1:8080/lucky/server/query/fk/12 +@Controller
 * +@RequestMapping("/server") public class Controller{
 * 
 * +@RequestMapping("/query->username,aga") public String
 * query(@RestParam("age")int uAge,@RestParam("username")String uName){ ...
 * uName=fk uAge=12 }
 * 
 * } Lucky���������urlΨһ��λ��һ��Controller���� method[]������������ͽ������ƣ�Ĭ�Ͻ���GET POST PUT
 * DELETE ��������
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
