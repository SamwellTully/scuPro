package com;

import com.entity.TestEntity;
import com.mapper.TestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.alibaba.fastjson.JSON;

@SpringBootTest
class CellDbApplicationTests {
    @Autowired
    TestMapper testMapper ;
    @Test
    void contextLoads() {
        TestEntity testEntity = testMapper.selectById(1);
        System.out.println(JSON.toJSONString(testEntity));
    }

}
