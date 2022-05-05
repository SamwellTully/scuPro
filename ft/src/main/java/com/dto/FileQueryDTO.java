package com.dto;

import com.constant.FileModuleTypeEnum;
import com.constant.FileServiceTypeEnum;

/**
 * @author fanteng
 * @date 2022/5/4 21:53
 * @description
 */
public class FileQueryDTO  {
    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "业务类型")
    @ApiModelPropertyEnum(FileModuleTypeEnum.class)
    @CheckEnum(enumClazz = FileModuleTypeEnum.class, message = "文件业务类型错误")
    private Integer moduleType;

    @ApiModelProperty(value = "文件位置")
    @ApiModelPropertyEnum(FileServiceTypeEnum.class)
    @CheckEnum(enumClazz = FileServiceTypeEnum.class, message = "文件位置类型错误")
    private Integer fileLocationType;
}
