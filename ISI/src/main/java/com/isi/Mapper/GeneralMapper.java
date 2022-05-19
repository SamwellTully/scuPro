package com.isi.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.isi.pojo.GeneralTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GeneralMapper extends BaseMapper<GeneralTable> {
    List<Map<String, Object>> Showstructure(@Param("tableName") String tableName);
    //数据传输到数据库List<>;
//    Boolean Writing(@Param("tableName") String tableName,@Param("Map") Map map,@Param("List<Map>") List<Map<String,Object>> listMap);
}
