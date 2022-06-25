package com.isi.Service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isi.Mapper.GeneralMapper;
import com.isi.Service.GeneralService;
import com.isi.Service.ReadFileService;
import com.isi.dto.Result;
import com.isi.pojo.GeneralTable;
import com.isi.utils.ExcelTool;
import org.apache.ibatis.annotations.Param;
import org.nfunk.jep.JEP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GeneralServiceImpl extends ServiceImpl<GeneralMapper, GeneralTable> implements GeneralService {
    @Autowired
    private GeneralMapper generalMapper;
    @Autowired
    private GeneralService generalService;
    @Autowired
    private ReadFileService readFileService;
    @Override
    public Boolean Gupdate(String GTalename, String GTabledescription)  {
        GeneralTable general = new GeneralTable();
        general.setGTalename(GTalename);
        general.setGTabledescription(GTabledescription);

        if (generalService.SelCondition(GTalename) > 0) {
            System.out.println("表不存在");
//            throw new Exception("该表已存在");
            return false;
        } else {
            generalMapper.insert(general);
            return true;
        }
    }

    @Override
    public IPage<GeneralTable> getPage(Integer currentPage, Integer pageSize) {
        IPage page = new Page(currentPage,pageSize);
        generalMapper.selectPage(page,null);
        return page;
    }

    @Override
    public Boolean delectdate(String GTalename) {
        GeneralTable general = new GeneralTable();
        general.setGTalename(GTalename);
        if (generalService.SelCondition(GTalename) > 0) {
            HashMap<String,Object> map = new HashMap<>();
            map.put("GeneralTable_name",general.getGTalename());
            generalMapper.deleteByMap(map);
            return true;
        } else {
            System.out.println("=====" + general.getGTalename()+"不存在");
            return false;
        }
    }

//    查到表的所有属性
    @Override
    public List<Map<String, Object>> getProper(String tableName) {
        List<Map<String,Object>> li = generalMapper.Showstructure(tableName);
        List proper = new ArrayList<>();
        for (Map<String,Object> mapList:li){
            proper.add(mapList.get("Field"));
        }
        return proper;
    }

    @Override
    public List<Map<String, Object>> GetAllData(String tableName) {
        List<Map<String,Object>> listmap = new ArrayList<>();
        listmap = generalMapper.SelectAllData(tableName);
        return listmap;
    }

    @Override
    public int SelCondition(String GTalename) {
        GeneralTable general = new GeneralTable();
        general.setGTalename(GTalename);
        int count = generalMapper.selectCount(new QueryWrapper<GeneralTable>()
                .eq("GeneralTable_name", general.getGTalename())
        );
        return count;
    }
    @Override
    public double MatFormula(String Formula,double value) throws Exception {
        JEP jep = new JEP();
        jep.addVariable("X",value);
        jep.parseExpression(Formula);
        double result =  jep.getValue();
        return result;
    }

    //    内容替换
    @Override
    public List<Map<String, String>> Conreplacement(List<Map<String,String>> listmap,Map<String,String> relationMap,Map<String,Map<String,String>> hashMap) throws Exception {

//能把单位换算和内容覆盖写在一起
//                                                                                 切割公式
        String regExF = "^\\{(.*)\\}(.*)";
        Pattern Pformul = Pattern.compile(regExF);
//                                                                                 普通切割
        String regEx="^([0-9]*[.]?[0-9]+)(.*)";
        Pattern pattern = Pattern.compile(regEx);

        String hashmapkey_value_code = String.valueOf(new StringBuffer());
        String hashmapkey_unit_code = String.valueOf(new StringBuffer());

        String hashmap_value_code = String.valueOf(new StringBuffer());
        String hashmap_unit_code = String.valueOf(new StringBuffer());

        String listmap_value_code = String.valueOf(new StringBuffer());
        String listmap_unit_code = String.valueOf(new StringBuffer());

        String Formul = String.valueOf(new StringBuffer());
        String Unit = String.valueOf(new StringBuffer());

        String listmap_Data = String.valueOf(new StringBuffer());
        String listmap_Unit = String.valueOf(new StringBuffer());

        String Fcon_unit = String.valueOf(new StringBuffer());
        for(int i = 0; i< listmap.size(); i++) { // 对整个遍历listmap从i=0，开始
            for (String hashkey : hashMap.keySet()) { //对hashMap 的key进行遍历  hashmap就是前端传的数据
                for (String listkey : listmap.get(i).keySet()) { //对listmap的key进行遍历，取第0个map
                    if (listmap.get(i).get(listkey) == null) continue;//如果listmap列表中为空时，不做内容替换
                    if (hashkey.equals(listkey)) { // 判断 hashMap的某个key是否等于 listmap第0个map的key
                        Matcher mat = null;
                        Matcher Mat = null;
                        for (String hashmapkey : hashMap.get(hashkey).keySet()) {//hashmapkey是hashmap中的那个map ，key 指的是对hashmap中的map中的key遍历
                            mat = pattern.matcher(hashmapkey);
                            Mat = Pformul.matcher(hashMap.get(hashkey).get(hashmapkey));
                            Boolean token = mat.find();
                            Boolean flag = Mat.find();
                            mat = pattern.matcher(hashmapkey);
                            if (hashmapkey.equals(listmap.get(i).get(listkey)) && flag == false) {//如果hashmapkey中的value与某个listmap中map确定的key的指向的value的内容相同
                                listmap.get(i).put(listkey, hashMap.get(hashkey).get(hashmapkey));//内容替换,字符串的内容替换，全覆盖
                            }
                            else if(flag){
                                Mat = Pformul.matcher(hashMap.get(hashkey).get(hashmapkey));
                                while(Mat.find()){
                                    String formul = Mat.group(1);
                                    String unit = Mat.group(2);
                                    Formul = formul; // 存储公式
                                    Unit = unit; // 存储单位
                                }
                                Matcher mat_listmap = pattern.matcher(listmap.get(i).get(listkey));//切开listmap中的value，将单位和数值分开
                                while (mat_listmap.find()) {
                                    String listmap_data = mat_listmap.group(1);
                                    String listmap_unit = mat_listmap.group(2);
                                    listmap_Data = listmap_data; // 存储list数值
                                    listmap_Unit = listmap_unit; // 存储list单位
                                }
                                Matcher mat_conkey = pattern.matcher(hashmapkey);
                                while(mat_conkey.find()){
                                    String conkey = mat_conkey.group(2);
                                    Fcon_unit = conkey;
                                }
                                if(Fcon_unit.equals(listmap_Unit)){
                                    double Fcon_data = Double.parseDouble(String.valueOf(listmap_Data));
                                    double result = generalService.MatFormula(Formul,Fcon_data);
                                    String Newresult = String.valueOf(result);
                                    listmap.get(i).put(listkey, Newresult);
                                }
                            }
                            else if (token) {
                                Matcher matcher_hashmapkey = pattern.matcher(hashmapkey);// 切开hashmap中的key
                                while (matcher_hashmapkey.find()) {
                                    String hashmapkey_value_group = matcher_hashmapkey.group(1);
                                    String hashmapkey_unit_group = matcher_hashmapkey.group(2);
                                    hashmapkey_value_code = hashmapkey_value_group; // value_code 里面存储数值
                                    hashmapkey_unit_code = hashmapkey_unit_group; // unit_code 里面存储 单位
                                }
                                //切开hashmap中的value，将单位和数值分开
                                Matcher matcher_hashmap = pattern.matcher(hashMap.get(hashkey).get(hashmapkey));//切开hashmap中的value，将单位和数值分开
                                while (matcher_hashmap.find()) { //切开的是value
                                    String hashmap_value_group = matcher_hashmap.group(1);
                                    String hashmap_unit_group = matcher_hashmap.group(2);
                                    hashmap_value_code = hashmap_value_group; // value_code 里面存储数值
                                    hashmap_unit_code = hashmap_unit_group; // unit_code 里面存储 单位
                                }
                                //切开listmap中的value，将单位和数值分开
                                Matcher matcher_listmap = pattern.matcher(listmap.get(i).get(listkey));//切开listmap中的value，将单位和数值分开
                                while (matcher_listmap.find()) {
                                    String listmap_value_group = matcher_listmap.group(1);
                                    String listmap_unit_group = matcher_listmap.group(2);
                                    listmap_value_code = listmap_value_group; // value_code 里面存储数值
                                    listmap_unit_code = listmap_unit_group; // unit_code 里面存储 单位
                                }
                                if (hashmapkey_unit_code.equals(listmap_unit_code)) {
//                                        hashmap的key的数值  hashmap的value的数值 listmap的value的数值将数值转为 double型便于操作
                                    double doublehashmapkey = Double.parseDouble(String.valueOf(hashmapkey_value_code));
                                    double doublehashmapvalue = Double.parseDouble(String.valueOf(hashmap_value_code));
                                    double doublelistmapvalue = Double.parseDouble(String.valueOf(listmap_value_code));
//                                   定义一个转换数
                                    double convert = 0.0;
                                    convert = doublelistmapvalue / doublehashmapkey * doublehashmapvalue;
//                                    获得变换之后的值  （只是数值）
                                    String Newconvert = String.valueOf(convert);
                                    listmap.get(i).put(listkey, Newconvert);
                                }
                            }
                        }

                    }
                }
            }
        }
        return listmap;
    }
    @Override
    public List<Map<String, Object>> Gettarget(String tableName) {
        List<Map<String,Object>> data = generalMapper.Selecttarget(tableName);
        return data;
    }

    @Override
    public List<Map<String,Object>> GetDatebase() {
        List<Map<String,Object>> database = generalMapper.SelectDatebase();
        return database;
    }

    @Override
    public List<Map<String, String>> Cleandata(MultipartFile file,String relationString) throws Exception {
        Map<String,String> relationMap = new HashMap<>();
        relationMap = (Map<String, String>) JSON.parse(relationString);
        List<Map<String, String>> content = new ArrayList<>();
        String postfix = ExcelTool.getPostfix(file.getOriginalFilename());

        if (("xlsx".equals(postfix) || "xls".equals(postfix))) {content = readFileService.readExcelContent(file);}
        else if ("csv".equals(postfix)) {content = readFileService.readCSV(file);}

        List<Map<String,String>> starlistmap = new ArrayList<>(content);//解析完的excel文件传输的数据

//初步清洗清洗数据
        List<Map<String,String>> listmap = new ArrayList<>();
        for(int i = 0;i < starlistmap.size();i++){
            Map<String,String> middleMap = new HashMap<>();
            for(String relationkey :relationMap.keySet()){
                for(String listkey : starlistmap.get(i).keySet()){
                    if (relationkey.equals(listkey)){
                        middleMap.put(relationkey,starlistmap.get(i).get(relationkey));
                    }
                }
            }
            listmap.add(middleMap);
        }
        return listmap;
    }

    @Override
    public List<Map<String, Object>> IsNotEnume(String tableName, String columnName) {
        String token = generalMapper.SelectEnume(tableName,columnName);
        List<Map<String,Object>> map = new ArrayList<>();
        Map<String,Object> tokenMap = new HashMap<>();
//        枚举型
        if (token == null) {
            map = generalMapper.NotEnumeration(tableName, columnName);
            tokenMap.put("token","false");
            map.add(tokenMap);
        }
        else if(token.equals("true"))
        {
            map = generalMapper.Enumeration(tableName, columnName);
            tokenMap.put("token","true");
            map.add(tokenMap);
        }
        return map;
    }

    @Override
    public List<Map<String, String>> NotRelationData(List<Map<String, String>> listmap, Map<String, String> relationMap, Map<String,Map<String,String>> hashMap) {
//        String listmap_unit_code = String.valueOf(new StringBuffer());
//        String hashmap_unit_code = String.valueOf(new StringBuffer());
//
//        List<Map<String,String>> listmaps = new ArrayList<>();
//        String regEx="^([0-9]*[.]?[0-9]+)(.*)";
//        Pattern pattern = Pattern.compile(regEx);
//        for(int i = 0;i <listmap.size();i++){
//            boolean flag = true;
//
//            for(String listkey : listmap.get(i).keySet()){
//                for(String hashkey : hashMap.keySet()){
//                    if(listkey.equals(hashkey)){
//                        for(String hashkey_key : hashMap.get(hashkey).keySet()){
//                            Matcher mat =null;
//                            Map<String,String> middleMap = new HashMap<>();
//                                mat = pattern.matcher(hashMap.get(hashkey).get(hashkey_key));
//                                if (hashMap.get(hashkey).get(hashkey_key).equals(listmap.get(i).get(listkey))) {
//                                }
//                                else if(mat.find())
//                                {
//                                    if(listmap.get(i).get(listkey) == null){
//                                        flag = false ;
//                                        continue;
//                                    }
//
//                                    //切开hashmap中的value，将单位和数值分开
//                                    Matcher matcher_hashmap = pattern.matcher(hashMap.get(hashkey).get(hashkey_key));//切开hashmap中的value，将单位和数值分开
//                                    while (matcher_hashmap.find()) { //切开的是value
//                                        String hashmap_unit_group = matcher_hashmap.group(2);
//                                        hashmap_unit_code = hashmap_unit_group; // unit_code 里面存储 单位
//                                    }
//                                    //切开listmap中的value，将单位和数值分开
//                                    Matcher matcher_listmap = pattern.matcher(listmap.get(i).get(listkey));//切开listmap中的value，将单位和数值分开
//                                    while (matcher_listmap.find()) {
//                                        String listmap_unit_group = matcher_listmap.group(2);
//                                        listmap_unit_code=listmap_unit_group; // unit_code 里面存储 单位
//                                    }
//                                    if(hashmap_unit_code.equals(listmap_unit_code));
//                                    else flag = false;
//                                }
//                                else {
//                                    flag = false;
//                                }
//
//                            }
//                        }
//                }
//            }
//
//            if(flag) {
//                listmaps.add(listmap.get(i));
//            }
//        }
        return null;
    }

    @Override
    public List<Map<Integer, String>> NotRelationMsg(List<Map<String, String>> listmap, Map<String, String> relationMap, Map<String, Map<String, String>> hashMap) {
        return null;
    }
    @Override
    public List<Map<String, Object>> NotEnume(String tableName, String columnName) {
        return  generalMapper.NotEnumeration(tableName,columnName);
    }

    @Override
    public  List<GeneralTable> GetGeneraltable() {
        List<GeneralTable> generalTables = generalMapper.selectList(null);
        return generalTables;
    }
    @Override
    public List<String> UnitData(List<Map<String, String>> listmap, String columnName) {
        List<String> unit = new ArrayList<>();
        String regExe = "^([0-9]*[.]?[0-9]+)(.*)";
        Pattern pattern = Pattern.compile(regExe);
        String listmap_Unit = String.valueOf(new StringBuffer());
        for(int i = 0;i < listmap.size();i++){
            Matcher mat = null;
            Boolean token = true ;
            Boolean flag ;
            mat = pattern.matcher(listmap.get(i).get(columnName));
            flag = mat.find();
            if (flag==false)continue;
            else
            {
                mat = pattern.matcher(listmap.get(i).get(columnName));
                while (mat.find()) {
                    String listmap_unit = mat.group(2);
                    listmap_Unit = listmap_unit;
                }
            }
            if(i==0) unit.add(listmap_Unit);
            else {
                for(int j = 0;j < unit.size(); j++){
                    if(unit.get(j).equals(listmap_Unit)) {
                        token = false;
                    }
                }
                if(token == true)unit.add(listmap_Unit);
            }
        }

        return unit;
    }
}