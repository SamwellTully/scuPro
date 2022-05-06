package com.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author fanteng
 * @date 2022/5/4 22:00
 * @description
 */
@Data
public class UploadVO {
    @ApiModelProperty(value = "文件名称")
    private String fileName;
    @ApiModelProperty(value = "url")
    private String url;
    @ApiModelProperty(value = "filePath")
    private String filePath;
    @ApiModelProperty(value = "文件大小")
    private Long fileSize;
}
