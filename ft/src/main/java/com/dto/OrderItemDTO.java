package com.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fanteng
 * @date 2022/5/6 14:11
 * @description
 */
@Slf4j
@Data
public class OrderItemDTO {
    private String column;
    private boolean asc = true;
}
