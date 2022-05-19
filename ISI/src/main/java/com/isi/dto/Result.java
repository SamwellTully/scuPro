package com.isi.dto;

import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;
import java.util.List;

/**
 * @author fanteng
 * @date 2022/5/13 10:17
 * @description
 * 封装返回的结果
 */
public class Result implements Serializable {
    private boolean flag;//执行结果，true为执行成功 false为执行失败
    private String message;//返回结果信息，主要用于页面提示信息
    private Object data;//返回数据
    private List list;

    public static Result success(String message){
        return new Result(true,message);
    }

    public static Result success(String message,Object data){
        return new Result(true,message,data);
    }

    public static Result error(String message){
        return new Result(false,message);
    }

    public Result(boolean flag, String message) {
        super();
        this.flag = flag;
        this.message = message;
    }

    public Result(boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    public Result(List<T> list) {
        this.list = list;
    }

    public boolean isFlag() {
        return flag;
    }
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public List getList() {
        return list;
    }
    public void setList(List list) {
        this.list = list;
    }
}
