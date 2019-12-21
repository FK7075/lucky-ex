package com.lucky.jacklamb.annotation.mvc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ����һ��ֻ��ӦPOST���������URLӳ��(֧��Rest����URL)<br>
 * 1.��ͨ���͵�URLӳ��<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * -@PostMapping("/server/query")<br>
 * 2.Rest����URLӳ��<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * -@PostMapping("/server/query/name/#{name}/sex/#{sex}")<br>
 * 3.����������ŵ�URLӳ��<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * a."?":ƥ��һ��������ַ���<br>&nbsp;&nbsp;&nbsp;&nbsp;
 * -@PostMapping("/server/?/query")<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * b.xxx*:ƥ��һ����xxx��ͷ���ַ���<br>&nbsp;&nbsp;&nbsp;&nbsp;
 * -@PostMapping("/server/admin_* /query")<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * c.*xxx��ƥ��һ����xxx��β���ַ���<br>&nbsp;&nbsp;&nbsp;&nbsp;
 * -@PostMapping("/server/*_admin/query")<br>
 * @author fk-7075
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PostMapping {
	
	/**
	 * ����һ��url����ӳ��
	 * @return
	 */
	String value() default "";
	
	/**
	 * ָ��һЩ�Ϸ����ʵ�ip��ַ����������ip��ַ�����󽫻ᱻ�ܾ�
	 * @return
	 */
	String[] ip() default {};
	
	/**
	 * ָ��һЩ�Ϸ����ʵ�ip�Σ���������ip��ַ�����󽫻ᱻ�ܾ�
	 * @return
	 */
	String[] ipSection() default {};
	
}
