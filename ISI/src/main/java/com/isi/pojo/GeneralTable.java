package com.isi.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("Generaltable")
public class GeneralTable {
    @TableId(type= IdType.AUTO)
    private int id;
    @TableField("GeneralTable_name")
    private String GTalename;
    @TableField("GeneralTable_description")
    private String GTabledescription;
}
