package com.isi.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhulei
 * @create 2022-05-04 21-20
 * @description 用户类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {
    @TableId(type= IdType.AUTO)
    private int  userId                          ;
    private String institutionName       ;
    private String institutionType               ;
    private String institutionInstruction;
    @TableField("institution_postalCode")
    private String institutionPostalCode         ;
    private String institutionAddress    ;
    private String userEmail                     ;
    @TableField("user_operatorName")
    private String userOperatorName      ;
    @TableField("user_phoneNum")
    private String userPhoneNum                  ;
    private String userName              ;
    @JsonIgnore
    private String userPassword                  ;
    private int userPrivileges        ;
    //    @TableField(exist = false)
    //    private String token;
}
