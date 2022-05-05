package com.dto;

import com.entity.BaseEnum;

/**
 * @author fanteng
 * @date 2022/5/4 16:40
 * @description
 */
public @interface CheckEnum {
    /**
     * 默认的错误提示信息
     *
     * @return String
     */
    String message() default "非法的枚举值";

    /**
     * 枚举类对象 必须实现BaseEnum接口
     *
     * @return
     */
    Class<? extends BaseEnum> enumClazz();

    /**
     * 是否必须
     *
     * @return boolean
     */
    boolean required() default false;

    //下面这两个属性必须添加 :不然会报错
    Class<?>[] groups() default {};

//    Class<? extends Payload>[] payload() default {};
}
