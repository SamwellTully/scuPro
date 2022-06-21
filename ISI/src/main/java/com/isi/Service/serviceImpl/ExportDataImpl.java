package com.isi.Service.serviceImpl;
import com.isi.Service.ExportData;
import com.isi.utils.JdbcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExportDataImpl implements ExportData {
    @Autowired
    private JdbcUtils jdbcUtils;
    @Override
    public List<List<String>> exportData(String tableName,List<String> filedNames) {
        //获得数据库中的数据
        List<List<String>> data = jdbcUtils.queryTable(tableName,filedNames);
        return data;
    }

}
