package com.isi.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.isi.Service.CreateService;
import com.isi.Service.GeneralService;
import com.isi.Service.SaveDataService;
import com.isi.dto.APIResult;
import com.isi.dto.Result;
import com.isi.dto.SaveDataParam;
import com.isi.pojo.CreateTable;
import com.isi.pojo.Enume;
import com.isi.pojo.IsNotEnume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创表和将新表名和描述添加到总表中
 * 添加删除
 */
@RequestMapping("/C")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CreateController {
    Boolean token;
    @Autowired
    private CreateService createService;
    @Autowired
    private GeneralService generalService;
    @Autowired
    private SaveDataService saveDataService;
    @RequestMapping(value = "/createTable",method = RequestMethod.POST) //json字符串
    public APIResult create(String JsonString){
        CreateTable createTable = new CreateTable();
        createTable = createService.Create_Json(JsonString);
        token = generalService.Gupdate(createTable.getTableName(),
                                       createTable.getTableDescription());
        if (token) {
            createService.MakeTable(createTable.getTableName(),
                    createTable.getTableDescription(),
                    createTable.getCustomTables());
            //data : 应为返回查询结果。。。。🐟
            return APIResult.succ("创建成功",1);

        }else {
            return APIResult.fail("创建失败",-1);
        }
    }

    @RequestMapping(value = "/selectTable",method = RequestMethod.POST)
    public APIResult select(String tableName){
        token = generalService.delectdate(tableName);
        if (token) {
            createService.SelectTable(tableName);
            return APIResult.succ("删除成功",1);
        }else {
            //data : 应为返回查询结果。。。。🐟
            return APIResult.fail("删除失败",-1);
        }
    }
  @RequestMapping("/limits")
  public APIResult Limits(String JsonString){
        List<Map<String,Object>> tempMap = new ArrayList<>();
       tempMap = JSON.parseObject(JsonString,List.class);
       for(int i = 0;i<tempMap.size();i++){
           if(tempMap.get(i).get("tokenEnume").equals("true")){
               List<Enume> enumes = new ArrayList<>();
               List<Map> data = (List)tempMap.get(i).get("itemEnume");
               for(Map map :data){
                   Enume enume = new Enume();
                   enume.setEnumes((String) map.get("enumes"));
                   enumes.add(enume);
               }
               createService.ELmit((String) tempMap.get(i).get("tableName"),
                                   (String) tempMap.get(i).get("columnName"),
                                     enumes,
                                   (String) tempMap.get(i).get("tokenEnume"));
           }
           else {
               createService.LLmit((String) tempMap.get(i).get("tableName"),
                                   (String) tempMap.get(i).get("columnName"),
                                   (int) tempMap.get(i).get("lengthMin"),
                                   (int) tempMap.get(i).get("lengthMax"),
                                  (String) tempMap.get(i).get("tokenEnume"));
           }
       }
       return APIResult.succ("添加成功",true);
  }
    @RequestMapping("/sample")
    public Result SampleData(@RequestBody SaveDataParam saveDataParam){
        if (saveDataParam!=null){
            return saveDataService.saveData(saveDataParam.getData(),saveDataParam.getRelationMap(),saveDataParam.getTableName());
        }
        return new Result(false,"数据保存失败");

    }
}
