package xfl.fk.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
  * DI���ע�⣬Ϊ���װ��һ��Map����
 * 	key_type:������key������
 * 	value_type��������value������
 * 	keys:����key��ֵ
 * 	values�����ö�Ӧvalue��ֵ
 * 	ref_keys�������������͵�keyֵ
 * 	ref_values�������������͵�valueֵ
 * @author fk-7075
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LuckyMap {
	String key_type() default "";
	String value_type() default "";
	String[] keys() default {};
	String[] values() default {};
	String[] ref_keys() default {};
	String[] ref_values() default {};

}
