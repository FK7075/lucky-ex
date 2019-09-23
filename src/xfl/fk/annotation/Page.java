package xfl.fk.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mapperģʽ�µķ�ҳ����
 * 	value��COUNT����SQL[SELECT COUNT(*) FROM book]�� LIMIT����SQL[SELECT * FROM book LIMIT ?,?]
 *  �Ļ���SQL��ҳ������ģʽ��mapper�ӿڵķ����̶�Ϊ(int ��ǰҳҳ��,int ��ҳ��)
 * 	strategy�����Զ���ʽ��ҳ������һ�����Զ���(֧��ref��Class)
 * 	method:���Զ���ʽ��ҳ��������ʹ�õĲ��Է���
 * @author fk-7075
 *
 */
@Target({ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Page {
	String value() default "";
	String strategy() default "";
	String method() default "";
}
