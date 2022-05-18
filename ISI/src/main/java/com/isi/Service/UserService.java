package com.isi.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.isi.pojo.User;

public interface UserService extends IService<User> {
    String executeLogin(String username,String password);
    User getUserByUsername(String username);
    User executeRegister(User user);
}
