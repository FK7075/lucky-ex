package com.lucky.jacklamb.annotation.ioc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DI��ص�ע��
 * value��IDΪvalue��������ᱻװ�䵽�����ϣ�
 * 		    ��valueΪĬ��ֵʱLucky�������ͼ��ķ�ʽ����װ��
 * 		    �����ҪΪControllerע��Request Response Session��Model������������ʱ����value����Ϊ"request" "response" "session"��"model"���ɣ�
 * @author fk-7075
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
	String value() default "";
}
