package com.google.samples.apps.sunflower.http.annotation;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author neoliu6
 * @version 创建时间:2016年3月3日 上午10:52:53
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Ignore {
}

