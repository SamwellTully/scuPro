package com.isi.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomTable {

    private String attributename ;
    private String fieldType;
    private  int lengthLimit;
    private  boolean isNotNull;
    /*选择,choose如果为true说明是可添加*/
    private boolean choose;
}
