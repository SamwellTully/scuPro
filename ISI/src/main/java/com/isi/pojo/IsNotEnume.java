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
public class IsNotEnume {
    public String tableName;
    private String columnName;
    private boolean tokenEnume;
    private List<Enume> itemEnume;
    private int lengthMin;
    private int lengthMax;
}
