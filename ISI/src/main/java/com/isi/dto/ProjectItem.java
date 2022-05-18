package com.isi.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author fanteng
 * @date 2022/5/13 10:18
 * @description
 * 项目清单表实体类
 */
@Data
@TableName("project_item")
public class ProjectItem implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键uuid
     */
    private Integer id;
    /**
     * 项目序号
     */
    private String orderNumber;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 项目内容
     */
    private String content;
    /**
     *  文件类型
     */
    private Integer type;
    /**
     * 上传的单位
     */
    private String unit;
    /**
     * 标签
     */
    private String price;
    /**
     * 数量
     */
    private String count;
    /**
     * 是否已删除[0-否、1-是]
     */
    private String isDeleted;
}
