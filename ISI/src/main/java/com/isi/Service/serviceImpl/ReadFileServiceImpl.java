package com.isi.Service.serviceImpl;

import com.isi.Service.ReadFileService;
import com.isi.utils.ExcelTool;
import com.isi.utils.ExcelUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author fanteng
 * @date 2022/5/19 21:00
 * @description
 */
@Service
public class ReadFileServiceImpl implements ReadFileService {
    @Override
    public Map<Integer, Map<String, String>> readExcelContent(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        String postfix = ExcelTool.getPostfix(file.getOriginalFilename());
        return ExcelUtils.readExcelContent(inputStream,postfix);
    }
}
