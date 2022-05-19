package com.isi.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.isi.pojo.CreateTable;
import com.isi.pojo.CustomTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CreateService extends IService<CreateTable> {
    Boolean MakeTable(String tableName,String tableDescription, List<CustomTable> customTables);
    Boolean SelectTable(String tableName);
    CreateTable Create_Json(String JsonString);
}
