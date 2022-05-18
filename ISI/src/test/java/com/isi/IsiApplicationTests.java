package com.isi;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.isi.Mapper.AdminMapper;
import com.isi.Mapper.UserMapper;
import com.isi.Service.AdminService;
import com.isi.Service.UserService;
import com.isi.pojo.Admin;
import com.isi.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class IsiApplicationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminMapper adminMapper;

    @Test
    void contextLoads() {
        System.out.println(("----- selectAll method test ------"));
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            String str = String.valueOf(i);

        }
        userService.saveBatch(users);
    }
    @Test
    void baseMapTest(){
        String username="username0";
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserName, username));

    }
    @Test
    void addAdminTest(){
//        List<Admin> admins=new ArrayList<Admin>();
//
//
//        Admin admin = new Admin();
//            admin.setAdminAddress("address"+String.valueOf(30));
//            admin.setAdminEmail("email"+String.valueOf(30));
//            admin.setAdminName("name"+String.valueOf(30));
//            admin.setAdminPassword(("password"+String.valueOf(30)));
//            admin.setAdminPhoneNum("phoneNum"+String.valueOf(30));
//            admin.setAdminRealName("realName"+String.valueOf(30));


        int delete;
        int admin = adminMapper.delete(new LambdaQueryWrapper<Admin>().eq(Admin::getAdminId,11));
        //System.out.println(delete);
        //Boolean aBoolean = adminService.addAdmin(admin);


    }
    @Test
    void updateTest(){
        User user = new User();
        user.setUserName("username0");
        user.setUserPassword("123456");
        userService.update(user, new LambdaQueryWrapper<User>().eq(User::getUserName, user.getUserName()));
    }
}
