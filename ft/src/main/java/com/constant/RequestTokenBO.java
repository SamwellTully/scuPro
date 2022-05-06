package com.constant;

import lombok.Getter;

/**
 * @author fanteng
 * @date 2022/5/6 15:00
 * @description
 */
@Getter
public class RequestTokenBO {


    private Long requestUserId;

    @Override
    public String toString() {
        return "RequestTokenBO{" +
                "requestUserId=" + requestUserId +
                '}';
    }
}
