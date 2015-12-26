package org.dc.penguinMVC.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义请求路径的java annotation
 * @author: dc
 * @time: 2015-12-13
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)  
public @interface RequestMapping {

    public String value() default "";
}