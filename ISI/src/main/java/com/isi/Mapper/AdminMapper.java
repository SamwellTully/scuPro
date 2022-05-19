package com.isi.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isi.pojo.Admin;
import com.isi.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMapper extends BaseMapper<Admin> {
    Page<User> selectUserPage(@Param("page") Page<User> page, String institutionName,
                              String institutionType);


}
