package com.isi.Controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.isi.Service.GeneralService;
import com.isi.dto.APIResult;
import com.isi.pojo.GeneralTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/G")
@RestController

@CrossOrigin(origins = "*", maxAge = 3600)
public class GeneralController {

    @Autowired
    private GeneralService generalService;

    //    分页 ’‘’路径传参
    @PostMapping("/Pages")
    public IPage<GeneralTable> getPages( Integer currentPage,  Integer pageSize) {
        return generalService.getPage(currentPage, pageSize);
    }

//       路径传参
    @PostMapping("/Propers")
    public APIResult getProper( String tableName) {
       generalService.getProper(tableName);
        return APIResult.succ("创建成功", generalService.getProper(tableName));
    }
//     内容替换
    @PostMapping("/relacement")
    public APIResult Contentreplacement(MultipartFile file, String relationString, String hashString) throws Exception {
        Map<String,String> relationMap = new HashMap<>();
        relationMap = (Map<String, String>) JSON.parse(relationString);
        Map<String,Map<String,String>> hashMap = new HashMap<>();
        hashMap = (Map<String, Map<String, String>>) JSON.parse(hashString);
        List<Map<String,String>> listmaps = new ArrayList<>();
        listmaps = generalService.Conreplacement(generalService.Cleandata(file,relationString), relationMap,hashMap);
        return APIResult.succ("替换成功", listmaps);
    }
    @PostMapping("/getdata")
    public List<Map<String,Object>> Gettarget(String tableName){
        List<Map<String,Object>> data = generalService.Gettarget(tableName);
        return data;
    }
    @GetMapping("/getdatabase")
    public List<Map<String,Object>> Getdatabase(){
        List<Map<String,Object>> database = generalService.GetDatebase();
        return database;
    }
    @PostMapping("/IsNotEnume")
    public Boolean JudgeEnume(String tableName,String columnName){
        List<Map<String,Object>> maplist = new ArrayList<>();
        maplist = generalService.IsNotEnume(tableName, columnName);
        for (Map map :maplist){
            if(map.get("token")!=null)
            {
                if(map.get("token").equals("true")) return true;
                else return false;
            }
        }
            return false;
    }

    @PostMapping("/IsNotEnumeData")
    public APIResult GetEnume(String tableName,String columnName){
        return APIResult.succ("枚举项",generalService.IsNotEnume(tableName, columnName));
    }
    @PostMapping("/NotEnumeData")
    public APIResult GetNotEnume(String tableName,String columnName){
        return APIResult.succ("不是枚举项",generalService.NotEnume(tableName,columnName));
    }
    @GetMapping("/selectGenertable")
    public APIResult Getgenertable(){
        return APIResult.succ("查询成功",generalService.GetGeneraltable());
    }
    @PostMapping("/GetAllData")
    public APIResult GetAllData(String tableName){return APIResult.succ("查询成功",generalService.GetAllData(tableName));}
}