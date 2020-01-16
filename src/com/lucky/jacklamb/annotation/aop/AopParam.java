package com.lucky.jacklamb.annotation.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ������ǿ����
 * @author fk-7075
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AopParam {
	
	/**
	 * ������ǿ�����Ĳ���<br>
	 * value������ָ����д��,��ͬ��ǰ׺����ͬ�ĺ��壺<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;
	 * ref:��ʾ��IOC������IDΪid��������ö�Ӧλ�õĲ�����eg:params={"ref:beanId"}<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;
	 * ind:��ʾ����ʵ������Ϊ��ǿ�����Ĳ�����eg:params={"ind:index"},��ʾ����ʵ���������б��еĵ�index������Ϊ��ǿ�����Ĳ���<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;
	 * ��ǰ׺:����Java�������͵�ֵ<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;
	 * [method]:��ʾ����ʵ������Ӧ��Method��������Ϊ��ǿ�����Ĳ�������λ�õĲ������ͱ���ΪMethod<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;
	 * [params]:��ʾ����ʵ�����Ĳ����б���Ϊ��ǿ����Ĳ�������λ�õĲ������ͱ���ΪObject[]<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;
	 * [target]:��ʾ����ʵ���Class��Ϊ��������λ�õĲ������ͱ���ΪClass<br>
	 * @return
	 */
	String value();

}
