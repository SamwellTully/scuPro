package com.isi.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.isi.dto.ProjectItem;
import org.apache.ibatis.annotations.Param;

/**
 * @author fanteng
 * @date 2022/5/13 10:21
 * @description
 */
public interface ExcelMapper extends BaseMapper<ProjectItem> {
    int insertProjectItem(@Param("orderNumber") String orderNumber, @Param("name") String name, @Param("content") String content,
                          @Param("type") Integer type, @Param("unit") String unit, @Param("price") String price, @Param("count") String count);
}
