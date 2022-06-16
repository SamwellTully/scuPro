package com.isi.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.isi.pojo.CreateTable;
import com.isi.pojo.CustomTable;
import com.isi.pojo.Enume;

import java.util.List;

public interface CreateService extends IService<CreateTable> {
    Boolean MakeTable(String tableName,String tableDescription, List<CustomTable> customTables);
    Boolean SelectTable(String tableName);
    CreateTable Create_Json(String JsonString);

    Boolean ELmit(String tableName, String columnName, List<Enume> enumes,String tokenEnume);

    Boolean LLmit(String tableName, String columnName, int lengthMin, int lengthMax, String tokenEnume);
}
