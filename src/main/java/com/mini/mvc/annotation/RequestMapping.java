package com.mini.mvc.annotation;

import com.mini.mvc.domain.MediaType;
import com.mini.mvc.domain.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhanghaojie on 2017/10/29.
 * url映射注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    /**
     * url path
     *
     * @return
     */
    String value() default "";

    /**
     * 请求方式
     *
     * @return
     */
    RequestMethod method() default RequestMethod.GET;

    /**
     * 文本格式
     *
     * @return
     */
    MediaType produces() default MediaType.TEXT_PLAIN_UTF_8;

}
