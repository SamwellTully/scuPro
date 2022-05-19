package com.isi.Controller;

import com.isi.Service.ReadFileService;
import com.isi.dto.Result;
import com.isi.utils.ExcelTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Map;

/**
 * @author fanteng
 * @date 2022/5/19 20:56
 * @description
 * 上传和读文件的接口   postman测试地址：localhost:8000/upload/import
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/upload")
public class FileController {
    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private ReadFileService readFileService;
    @PostMapping("/import")
    public Result importProject(MultipartFile file) throws IOException {
        String postfix = ExcelTool.getPostfix(file.getOriginalFilename());
        if (!"xlsx".equals(postfix) && !"xls".equals(postfix) && !"csv".equals(postfix) ) {
            return Result.error("导入失败，请选择正确的文件格式上传，本系统只支持xlsx、xls、csv格式");
        }
        Map<Integer, Map<String, String>> integerMapMap = readFileService.readExcelContent(file);
        return Result.success("读取成功",integerMapMap);
    }
}
