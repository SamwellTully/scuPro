package com.isi.Service.serviceImpl;

import com.isi.Service.SaveDataService;
import com.isi.dto.Result;
import com.isi.utils.JdbcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fanteng
 * @date 2022/5/28 22:36
 * @description
 */
@Service
public class SaveDataServiceImpl implements SaveDataService {
    @Autowired
    private JdbcUtils jdbcUtils;

    /**
     * @param data        表格数据
     * @param relationMap 表头和数据库表字段映射关系
     * @param tableName   数据库表名
     * @return
     */
    @Override
    public Result saveData(List<Map<String, String>> data, Map<String, String> relationMap, String tableName) {
        if (data.isEmpty() || relationMap.isEmpty() || tableName.isEmpty()) {
            return new Result(false, "缺少数据，导入数据库异常");
        }
        //解析表头
        List<String> headData = parseHead(data);
        //根据数据库表的映射关系将解析的表头数据进行替换
        List<String> replaceHeadData = replaceHeadData(headData, relationMap);
        //解析数据
        List<List<String>> parseData = parseData(data);
        //sql拼接
        String sql = sqlString(replaceHeadData, parseData, tableName);
        Boolean result = jdbcUtils.insert(sql);
        if (!result) {
            return new Result(result, "插入失败");
        }
        return new Result(result, "插入成功");
    }

    /**
     * 拼接sql语句
     *
     * @param replaceHeadData 数据库的字段名称
     * @param parseData       插入的数据
     * @param tableName       表名
     * @return3
     */
    private String sqlString(List<String> replaceHeadData, List<List<String>> parseData, String tableName) {
        StringBuffer insert = new StringBuffer("insert into " + tableName + " (");
        for (int i = 0; i < replaceHeadData.size(); i++) {
            if (i == replaceHeadData.size() - 1) {
                insert.append(replaceHeadData.get(i) + ")");
            } else {
                insert.append(replaceHeadData.get(i) + ",");
            }
        }
        //数据值的拼接
        insert.append(" values ");
        for (int i = 0; i < parseData.size(); i++) {
            insert.append("(");
            for (int j = 0; j < parseData.get(i).size(); j++) {
                if (j == parseData.get(i).size() - 1) {
                    insert.append("'" + parseData.get(i).get(j) + "'");
                } else {
                    insert.append("'" + parseData.get(i).get(j) + "',");
                }
            }
            if (i == parseData.size() - 1) {
                insert.append(")");
            } else {
                insert.append("),");
            }
        }
        return insert.toString();
    }

    /**
     * 将表头数据的值进行一个替换
     *
     * @param headData
     * @param relationMap
     * @return
     */
    private List<String> replaceHeadData(List<String> headData, Map<String, String> relationMap) {
        for (int i = 0; i < headData.size(); i++) {
            if (!relationMap.get(headData.get(i)).isEmpty()) {
                headData.set(i, relationMap.get(headData.get(i)));
            }
        }
        return headData;
    }

    /**
     * 获取excel表头字段
     */
    private List<String> parseHead(List<Map<String, String>> data) {
        ArrayList<String> headList = new ArrayList<>();
        //获得表头
        for (Map.Entry<String, String> stringStringEntry : data.get(0).entrySet()) {
            headList.add(stringStringEntry.getKey());
        }
        return headList;
    }

    /**
     * 解析map将表头和分开提取出来
     *
     * @param data 替换之后的表数据
     * @return
     */
    private List<List<String>> parseData(List<Map<String, String>> data) {
        List<List<String>> lists = new ArrayList<>();
        //获取数据
        for (Map<String, String> datum : data) {
            ArrayList<String> dataList = new ArrayList<>();
            for (Map.Entry<String, String> stringStringEntry : datum.entrySet()) {
                if (StringUtils.isEmpty(stringStringEntry.getValue())) {
                    dataList.add("");
                }else {
                    dataList.add(stringStringEntry.getValue());
                }
            }
            lists.add(dataList);
        }
        return lists;
    }
}
