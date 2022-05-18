package com.isi.Controller;

import com.isi.Service.ExcelService;
import com.isi.dto.Result;
import com.isi.utils.ExcelTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author fanteng
 * @date 2022/5/13 10:14
 * @description
 * 文件操作接口
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {
    Logger logger = LoggerFactory.getLogger(ExcelController.class);

    @Autowired
    private ExcelService excelService;

    @PostMapping("/import")
    public Result importProject(@RequestParam(value = "file") MultipartFile file) {
        String postfix = ExcelTool.getPostfix(file.getOriginalFilename());
        if (!"xlsx".equals(postfix) && !"xls".equals(postfix)) {
            return Result.error("导入失败，请选择正确的文件格式支持xlsx或xls");
        }
        return excelService.importProject(file);


    }

    //上传和读文件的接口   postman测试地址：localhost:8000/excel/filed
    @PostMapping("/filed")
    public Result getExcelFiled(@RequestParam(value = "file") MultipartFile file,String name,String date) {
        String postfix = ExcelTool.getPostfix(file.getOriginalFilename());
        if (!"xlsx".equals(postfix) && !"xls".equals(postfix)) {
            return Result.error("导入失败，请选择正确的文件格式支持xlsx或xls");
        }
        return excelService.getExcelFiled(file);
    }
}
