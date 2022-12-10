package com.kang.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan( basePackages = "com.kang.blog.mapper")
public class BlogAPP {
    public static void main(String[] args) {
        SpringApplication.run(BlogAPP.class,args);
    }
}
