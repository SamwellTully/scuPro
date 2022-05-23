package com.isi.Service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isi.Mapper.AdminMapper;
import com.isi.Service.AdminService;
import com.isi.pojo.Admin;
import com.isi.pojo.User;
import com.isi.utils.JWTUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhulei
 * @create 2022-05-07 17-45
 * @description AdminService
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    public static int PAGE_SIZE=10;

    @Override
    public String executeLogin(String admin_name, String password) {
        String token=null;
        try {
            Admin admin = getAdminByName(admin_name);
            //String encodePwd = MD5Utils.getPwd(dto.getPassword());
            if (!password.equals(admin.getAdminPassword())) {
                throw new Exception("密码错误");
            }
            token = JWTUtils.generateToken(String.valueOf(admin.getAdminId()));
        } catch (Exception e) {
            //留下的坑一
            log.warn("用户不存在or密码验证失败=======>{}");
        }
        return token;
    }

    @Override
    public Admin getAdminByName(String admin_name) {

        return baseMapper.selectOne(new LambdaQueryWrapper<Admin>().eq(Admin::getAdminName,admin_name));
    }

    @Override
    public Boolean addAdmin(Admin admin) {
        Admin adminByName = getAdminByName(admin.getAdminName());
        if (adminByName!=null){
            return false;
        }

        try{
            int i = baseMapper.insert(admin);
        }
        catch (Exception e){
            e.toString();
            return false;
        }
        return true;
    }

    @Override
    public Map<String,Object> selectUserPage( String institutionName,
                                     String institutionType,Integer pageIndex,Integer PageSize) {
        if(institutionName.equals("")){
            institutionName=null;
        }
        if(institutionType.equals("")){
            institutionType=null;
        }
        if(pageIndex==null){
            pageIndex=1;
        }
        Page<User> userPage1;
        if(PageSize!=null)
        {
             userPage1= new Page<>((pageIndex - 1) * PageSize, PageSize);
        }
        else
        {
            userPage1=new Page<>((pageIndex - 1) * 10, PAGE_SIZE);
        }
        try {
            Page<User> userPage = baseMapper.selectUserPage(userPage1, institutionName, institutionType);


            Map<String,Object> pageResult=new HashMap<>();
            pageResult.put("page",userPage.getRecords());
            pageResult.put("totalPageNum",userPage.getPages());
            pageResult.put("currentPageNum",pageIndex);
            return pageResult;
        }
        catch (Exception e){
            return null;
        }

    }


}
