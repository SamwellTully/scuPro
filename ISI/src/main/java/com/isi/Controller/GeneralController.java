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



/**
 * 字段映射
  */









/**
 * 1.接受前端已建立好的映射 map<String,Object> map中<key 和 value>
 * 2.遍历选中的表，将所有的filed存储到一个list<>中。
 * 3.将所有的内容都存储到list<Map>中
 * 4.1.可以把所有List<Map>中的每一个map的key与value交换
 * 5.用xml去写sql的最底层语言，用foreach去做字符拼接
 */
}