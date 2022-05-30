package com.isi.Service.serviceImpl;

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
//        int count = generalMapper.selectCount(new QueryWrapper<GeneralTable>()
//                .eq("GeneralTable_name", general.getGTalename())
//        );
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
    public int SelCondition(String GTalename) {
        GeneralTable general = new GeneralTable();
        general.setGTalename(GTalename);
        int count = generalMapper.selectCount(new QueryWrapper<GeneralTable>()
                .eq("GeneralTable_name", general.getGTalename())
        );
        return count;
    }

//    内容替换
    @Override
    public List<Map<String, String>> Conreplacement(MultipartFile file,Map<String,String> relationMap,Map<String,Map<String,String>> hashMap) throws IOException {

        Map<Integer, Map<String, String>> content = new HashMap<>();
        String postfix = ExcelTool.getPostfix(file.getOriginalFilename());

        if (("xlsx".equals(postfix) || "xls".equals(postfix))) {content = readFileService.readExcelContent(file);}
        else if ("csv".equals(postfix)) {content = readFileService.readCSV(file);}

        Collection<Map<String, String>> map = content.values();
        List<Map<String,String>> starlistmap = new ArrayList<>(map);//解析完的excel文件传输的数据

        String listmap_value_code = String.valueOf(new StringBuffer());          //正则时使用 值     例：1cm   value = 1 ；处理总数据表
        String listmap_unit_code = String.valueOf(new StringBuffer());          //正则时使用  单位   例： 1cm   unit = cm;

        String hashmapkey_value_code = String.valueOf(new StringBuffer());          //正则时使用 前端反馈的表的key
        String hashmapkey_unit_code = String.valueOf(new StringBuffer());

        String hashmap_value_code = String.valueOf(new StringBuffer());          //正则时使用 值   前端反馈表
        String hashmap_unit_code = String.valueOf(new StringBuffer());          //正则时使用  单位



//清洗数据
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

//能把单位换算和内容覆盖写在一起
        String regEx="^([0-9]*[.]?[0-9]+)(.*)";
        Pattern pattern = Pattern.compile(regEx);

        for (String hashkey : hashMap.keySet()) { //对hashMap 的key进行遍历  hashmap就是前端传的数据
            for(String listkey : listmap.get(0).keySet()){ //对listmap的key进行遍历，取第0个map
                if(hashkey.equals(listkey)){ // 判断 hashMap的某个key是否等于 listmap第0个map的key
                    for(int i = 0;i<listmap.size();i++){ // 对整个遍历listmap从i=0，开始
                        Matcher mat =null;
                        for (String hashmapkey : hashMap.get(hashkey).keySet()) {//hashmapkey是hashmap中的那个map ，key 指的是对hashmap中的map中的key遍历
                            mat = pattern.matcher(hashmapkey);
                            if (hashmapkey.equals(listmap.get(i).get(listkey))) {//如果hashmapkey中的value与某个listmap中map确定的key的指向的value的内容相同
                                listmap.get(i).put(listkey, hashMap.get(hashkey).get(hashmapkey));//内容替换,字符串的内容替换，全覆盖
                            }
                             if(mat.find())
                            {
                                if(listmap.get(i).get(listkey) == null)continue;//如果listmap列表中为空时，不做内容替换
                                Matcher matcher_hashmapkey = pattern.matcher(hashmapkey);// 切开hashmap中的key
                                while (matcher_hashmapkey.find()) { //切开的是value
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
                                        listmap_value_code=listmap_value_group; // value_code 里面存储数值
                                        listmap_unit_code=listmap_unit_group; // unit_code 里面存储 单位
                                    }

                                    if(hashmapkey_unit_code.equals(listmap_unit_code)) {
//                                        hashmap的key的数值  hashmap的value的数值 listmap的value的数值将数值转为 double型便于操作
                                        double doublehashmapkey = Double.parseDouble(String.valueOf(hashmapkey_value_code));
                                        double doublehashmapvalue = Double.parseDouble(String.valueOf(hashmap_value_code));
                                        double doublelistmapvalue = Double.parseDouble(String.valueOf(listmap_value_code));
//                                   定义一个转换数
                                        double convert = 0.0;
                                        convert = doublelistmapvalue / doublehashmapkey * doublehashmapvalue;
//                                    获得变换之后的值  （只是数值）
                                        String Newconvert = String.valueOf(convert);
                                        String Newvalue = String.join("", Newconvert, hashmap_unit_code);
                                        listmap.get(i).put(listkey, Newvalue);
                                    }
                            }
                        }
                        }
                    }
                }
            }
        return listmap;
    }

}