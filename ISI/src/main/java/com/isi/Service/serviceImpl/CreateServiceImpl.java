package com.isi.Service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isi.Mapper.CreateMapper;
import com.isi.Mapper.GeneralMapper;
import com.isi.Service.CreateService;
import com.isi.Service.GeneralService;
import com.isi.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * sml
 *
 */
@Service
public class CreateServiceImpl extends ServiceImpl<CreateMapper, CreateTable> implements CreateService{

    @Autowired
    private CreateMapper createMapper;
    @Autowired
    private GeneralMapper generalMapper;
    @Override
    public Boolean MakeTable(String tableName, String tableDescription, List<CustomTable> customTables) {
        createMapper.Buildtable(tableName,tableDescription, customTables);
        return true;
    }

    @Override
    public Boolean SelectTable(String tableName) {
        createMapper.DropTable(tableName);
        return true;
    }

    @Override
    public CreateTable Create_Json(String JsonString) {
        Map map = null;
        try{
            map = (Map) JSON.parse(JsonString);
        } catch (ClassCastException | JSONException e) {

            System.out.println("{msg:JSON格式错误, code=-1}");

        }
        List<Map> data = (List)map.get("customTables");
        List<CustomTable> data1=new ArrayList<>();
        for(Map map1 :data){
            data1.add(JSON.parseObject(JSON.toJSONString(map1), CustomTable.class));
        }
        CreateTable create = new CreateTable();
        create.setTableName((String) map.get("tableName"));
        create.setTableDescription((String) map.get("tableDescription"));
        create.setCustomTables(data1);
        return create;
    }

    @Override
    public Boolean ELmit(String tableName, String columnName, List<Enume> enumes,String tokenEnume) {
        for(int i = 0;i<enumes.size();i++) {
            createMapper.EnumeTable(tableName, columnName, enumes.get(i).getEnumes(), tokenEnume);
        }
            return true;
    }

    @Override
    public Boolean LLmit(String tableName, String columnName, int lengthMin,int lengthMax,String tokenEnume) {
         createMapper.NotEnumeTable(tableName,columnName,lengthMin,lengthMax,tokenEnume);
        return true;
    }
}
