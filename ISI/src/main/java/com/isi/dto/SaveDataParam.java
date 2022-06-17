package com.isi.dto;


import java.util.List;
import java.util.Map;

/**
 * @author fanteng
 * @date 2022/6/15 20:55
 * @description 数据保存到库中接收参数的封装
 */

public class SaveDataParam {
    /**
     * 数据
     */
    private List<Map<String, String>> data;

    /**
     * 映射关系
     */
    private Map<String,String> relationMap;
    /**
     * 表名
     */
    private String tableName;

    public List<Map<String, String>> getData() {
        return data;
    }

    public void setData(List<Map<String, String>> data) {
        this.data = data;
    }

    public Map<String, String> getRelationMap() {
        return relationMap;
    }

    public void setRelationMap(Map<String, String> relationMap) {
        this.relationMap = relationMap;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
