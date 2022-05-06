package com.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author fanteng
 * @date 2022/5/4 10:34
 * @description
 */
@Data
@TableName(value = "t_file")
public class FileEntity {
    /**
     * 文件id
     */
    private String moduleId;
    /**
     * 上传文件类型
     */
    private String moduleType;
    /**
     * 文件位置类型
     */
    private Integer fileLocationType;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件大小
     */
    private String fileSize;
    /**
     * 文件类型，程序中枚举控制，文件类型
     */
    private String fileType;
    /**
     * 文件key，用于文件下载
     */
    private String filePath;
    /**
     * 创建人，即上传人或者单位
     */
    private Long createrUser;
    /**
     * 创建时间
     */
    private Date createTime;
}
