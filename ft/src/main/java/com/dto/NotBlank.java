package com.dto;

import java.lang.annotation.*;

/**
 * @author fanteng
 * @date 2022/5/4 11:14
 * @description
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(NotBlank.List.class)
public @interface NotBlank {
    String message() default "{javax.validation.constraints.NotBlank.message}";

    Class<?>[] groups() default {};


    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        NotBlank[] value();
    }
}
