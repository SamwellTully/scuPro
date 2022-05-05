package com.dto;

import com.entity.BaseEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author fanteng
 * @date 2022/5/4 11:16
 * @description
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiModelPropertyEnum {
    /**
     * 枚举类对象
     *
     * @return
     */
    Class<? extends BaseEnum> value();

    String example() default "";

    /**
     * 是否隐藏
     *
     * @return
     */
    boolean hidden() default false;

    /**
     * 是否必须
     *
     * @return
     */

    boolean required() default true;

    String dataType() default "";

    String enumDesc() default "";

}
