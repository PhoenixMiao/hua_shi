package com.phoenix.kuangjia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.phoenix.kuangjia.mapper")
@ComponentScan(basePackages = {"com.phoenix"})
public class KuangJiaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KuangJiaApplication.class, args);
    }

}
