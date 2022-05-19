package com.isi.Service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isi.Mapper.UserMapper;
import com.isi.Service.UserService;
import com.isi.dto.APIResult;
import com.isi.pojo.User;
import com.isi.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

/**
 * @author zhulei
 * @create 2022-05-05 16-12
 * @description UserService
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
//    @Autowired
//    private UserMapper userMapper;


    @Override
    public String executeLogin(String username, String password) {
        String token = null;
        try {
            User user = getUserByUsername(username);
            //String encodePwd = MD5Utils.getPwd(dto.getPassword());
            if (!password.equals(user.getUserPassword())) {
                throw new Exception("密码错误");
            }
            token = JWTUtils.generateToken(String.valueOf(user.getUserId()));
        } catch (Exception e) {
            //留下的坑一
            log.warn("用户不存在or密码验证失败=======>{}");
        }
        return token;

    }
    @Override
    public User executeRegister(User user) {
        //查询是否有相同用户名的用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, user.getUserName()).or().eq(User::getUserEmail, user.getUserEmail());
        List<User> dbUser = baseMapper.selectList(wrapper);
        if (!ObjectUtils.isEmpty(dbUser)) {
            return  null;
        }
//        UmsUser addUser = UmsUser.builder()
//                .username(dto.getName())
//                .alias(dto.getName())
//                .password(MD5Utils.getPwd(dto.getPass()))
//                .email(dto.getEmail())
//                .createTime(new Date())
//                .status(true)
//                .build();
        baseMapper.insert(user);

        return user;
    }

    @Override
    public User getUserByUsername(String username) {

        return baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserName, username));
    }
}

