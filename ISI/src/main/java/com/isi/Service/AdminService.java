package com.isi.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.isi.pojo.Admin;
import com.isi.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface AdminService extends IService<Admin> {
    String executeLogin(String username,String password);
    Admin getAdminByName(String admin_name);
    Boolean addAdmin(Admin admin);
    Map<String,Object> selectUserPage(String institutionName,
                                      String institutionType, Integer pageIndex, Integer PageSize);
}
