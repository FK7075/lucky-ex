package com.lucky.jacklamb.annotation.orm.mapper;

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
 * value() SQl�������Class
 * properties() SQl�����ļ���classpth( eg : com/lucky/mapper/user.properties)
 * codedformat() properties�����ļ��ı����ʽ(Ĭ��ֵΪgbk)
 * @author fk-7075
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Mapper {
	
	/**
	 * Ϊ��Mapper���ָ��һ��ΨһID��Ĭ�ϻ�ʹ��[����ĸСд����]��Ϊ�����ΨһID
	 * @return
	 */
	String id() default "";
	
	/**
	 * Mapper�ӿ���ʹ�õ�����Դ��Ĭ��defaultDB
	 * @return
	 */
	String dbname() default "defaultDB";
	
	/**
	 * �ⲿSQl�������Class
	 * @return
	 */
	Class<?> value() default Void.class;
	
	/**
	 * �ⲿSQl�����ļ���classpth( eg : com/lucky/mapper/user.properties)
	 * @return
	 */
	String[] properties() default {};
	
	/**
	 * properties�����ļ��ı����ʽ(Ĭ��ֵΪgbk)
	 * @return
	 */
	String  codedformat() default "gbk";
}
