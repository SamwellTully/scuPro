package com.isi.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhulei
 * @create 2022-05-07 17-29
 * @description 管理员类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin {
    @TableId(type= IdType.AUTO)
    private int adminId;
    private String adminName;
    @TableField("admin_phoneNum")
    private String adminPhoneNum;
    private String adminEmail;
    @TableField("admin_realName")
    private String adminRealName;
    private String adminPassword;
    private String adminAddress;

}

