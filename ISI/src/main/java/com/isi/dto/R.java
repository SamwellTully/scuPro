package com.isi.dto;

import java.util.Map;

public class R {
    private Integer code;
    private String msg;
    private Map<String,Object> data;


    public R(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static R ok(){
        return new R(200,"success");
    }

    public static R ok(String msg){
        return new R(200,msg);
    }

    public static R ok(Integer code,String msg){
        return new R(code,msg);
    }

    public static R serviceError(String msg){
        return new R(500,msg);
    }

    public static R serviceError(Integer code,String msg){
        return new R(code,msg);
    }

    public static R clientError(String msg){
        return new R(400,msg);
    }

    public static R clientError(Integer code,String msg){
        return new R(code,msg);
    }




    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
