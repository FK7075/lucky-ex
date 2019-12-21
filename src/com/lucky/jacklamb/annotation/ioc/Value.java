package com.lucky.jacklamb.annotation.ioc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DI��ص�ע��
 * @author fk-7075
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {
	
	/**
	 * ָ��Ҫע��������Ϣ<br>
	 * 1.������ΪJava��������ʱ->[@Value("value")]<br>
	 * 2.������Ϊ�Զ������������ʱ->[@Value("iocID")]
	 * 3.����������ΪJava�������͵�����ʱ->[@Value({"value1",����,"value10"})]<br>
	 * 4.����������ΪJava��Collection����,��������Ϊ�������͵İ�װ����ʱ->[@Value({"value1",����,"value10"})]<br>
	 * 5.����������ΪJava��Collection����,��������Ϊ�Զ������������ʱ->[@Value({"iocID1",����,"iocID10"})]<br>
	 * 6.����������ΪJava��Map����,��������Ϊ�������͵İ�װ����ʱ->[@Value({"key1:value1",����,"key10:value10"})]<br>
	 * 7.����������ΪJava��Map����,��������Ϊ�Զ������������ʱ->[@Value({"iocID1:iocIDa",����,"iocID10:iocIDj"})]<br>
	 * 8.Map���Ե������������6,7<br>
	 * 9.Ĭ�����ʱ,��������ɨ����ƽ����Զ�ע��
	 * @return
	 */
	String[] value() default {};
	
}
