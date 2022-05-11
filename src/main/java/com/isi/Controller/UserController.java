package com.isi.Controller;

import com.isi.Service.UserService;
import com.isi.dto.APIResult;
import com.isi.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhulei
 * @create 2022-05-04 21-33
 * @description 用户控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private  UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public APIResult login(String username , String password) {
        String token = userService.executeLogin(username,password);
        if (ObjectUtils.isEmpty(token)) {
            return APIResult.fail("账号密码错误",null);
        }
        Map<String, String> map = new HashMap<>(16);
        map.put("token", token);
        return APIResult.succ("登入成功",map);
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public APIResult register(String institutionName,String institutionType,String institutionInstruction,
                    String institutionPostalCode,String institutionAddress ,String userEmail,String userOperatorName,
                              String userPhoneNum,String userName,String userPassword) {
        User addUser =  User.builder().
                        userName(userName)
                        .userPassword(userPassword)
                        .userEmail((userEmail))
                        //默认设置权限为0
                        .userPrivileges(0)
                        .userPhoneNum(userPhoneNum)
                        .userOperatorName(userOperatorName)
                        .institutionName(institutionName)
                        .institutionAddress(institutionAddress)
                        .institutionPostalCode(institutionPostalCode)
                        .institutionInstruction(institutionInstruction)
                        .institutionType(institutionType)
                        .build();
        User user = userService.executeRegister(addUser);
        if (ObjectUtils.isEmpty(user)) {
            return APIResult.fail("账号注册失败,用户名或邮箱已经使用",null);
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("user", user);
        return APIResult.succ("注册成功",map);
    }
}
