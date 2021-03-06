package com.isi.Controller;

import com.isi.Service.GeneralService;
import com.isi.Service.ReadFileService;
import com.isi.dto.Result;
import com.isi.utils.ExcelTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author fanteng
 * @date 2022/5/19 20:56
 * @description
 * 上传和读文件的接口
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/upload")
public class FileController {
    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private ReadFileService readFileService;
    @Autowired
    private GeneralService generalService;
//    xlsx和xls文件格式上传的postman测试地址：localhost:8000/upload/import
    @PostMapping("/import")
    public Result importProject(MultipartFile file) throws IOException {
        String postfix = ExcelTool.getPostfix(file.getOriginalFilename());
        if (!"xlsx".equals(postfix) && !"xls".equals(postfix) && !"csv".equals(postfix) ) {
            return Result.error("导入失败，请选择正确的文件格式上传，本端口只支持xlsx、xls文件格式");
        }
        if (!readFileService.checkFileSize(file,5,"M")){
            return Result.error("文件过大");
        }
        List<Map<String, String>> integerMapMap = readFileService.readExcelContent(file);

        return Result.success("读取成功",integerMapMap);
    }

//    csv文件格式上传postman测试地址：localhost:8000/upload/importCSV
    @PostMapping("/importCSV")
    public Result importCSV(MultipartFile file) throws Exception {
        String postfix = ExcelTool.getPostfix(file.getOriginalFilename());
        if (!"csv".equals(postfix)) {
            return Result.error("导入失败，请选择正确的文件格式，本端口只支持csv文件格式");
        }
        if (!readFileService.checkFileSize(file,5,"M")){
            return Result.error("文件过大");
        }
        List<Map<String, String>> map = readFileService.readCSV(file);
        return Result.success("读取成功",map);
    }
}
