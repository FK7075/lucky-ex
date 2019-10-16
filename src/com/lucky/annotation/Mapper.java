package com.lucky.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ��ע��Mapper�ӿ��ϣ�ע��һ��java���͵�mapper�����ļ� java�����ļ���SQl���ù���
 * ÿ��û��ʹ��ע���mapper�ӿڷ������������������а�һ��ִ��SQL����ʽΪһ��String���ͱ�����+"�ض�SQL"
 * eg��
 * ��ͨģʽ������SQL->[SELECT * FROM book WHERE bid=?]
 * �������ڷǿռ��Ķ�̬sqlģʽ��C:SQL->[C:SELECT * FROM book WHERE bid=? AND bprice=?]
 * 
 * @author fk-7075
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Mapper {
	Class<?> value() default Void.class;
}
