package com.constant;

import com.entity.BaseEnum;

/**
 * @author fanteng
 * @date 2022/5/4 11:22
 * @description
 */
public enum FileServiceTypeEnum implements BaseEnum {
    /**
     * 本地文件服务
     */
    LOCAL(1, FileServiceNameConst.LOCAL, "本地文件服务");
    private Integer locationType;

    private String serviceName;

    private String desc;

    FileServiceTypeEnum(Integer locationType, String serviceName, String desc) {
        this.locationType = locationType;
        this.serviceName = serviceName;
        this.desc = desc;
    }

    public String getServiceName() {
        return serviceName;
    }
    @Override
    public Integer getValue() {
        return this.locationType;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }



}
