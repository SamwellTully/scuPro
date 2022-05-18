package com.isi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.isi.Mapper")
public class IsiApplication {

    public static void main(String[] args) {
        SpringApplication.run(IsiApplication.class, args);
    }

}
