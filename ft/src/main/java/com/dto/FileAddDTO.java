package com.dto;

import com.constant.*;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;

/**
 * @author fanteng
 * @date 2022/5/4 11:12
 * @description
 */
public class FileAddDTO {
//    @ApiModelProperty("相关业务id(无业务可写死一个id)")
//    @NotBlank(message = "相关业务id不能为空")
//    private String moduleId;
//
//    @ApiModelProperty("相关业务类型(无模块写1)")
//    @NotBlank(message = "相关业务类型不能为空")
//    private String moduleType;

    @ApiModelPropertyEnum(enumDesc = "文件类型",value = FileServiceTypeEnum.class)
    @CheckEnum(enumClazz = FileServiceTypeEnum.class,message = "文件类型错误")
    private Integer fileLocationType;

    @ApiModelProperty("文件名称")
    @NotBlank(message = "文件名称不能为空")
    private String fileName;

    @ApiModelProperty("文件路径")
    @NotBlank(message = "文件路径不能为空")
    private String filePath;
}
