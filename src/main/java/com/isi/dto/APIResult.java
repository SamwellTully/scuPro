package com.isi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhulei
 * @create 2022-05-05 21-35
 * @description API传输对象
 */
@Data
@NoArgsConstructor
public class APIResult implements Serializable {
    private String msg;
    private Integer code;//200 20000 50000
    private Object data;
    private Boolean success;

    public static APIResult succ(String msg, Object data) {
        return new APIResult(msg, 200, data, true);
    }

    public static APIResult fail(String msg, Object data) {
        return new APIResult(msg, 500, data, false);
    }

    public APIResult(String msg, Integer code, Object data, Boolean success) {
        this.msg = msg;
        this.code = code;
        this.data = data;
        this.success = success;
    }

    @Override
    public String toString() {
        return "Result{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                ", success=" + success +
                '}';
    }

}
