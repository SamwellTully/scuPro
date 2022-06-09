package com.isi.Service.serviceImpl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isi.Mapper.MappingMapper;
import com.isi.Service.MappingService;
import com.isi.pojo.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 不允许更新，只允许插入
 */
@Service
public class MappingServiceImpl extends ServiceImpl<MappingMapper, Mapping> implements MappingService {


    @Autowired
    private MappingMapper mappingMapper;
    @Override
    public Boolean IncreaseMapping(int UserId, String Tablename, Map<String, String> Relationship) {
        Mapping mapping = new Mapping();
        mapping.setUserId(UserId);
        mapping.setTablename(Tablename);
//        map 转json
        Object obj = JSONArray.toJSON(Relationship);
        String json = obj.toString();

        mapping.setRelationship(json);
        mappingMapper.insert(mapping);
        return true;
    }

    @Override
    public Boolean DeleteMapping(int id) {
        int row = mappingMapper.deleteById(id);
        if(row == 1)return true;
        else return false;
    }

    @Override
    public Map<Object,Map<String,String>> CheckMapping(int userId, String Tablename) {
        Map<Object,Map<String,String>> map = new HashMap<>();
        List<Mapping> lists = mappingMapper.selectList(new QueryWrapper<Mapping>()
                .eq("User_id",userId)
                .eq("Mapping_tableName",Tablename)
        );
//        做一个for的遍历，然后创建一个Map 全部存储到里面
        for(int i=0;i<lists.size();i++){
            Map<String,String> temp = null;
            temp = (Map) JSON.parse(lists.get(i).getRelationship());
            map.put(lists.get(i).getId(),temp);
        }
        return map;
    }
}
