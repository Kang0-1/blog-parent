package com.kang.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan( basePackages = "com.kang.blog.mapper")
@ComponentScan( {"com.kang.blog"})
public class BlogAPP {
    public static void main(String[] args) {
        SpringApplication.run(BlogAPP.class,args);
    }
}
