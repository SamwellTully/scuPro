package com.constant;


/**
 * @author fanteng
 * @date 2022/5/4 21:44
 * @description
 */
public enum FileModuleTypeEnum implements BaseEnum {
    /**
     * path 首字符不能包含\ 或者/
     */

    BACK_USER(1, "backUser/config", "backUser"),

    CODE_REVIEW(2, "codeReview", "CodeReview");

    private Integer value;

    private String path;

    private String desc;

    FileModuleTypeEnum(Integer value, String path, String desc) {
        this.value = value;
        this.path = path;
        this.desc = desc;
    }

    public String getPath() {
        return path;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}

