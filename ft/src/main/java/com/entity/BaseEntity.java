package com.entity;

import com.dto.IdType;
import com.dto.TableId;

import java.util.Date;

/**
 * @author fanteng
 * @date 2022/5/4 14:40
 * @description
 */
public class BaseEntity {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    public BaseEntity(Long id) {
        this.id = id;
    }
}
