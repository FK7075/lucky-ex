package com.lucky.jacklamb.annotation.mvc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MVC中定义一个文件上传操作
 * 	names：表单中<form enctype="multipart/form-data">---<input type='file'>的name属性值所组成的数组
 * 	filePath：对应上传到项目文件夹的位置
 * 	type：允许上传的文件类型(eg:  .jpg,.jpeg,.png)
 * 	maxSize:允许上传的最大文件大小(单位:kb)
 * @author fk-7075
 *
 */
@Target({ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Upload {
	String[] names() default "";
	String[] filePath() default "";
	String type() default "";
	int maxSize() default 0;
}
