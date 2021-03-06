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
    //   解析数学公式
    double MatFormula(@Param("formula") String Formula,@Param("value") double value) throws Exception;

    //        内容替换
    List<Map<String, String>> Conreplacement(@Param("listmap") List<Map<String,String>> listmap,@Param("relationMap") Map<String,String> relationMap,@Param("hashMap") Map<String,Map<String,String>> hashMap) throws Exception;
    List<Map<String,Object>> Gettarget(@Param("tableName") String tableName);
    List<Map<String,Object>> GetDatebase();
    //    Boolean writedate(@Param("tableName") String tableName,@Param("Map") Map map,@Param("List<Map>") List<Map<String,Object>> listMap);
//  清洗数据
    List<Map<String,String>> Cleandata(@Param("file") MultipartFile file,String relationString) throws Exception;

    List<Map<String,Object>> IsNotEnume(@Param("tableName") String tableName,@Param("columnName") String columnName);

    //   没有匹配规则的错误报警
    List<Map<String,String>> NotRelationData(@Param("listmap") List<Map<String,String>> listmap,@Param("relationMap") Map<String,String> relationMap,@Param("hashMap") Map<String,Map<String,String>> hashMap);
    List<Map<Integer,String>> NotRelationMsg(@Param("listmap") List<Map<String,String>> listmap,@Param("relationMap") Map<String,String> relationMap,@Param("hashMap") Map<String,Map<String,String>> hashMap);
    List<Map<String,Object>> NotEnume(@Param("tableName") String tableName,@Param("columnName") String columnName);
    List<GeneralTable> GetGeneraltable();

    //  所有数据单位
    List<String>  UnitData(@Param("listmap") List<Map<String,String>> listmap,@Param("columnName") String columnName);

    //用户界面查找所有数据
    List<Map<String,Object>> GetAllData(@Param("tableName") String tableName);
}
