package com.isi.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author fanteng
 * @date 2022/5/19 20:59
 * @description
 */
public interface ReadFileService {
    List<Map<String, String>> readExcelContent(MultipartFile file) throws IOException;
    List<Map<String, String>> readCSV(MultipartFile file) throws Exception;

    Boolean checkFileSize(MultipartFile file, int size, String unit);
}
