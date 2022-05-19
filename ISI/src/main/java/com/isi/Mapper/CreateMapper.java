package com.isi.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.isi.pojo.CreateTable;
import com.isi.pojo.CustomTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreateMapper extends BaseMapper<CreateTable> {
    Boolean Buildtable(@Param("tableName") String tableName,
                       @Param("tableDescription") String tableDescription,
                       @Param("customTables") List<CustomTable> customTables);

    Boolean DropTable(@Param("tableName") String tableName);
}
