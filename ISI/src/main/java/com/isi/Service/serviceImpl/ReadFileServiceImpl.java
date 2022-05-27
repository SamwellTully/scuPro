package com.isi.Service.serviceImpl;

import com.isi.Service.ReadFileService;
import com.isi.utils.ExcelTool;
import com.isi.utils.ExcelUtils;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

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

    @Override
    public Map<Integer, Map<String, String>> readCSV(MultipartFile file) {
        Map<Integer, Map<String, String>> content = new HashMap<>();
        List<String> contentList;
        List<String> headerList = new ArrayList<>();
        int i = 0;
        try {
            CSVReader csvReader = new CSVReaderBuilder(
                    new BufferedReader(
                            new InputStreamReader(file.getInputStream(), "GBK"))).build();
            Iterator<String[]> iterator = csvReader.iterator();
            while (iterator.hasNext()) {
                String[] next = iterator.next();
                if (i == 0) {
                    headerList = Arrays.asList(next);
                }
                //去除第一行的表头，从第二行开始
                Map<String, String> map = null;
                if (i >= 1) {
                    map = new HashMap<>();
                    contentList = Arrays.asList(next);
                    for (int j = 0;j < headerList.size();j++) {
                        map.put(headerList.get(j),contentList.get(j));

                    }
                    content.put(i,map);
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        for (String s : headerList) {
//            System.out.println("测试表头字段："+s);
//        }
        return content;
    }
}
