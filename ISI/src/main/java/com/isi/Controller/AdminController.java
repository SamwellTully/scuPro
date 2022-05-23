package com.isi.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isi.Service.AdminService;
import com.isi.Service.UserService;
import com.isi.dto.APIResult;
import com.isi.pojo.Admin;
import com.isi.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhulei
 * @create 2022-05-07 17-37
 * @description AdminController
 */
@RequestMapping("/admin")
@RestController

@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
    @RequestMapping("login")
    public APIResult login(String admin_name, String password){


        String token = adminService.executeLogin(admin_name,password);
        if (ObjectUtils.isEmpty(token)) {
            return APIResult.fail("账号密码错误",null);
        }
        Map<String, String> map = new HashMap<>(16);
        map.put("token", token);
        return APIResult.succ("登入成功",map);
    }
    @RequestMapping("/addAdmin")
    public APIResult addAdmin(String admin_name,String admin_phoneNum,
                              String admin_email,String admin_realName,String admin_password,
                              String admin_address){
        Admin admin = Admin.builder().adminEmail(admin_email).adminAddress(admin_address).adminName(admin_name).
                adminPhoneNum(admin_phoneNum).adminPassword(admin_password).adminRealName(admin_realName)
                .build();
        if (adminService.addAdmin(admin))
            return APIResult.succ("添加成功" , admin);
        else
            return APIResult.fail("添加失败","账户已经存在");

    }
@RequestMapping("/getUserPage")
public APIResult userPage(Integer pageIndex,String institutionName,
                          String institutionType,Integer pageSize){

        Map<String, Object> pageResult = adminService.selectUserPage(institutionName,
                                        institutionType, pageIndex, pageSize);
        if(pageResult==null){
            return APIResult.fail("查询失败",null);
        }
        return APIResult.succ("page",pageResult);
}

//    public APIResult userPage(){
//
//        return APIResult.fail("查询失败",null);
//    }

}
