package com.isi.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.isi.pojo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreateMapper extends BaseMapper<CreateTable> {
    Boolean Buildtable(@Param("tableName") String tableName,
                       @Param("tableDescription") String tableDescription,
                       @Param("customTables") List<CustomTable> customTables);

    Boolean DropTable(@Param("tableName") String tableName);

    Boolean EnumeTable(@Param("tableName") String tableName,
                       @Param("columnName" ) String columeName,
                       @Param("enumes") String enumes,
                       @Param("tokenEnume") String tokenEnume);
    Boolean NotEnumeTable(@Param("tableName") String tableName,
                          @Param("columnName" ) String columeName,
                          @Param("lengthMin") int lengthMin,
                           @Param("lengthMax") int lengthMax,
                          @Param("tokenEnume") String tokenEnume);
}
