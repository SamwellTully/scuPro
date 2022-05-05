package com.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
@Data
@AllArgsConstructor
@NoArgsConstructor
/**

 * @author zhulei
 * @date 2022/4/27 22:31
 * @description
 */

public class TestEntity {
    private int id ;
    private  String sample_name;
    private List<TestEntityProperty> propertyList;
}
