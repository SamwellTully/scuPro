package com.isi.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author fanteng
 * @date 2022/5/19 20:59
 * @description
 */
public interface ReadFileService {
    Map<Integer, Map<String, String>> readExcelContent(MultipartFile file) throws IOException;
}
