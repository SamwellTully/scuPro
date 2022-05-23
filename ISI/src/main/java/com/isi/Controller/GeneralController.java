package com.isi.Controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.isi.Service.GeneralService;
import com.isi.dto.APIResult;
import com.isi.pojo.GeneralTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/G")
@RestController

@CrossOrigin(origins = "*", maxAge = 3600)
public class GeneralController {

    @Autowired
    private GeneralService generalService;

    //    分页 ’‘’路径传参
    @GetMapping("{currentPage}/{pageSize}")
    public IPage<GeneralTable> getPages(@PathVariable Integer currentPage, @PathVariable Integer pageSize) {
        return generalService.getPage(currentPage, pageSize);
    }


//       路径传参
    @GetMapping("{tableName}")
    public APIResult getProper(@PathVariable String tableName) {

       generalService.getProper(tableName);
        return APIResult.succ("创建成功", 1);
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