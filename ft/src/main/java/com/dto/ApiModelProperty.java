package com.dto;

/**
 * @author fanteng
 * @date 2022/5/4 11:13
 * @description
 */
public @interface ApiModelProperty {
    String value() default "";

    String name() default "";

    String allowableValues() default "";

    String access() default "";

    String notes() default "";

    String dataType() default "";

    boolean required() default false;

    int position() default 0;

    boolean hidden() default false;

    String example() default "";

    boolean readOnly() default false;

    String reference() default "";

    boolean allowEmptyValue() default false;
}
