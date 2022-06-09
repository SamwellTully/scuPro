package com.isi.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * name:sml
 * time:2022/6/9
 * 保存映射
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("mappingtable")
public class Mapping {
    @TableId(type= IdType.AUTO)
    private int id;
    @TableField("User_id")
    private int userId;
    @TableField("Mapping_tableName")
    private String Tablename;
    @TableField("Mapping_relation")
    private String Relationship;
}
