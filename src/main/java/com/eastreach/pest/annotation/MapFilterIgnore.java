package com.eastreach.pest.annotation;

import java.lang.annotation.*;

/**
 * MapFilter算子排除字段
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MapFilterIgnore {
}
