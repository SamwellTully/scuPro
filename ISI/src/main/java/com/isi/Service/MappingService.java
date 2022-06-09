package com.isi.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.isi.pojo.Mapping;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MappingService extends IService<Mapping> {

//    <用户id,库名>
//    <用户id，映射>
    Boolean IncreaseMapping(@Param("User_id") int UserId ,@Param("Table_Name") String Tablename,@Param("Relationship") Map<String,String> Relationship);
    Boolean DeleteMapping(@Param("id") int id);
    Map<Object,Map<String,String>> CheckMapping(@Param("User_id") int userId,@Param("Mapping_tableName") String Tablename);

}
