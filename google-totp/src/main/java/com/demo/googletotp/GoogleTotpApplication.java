package com.demo.googletotp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value="com.demo")
@SpringBootApplication
public class GoogleTotpApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoogleTotpApplication.class, args);
    }

}
