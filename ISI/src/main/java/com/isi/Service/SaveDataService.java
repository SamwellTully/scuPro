package com.isi.Service;

import com.isi.dto.Result;

import java.util.List;
import java.util.Map;

/**
 * @author fanteng
 * @date 2022/5/28 22:33
 * @description讲内容替换的数据导入数据库
 */
public interface SaveDataService {
    Result saveData(List<Map<String, String>> data,Map<String,String> relationMap,String tableName);
}
