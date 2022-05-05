package com.vo;

import com.constant.FileServiceTypeEnum;
import com.dto.ApiModelProperty;
import com.dto.ApiModelPropertyEnum;

import java.sql.Date;

/**
 * @author fanteng
 * @date 2022/5/4 21:57
 * @description
 */
public class FileVO {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("相关业务id")
    private String moduleId;

    @ApiModelProperty("相关业务类型")
    private String moduleType;

    @ApiModelPropertyEnum(FileServiceTypeEnum.class)
    private Integer fileLocationType;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("文件大小")
    private String fileSize;

    @ApiModelProperty("文件类型")
    private String fileType;

    @ApiModelProperty("文件路径")
    private String filePath;

    @ApiModelProperty("上传人")
    private Long createUser;

    @ApiModelProperty("updateTime")
    private Date updateTime;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("文件展示url")
    private String fileUrl;

}
