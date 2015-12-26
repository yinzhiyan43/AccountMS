package org.dc.penguinMVC.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @ClassName: Controller
* @Description: 自定义Controller注解
* @author: dc
* @time: 2014-11-16 下午6:16:40
*
*/ 
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Controller {

    public String value() default "";
}