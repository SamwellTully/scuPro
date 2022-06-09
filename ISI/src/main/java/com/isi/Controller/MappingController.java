package com.isi.Controller;

import com.isi.Service.MappingService;
import com.isi.dto.APIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/mappings")
@RestController

@CrossOrigin(origins = "*", maxAge = 3600)
public class MappingController {
    @Autowired
    private MappingService mappingService;
    @PostMapping("/increase")
    public APIResult Increase(int UserId, String Tablename, Map<String, String> Relationship){
        Boolean token = false;
        token = mappingService.IncreaseMapping(UserId,Tablename,Relationship);
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
}

