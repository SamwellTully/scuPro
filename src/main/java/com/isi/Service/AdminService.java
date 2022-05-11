package com.isi.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.isi.pojo.Admin;
import com.isi.pojo.User;

public interface AdminService extends IService<Admin> {
    String executeLogin(String username,String password);
    Admin getAdminByName(String admin_name);
    Boolean addAdmin(Admin admin);
}
