package com.phoenix.huashi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.phoenix.huashi.mapper")
@ComponentScan(basePackages = {"com.phoenix"})
public class HuaShiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HuaShiApplication.class, args);
    }

}
