package com.entity;

import java.lang.annotation.*;

/**
 * @author fanteng
 * @date 2022/5/4 10:36
 * @description
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})

public @interface TableName {
    String value() default "";

    String schema() default "";

    boolean keepGlobalPrefix() default false;

    String resultMap() default "";

    boolean autoResultMap() default false;

    String[] excludeProperty() default {};
}
