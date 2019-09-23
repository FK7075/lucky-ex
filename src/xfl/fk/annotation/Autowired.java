package xfl.fk.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DI��ص�ע��
 * value��IDΪvalue��������ᱻװ�䵽�����ϣ�
 * 		    ��valueΪĬ��ֵʱLucky�������ͼ��ķ�ʽ����װ��
 * 		    �����ҪΪControllerע��ȫ�ֵ�Request Response Session����value����Ϊ"request" "response" "session"���ɣ�
 * @author fk-7075
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
	String value() default "";
}
