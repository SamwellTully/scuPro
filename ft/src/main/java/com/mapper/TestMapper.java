package com.mapper;

import com.entity.TestEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TestMapper {
/**
 * @param [id]
 * @return com.entity.TestEntity
 * @author zhulei
 * @date 2022/4/27 22:24
 * @description
 */

    public TestEntity selectById(int id);
}
