package com.isi.Controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.isi.Service.GeneralService;
import com.isi.dto.APIResult;
import com.isi.pojo.GeneralTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        return APIResult.succ("创建成功", true);
    }
//     内容替换
    @PostMapping("/relacement")
    public APIResult Contentreplacement(MultipartFile file,  Map<String,String> relationMap,  Map<String,Map<String,String>> hashMap) throws Exception {
        Map<String,Map<String,String>> hashmap = new HashMap<>();//内容替换: String 指的是哪行 （前端传表）
                                                                    //Map<String,String>指的是替换规则 （男 = man）
        Map<String,String> mateMap = new HashMap<>(); //测试
        Map<String,String> mateMap1 = new HashMap<>();//测试
        mateMap.put("男","man");
        mateMap.put("女","woman");
        hashmap.put("性别",mateMap);
        mateMap1.put("1ul","111ml");
        hashmap.put("每份样本数量",mateMap1);
        Map<String,String> relationmap = new HashMap<>(); //关系映射

        relationmap.put("性别","sex");
        relationmap.put("每份样本数量","fen");
        relationmap.put("出生地","chusheng");

//        这只是测试用的
//        如果真的使用 要把                     👇 变为上面的relationMap   hashmap转为 hashMap
//        postman无法测试，因为postman传输的 不是map 需要前端将获取的数据（映射等）进行转化为 Map<String,String> 和 Map<String,Map<String,String>>
//        带单位
       generalService.Conreplacement(file,relationmap,hashmap);

        return APIResult.succ("替换成功", generalService.Conreplacement(file,relationmap,hashmap));
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
}