package com.isi.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.isi.pojo.GeneralTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface GeneralService extends IService<GeneralTable> {
    /*向总表中添加新表的描述和表名称*/
    Boolean Gupdate(@Param("GTalename") String GTalename, @Param("GTabledescription") String GTabledescription);

    IPage<GeneralTable> getPage(@Param("currentPage") Integer currentPage,@Param("pageSize") Integer pageSize);

    Boolean delectdate(@Param("GTalename") String GTalename);

    List<Map<String,Object>> getProper(@Param("tableName") String tableName);

    int SelCondition(@Param("GTalename") String GTalename);

//        内容替换
    List<Map<String, String>> Conreplacement(@Param("file") MultipartFile file,@Param("relationMap") Map<String,String> relationMap,@Param("hashMap") Map<String,Map<String,String>> hashMap) throws Exception;
    List<Map<String,Object>> Gettarget(@Param("tableName") String tableName);
    List<Map<String,Object>> GetDatebase();
//    Boolean writedate(@Param("tableName") String tableName,@Param("Map") Map map,@Param("List<Map>") List<Map<String,Object>> listMap);


}
