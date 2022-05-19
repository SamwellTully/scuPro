package com.isi.Controller;

import com.isi.Service.CreateService;
import com.isi.Service.GeneralService;
import com.isi.dto.APIResult;
import com.isi.pojo.CreateTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 创表和将新表名和描述添加到总表中
 * 添加删除
 */
@RequestMapping("/C")
@RestController
@CrossOrigin
public class CreateController {
    Boolean token;
    @Autowired
    private CreateService createService;
    @Autowired
    private GeneralService generalService;
    @RequestMapping(value = "/createTable",method = RequestMethod.POST) //json字符串
    public APIResult create(String JsonString){
        CreateTable createTable = new CreateTable();
        createTable = createService.Create_Json(JsonString);
        token = generalService.Gupdate(createTable.getTableName(),
                                       createTable.getTableDescription());

        System.out.println("========create de token="+token);
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
            System.out.println("======delect=========");
            return APIResult.succ("删除成功",1);
        }else {
            //data : 应为返回查询结果。。。。🐟
            return APIResult.fail("删除失败",-1);
        }
    }

}
