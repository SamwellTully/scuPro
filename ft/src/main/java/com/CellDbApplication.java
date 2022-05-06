package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan()
public class CellDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(CellDbApplication.class, args);
    }

}
