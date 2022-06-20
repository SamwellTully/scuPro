package com.isi.Controller;

import com.alibaba.fastjson.JSON;
import com.isi.Service.MappingService;
import com.isi.dto.APIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/mappings")
@RestController

@CrossOrigin(origins = "*", maxAge = 3600)
public class MappingController {
    @Autowired
    private MappingService mappingService;
    @PostMapping("/increase")
    public APIResult Increase(int UserId, String Tablename, String RelationString){
        Map<String, String> RelationMap = new HashMap<>();
        RelationMap = (Map<String, String>) JSON.parse(RelationString);
        Boolean token = false;
        token = mappingService.IncreaseMapping(UserId,Tablename,RelationMap);
        if (token)return APIResult.succ("插入数据成功",mappingService.CheckMapping(UserId,Tablename));
        else return APIResult.fail("插入数据失败","false");
    }
    @PostMapping("/delete")
    public APIResult delete(int id,int UserId, String Tablename){
        Boolean token = false;
        token = mappingService.DeleteMapping(id);
        if(token)return APIResult.succ("删除成功",mappingService.CheckMapping(UserId,Tablename));
        else return APIResult.fail("删除失败","fail");
    }
    @PostMapping("/check")
    public APIResult check(int UserId,String Tablename){
        return APIResult.succ("查询成功",mappingService.CheckMapping(UserId,Tablename));
    }
}

