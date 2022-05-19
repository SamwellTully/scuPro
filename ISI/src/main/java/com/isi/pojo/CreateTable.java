package com.isi.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 建表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTable {

    private String tableName;
    private String tableDescription;
    private List<CustomTable> customTables;
}
