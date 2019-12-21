package com.lucky.jacklamb.annotation.ioc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.lucky.jacklamb.enums.Rest;

/**
 * ��MVC�д����ڱ�ʶһ��Controller���
 * 	value������ʹ�ô�ע������������һ��IOC���
 * 	prefix��MVC�е���ͼ��λ��ǰ׺(eg: /WEB_INF/jsp/)
 * 	suffix:MVC�е���ͼ��λ�ĺ�׺(eg: .jsp)
 * -------------------------------------------
 * 	ʹ��"return String"�ķ�ʽ����ת�����ض����Ŀ�ĵ�(����ֵΪString�ķ���)
 * 	1.ת����ҳ�棺��ǰ׺ return page
 * 	2.ת����Controller����:return forward:method
 *	3.�ض���ҳ�棺return page:pageing
 *	4.�ض���Controller������return redirect:method
 * @author fk-7075
 *
 */
/**
 * ����һ��Controller���
 * @author fk-7075
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
	
	/**
	 * Ϊ��Controller���ָ��һ��ΨһID��Ĭ�ϻ�ʹ��[����ĸСд����]��Ϊ�����ΨһID
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
	String[] ipSection() default "";
	
	/**
	 * ָ����Controller�����з����ķ���ֵ�������<br>
	 * 1.Rest.NO(Ĭ��ѡ��)��ת�����ض�����,ֻ�Է���ֵ����ΪString�Ľ�����д���<br>
	 *  &nbsp;&nbsp;&nbsp;
	 * a.ת����ҳ�棺��ǰ׺ return page<br>
     * 	&nbsp;&nbsp;&nbsp;
     * b.ת����Controller����:return forward:method<br>
     *	&nbsp;&nbsp;&nbsp;
     * c.�ض���ҳ�棺return page:pageing<br>
     *	&nbsp;&nbsp;&nbsp;
     * d.�ض���Controller������return redirect:method<br>
     * 2.Rest.TXT��������ֵ��װΪtxt��ʽ�������ظ��ͻ���<br>
     * 3.Rest.JSON��������ֵ��װΪjson��ʽ�������ظ��ͻ���<br>
     * 4.Rest.XML��������ֵ��װΪxml��ʽ�������ظ��ͻ���
	 * @return
	 */
	Rest rest() default Rest.NO;
	
	/**
	 * ��ͼ��λ��ǰ׺(eg: /WEB_INF/jsp/),ֻ����rest=Rest.NOʱ��������
	 * @return
	 */
	String prefix() default "";
	
	/**
	 * ��ͼ��λ�ĺ�׺(eg: .jsp),ֻ����rest=Rest.NOʱ��������
	 * @return
	 */
	String suffix() default "";
}
