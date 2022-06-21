package com.isi.Service;

import java.util.List;

public interface ExportData {

    List<List<String>> exportData(String tableName, List<String> filedNames);
}
