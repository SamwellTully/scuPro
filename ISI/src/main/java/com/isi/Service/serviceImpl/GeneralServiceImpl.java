package com.isi.Service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isi.Mapper.GeneralMapper;
import com.isi.Service.GeneralService;
import com.isi.pojo.GeneralTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeneralServiceImpl extends ServiceImpl<GeneralMapper, GeneralTable> implements GeneralService {
    @Autowired
    private GeneralMapper generalMapper;
    @Autowired
    private GeneralService generalService;

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


//    @Override
//    public Boolean writedate(String tableName,Map map, List<Map<String, Object>> listMap) {
//        generalMapper.Writing(tableName,map, listMap);
//        return null;
//    }


}