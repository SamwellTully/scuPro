package com.dto;

import com.constant.ApiModelPropertyEnum;
import com.constant.FileServiceTypeEnum;

import java.sql.Date;

/**
 * @author fanteng
 * @date 2022/5/4 16:43
 * @description
 */

public class FileDTO {
    private Long id;

    private String moduleId;

    private String moduleType;

    @ApiModelPropertyEnum(FileServiceTypeEnum.class)
    private Integer fileLocationType;

    private String fileName;

    private String fileSize;

    private String fileType;

    private String filePath;

    private Long createUser;

    private Date updateTime;

    private Date createTime;

    private String fileUrl;

}
