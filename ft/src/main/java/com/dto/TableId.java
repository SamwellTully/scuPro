package com.dto;

import java.lang.annotation.*;

/**
 * @author fanteng
 * @date 2022/5/4 14:26
 * @description
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TableId {
    String value() default "";

    IdType type() default IdType.NONE;
}
