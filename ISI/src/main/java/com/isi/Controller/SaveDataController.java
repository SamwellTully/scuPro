package com.isi.Controller;

import com.isi.Service.SaveDataService;
import com.isi.dto.Result;
import com.isi.dto.SaveDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName SaveDataController
 * @author fanteng
 * @Date 2022/6/13 22:41
 * @Version 1.0
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping( "/save")
@RestController
public class SaveDataController {
    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private SaveDataService saveDataService;

    @PostMapping("/saveData")
    public Result saveData(@RequestBody SaveDataParam saveDataParam){
        if (saveDataParam!=null){
            return saveDataService.saveData(saveDataParam.getData(),saveDataParam.getRelationMap(),saveDataParam.getTableName());
        }
        return new Result(false,"数据保存失败");

    }
}