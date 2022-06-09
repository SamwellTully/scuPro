package com.isi.Service.serviceImpl;

import com.isi.Service.ReadFileService;
import com.isi.dto.BaseException;
import com.isi.utils.ExcelTool;
import com.isi.utils.ExcelUtils;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
    public List<Map<String, String>> readExcelContent(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        String postfix = ExcelTool.getPostfix(file.getOriginalFilename());
        return ExcelUtils.readExcelContent(inputStream, postfix);
    }

    @Override
    public List<Map<String, String>> readCSV(MultipartFile file) throws Exception {
        List<Map<String, String>> content = new ArrayList<>();
        List<String> contentList;
        List<String> headerList = new ArrayList<>();
        int i = 0;
        CSVReader csvReader = new CSVReaderBuilder(
                new BufferedReader(
                        new InputStreamReader(file.getInputStream(), "GBK"))).build();
        Iterator<String[]> iterator = csvReader.iterator();
        while (iterator.hasNext()) {
            String[] next = iterator.next();
            if (i == 0) {
                headerList = Arrays.asList(next);
            }
            for (String s : headerList) {
                if (s == null || "".equals(s)) {
                    throw new BaseException("文件格式有误，表头与内容未对齐,请检查后重新上传");
                }
            }
            //去除第一行的表头，从第二行开始
            Map<String, String> map = null;
            if (i >= 1) {
                map = new HashMap<>();
                contentList = Arrays.asList(next);
                if (judge(contentList)){
                    for (int j = 0; j < headerList.size(); j++) {
                        map.put(headerList.get(j), contentList.get(j));

                    }
                    content.add(map);
                }
            }
            i++;
        }
        return content;
    }

    /**
     * 判断csv当中当前行是否全部为空
     */
    private Boolean judge(List<String> list) {
        int a = 0;
        for (String s : list) {
            if (s == null || "".equals(s)) {
                a++;
            }
        }
        return a != list.size();
    }

    /**
     * 判断文件大小
     *
     * @param file  文件
     * @param size  限制大小
     * @param unit  限制单位（B,K,M,G）
     * @return
     */
    @Override
    public Boolean checkFileSize(MultipartFile file, int size, String unit) {
        if (file.isEmpty() || StringUtils.isEmpty(size) || StringUtils.isEmpty(unit)) {
            return false;
        }
        long len = file.getSize();
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        if (fileSize > size) {
            return false;
        }
        return true;
    }
}
