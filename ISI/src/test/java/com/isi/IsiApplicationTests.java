package com.isi;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.isi.Mapper.*;
import com.isi.Service.*;
import com.isi.pojo.Admin;
import com.isi.pojo.CustomTable;
import com.isi.pojo.User;
import com.isi.utils.JdbcUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class IsiApplicationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private GeneralService generalService;
    @Autowired
    private CreateService createService;
    @Autowired
    private SaveDataService saveDataService;
    @Autowired
    private MappingService mappingService;
    @Autowired
    private MappingMapper mappingMapper;
    @Autowired
    private CreateMapper createMapper;
    @Autowired
    private GeneralMapper generalMapper;
    @Autowired
    private JdbcUtils jdbcUtils;

    @Test
    void contextLoads() {
        System.out.println(("----- selectAll method test ------"));
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            String str = String.valueOf(i);

        }
        userService.saveBatch(users);
    }
    @Test
    void baseMapTest(){
        String username="username1";
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserName, username));

    }
//    @Test
//    void GeneralServiceTest() throws Exception {
//        System.out.println("=======================");
//        generalService.Gupdate("tablename5","description5");
//    }
    @Test
    void CreateTest(){
        System.out.println("<===========create table================>");
        List<CustomTable> cre = new ArrayList<>();
        CustomTable cus = new CustomTable();
        cus.setAttributename("testattributename1");
        cus.setFieldType("int");
        cus.setLengthLimit(10);
        cus.setChoose(true);
        cus.setNotNull(true);
        cre.add(cus);
        createService.MakeTable("testcreate1","test",cre);
    }

    @Test
    void addAdminTest(){
//        List<Admin> admins=new ArrayList<Admin>();
//
//
//        Admin admin = new Admin();
//            admin.setAdminAddress("address"+String.valueOf(30));
//            admin.setAdminEmail("email"+String.valueOf(30));
//            admin.setAdminName("name"+String.valueOf(30));
//            admin.setAdminPassword(("password"+String.valueOf(30)));
//            admin.setAdminPhoneNum("phoneNum"+String.valueOf(30));
//            admin.setAdminRealName("realName"+String.valueOf(30));


        int delete;
        int admin = adminMapper.delete(new LambdaQueryWrapper<Admin>().eq(Admin::getAdminId,11));
        //System.out.println(delete);
        //Boolean aBoolean = adminService.addAdmin(admin);
    }
    @Test
    void updateTest(){
        User user = new User();
        user.setUserName("username0");
        user.setUserPassword("123456");
        userService.update(user, new LambdaQueryWrapper<User>().eq(User::getUserName, user.getUserName()));
    }
    @Test
    void addUser(){
        List<User> users=new ArrayList<User>(20);
        for (int i = 0; i < users.size(); i++) {
            User user=new User();
            user.setUserName("userName"+String.valueOf(i+10));
            user.setUserEmail("UserEmail"+String.valueOf(i+10));
            user.setUserPassword("password");
        }
    }

    @Test
    void getSqlString() {
        String tableName = "test";
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("??????", "man");
        map1.put("??????????????????", "10");
        map1.put("?????????", "??????");
        Map<String, String> map2 = new HashMap<>();
        map2.put("??????", "woman");
        map2.put("??????????????????", "11");
        map2.put("?????????", "??????");
        Map<String, String> map3 =new HashMap<>();
        map3.put("??????","woman");
        map3.put("??????????????????", " ");
        map3.put("?????????", "1?????????22");
        data.add(map1);
        data.add(map2);
        data.add(map3);
        Map<String, String> relationmap = new HashMap<>(); //????????????
        relationmap.put("??????", "sex");
        relationmap.put("??????????????????", "fen");
        relationmap.put("?????????", "chusheng");
        saveDataService.saveData(data, relationmap, tableName);
    }
    @Test
    void Datetest(){
        List<Map<String,String>> data = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        Map<String,String> map1 = new HashMap<>();
        map.put("test_name","test1");
        map.put("test_sex","test1");
        map.put("test_address","test1");
        data.add(map);
        map1.put("test_name","test_name");
        map1.put("test_sex","test_sex");
        map1.put("test_address","test_address");
        System.out.println(data);
        saveDataService.saveData(data,map1,"relation_map1");
    }
    @Test
    public void queryTest(){
        List<String> arr = new ArrayList<>();
        arr.add("name");
        arr.add("sex");
        arr.add("age");
        List<List<String>> book = jdbcUtils.queryTable("exportdata",arr);
        EasyExcel.write();
        System.out.println(book);
    }
    /**
     * ?????????????????????
     */
    @Test
    public void noModelWrite() {
        // ??????1
        String fileName =  ".xlsx";
        // ?????? ????????????????????????class??????????????????????????????sheet?????????????????? ??????????????????????????????
        EasyExcel.write(fileName).head(head()).sheet("??????").doWrite(dataList());
    }

    private List<List<String>> head() {
        List<List<String>> list = ListUtils.newArrayList();
        List<String> head0 = ListUtils.newArrayList();
        head0.add("?????????" + System.currentTimeMillis());
        List<String> head1 = ListUtils.newArrayList();
        head1.add("??????" + System.currentTimeMillis());
        List<String> head2 = ListUtils.newArrayList();
        head2.add("??????" + System.currentTimeMillis());
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }

    private List<List<Object>> dataList() {
        List<List<Object>> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            List<Object> data = ListUtils.newArrayList();
            data.add("?????????" + i);
            data.add(new Date());
            data.add(0.56);
            list.add(data);
        }
        return list;
    }
}
