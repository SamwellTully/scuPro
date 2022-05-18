package com.isi.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.isi.Service.UserService;
import com.isi.dto.APIResult;
import com.isi.pojo.User;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

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
@Slf4j
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

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public APIResult getUser( String username) {
        User user = userService.getUserByUsername(username);
        if(user!=null)
            return APIResult.succ("用户信息",user);
        else
            return APIResult.fail("没有此用户",null);
    }

    @PostMapping("/update")
    public APIResult updateUser(String institutionName,String institutionType,String institutionInstruction,
                                String institutionPostalCode,String institutionAddress ,String userEmail,String userOperatorName,
                                String userPhoneNum,String userName,String userPassword) {



        User adduser = new User();
        if (userName==null||userName.equals("")){
            return APIResult.fail("用户名错误",null);
        }
        adduser.setUserName(userName);
        if(institutionName!=null && !(institutionName.equals(""))){
            adduser.setInstitutionName(institutionName);
        }
        if(institutionType!=null&&!(institutionType.equals(""))){
            adduser.setInstitutionType(institutionType);
        }
        if(institutionAddress!=null&&!(institutionAddress.equals(""))){
            adduser.setInstitutionAddress(institutionAddress);
        }
        if(institutionType!=null&&!(institutionType.equals("")))
        {
            adduser.setInstitutionType(institutionType);
        }
        if(institutionInstruction!=null&&!(institutionInstruction.equals(""))){
            adduser.setInstitutionInstruction(institutionInstruction);
        }
        if(institutionPostalCode!=null&&!(institutionPostalCode.equals(""))){
            adduser.setInstitutionPostalCode(institutionPostalCode);
        }
        if (userEmail!=null&&!(userEmail.equals(""))){
            adduser.setUserEmail(userEmail);
        }
        if (userOperatorName!=null&&!(userOperatorName.equals(""))){
            adduser.setUserOperatorName(userOperatorName);
        }
        if(userPhoneNum!=null&&!(userPhoneNum.equals(""))){
            adduser.setUserPhoneNum(userPhoneNum);
        }
        if(userPassword!=null&&!(userPassword.equals(""))){
            adduser.setUserPassword(userPassword);
        }
        boolean b = userService.update(adduser, new LambdaQueryWrapper<User>().
                eq(User::getUserName, adduser.getUserName()));

        if(b){
            User userByUsername = userService.getUserByUsername(userName);
            return APIResult.succ("修改成功",userByUsername);
        }
            return APIResult.fail("修改发生了失败",null);
    }
    @RequestMapping("/del")
    public APIResult delUser(Integer id){
        try {
            boolean b = userService.removeById(id);
            if(b==true){
                return APIResult.succ("删除成功",null);
            }
            return APIResult.fail("删除失败",null);
        }
         catch (Exception e){

             log.error(e.toString());
             return APIResult.fail("删除失败",null);
         }
    }

    @RequestMapping("/userPage")
    public APIResult userPage(){

        return null;
    }
}
