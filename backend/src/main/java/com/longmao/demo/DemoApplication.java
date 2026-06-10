package com.longmao.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cache.annotation.EnableCaching;

/**
 * 校园图书管理系统启动类
 * 开启组件扫描、Mapper扫描及缓存支持
 */
@SpringBootApplication
@MapperScan("com.longmao.demo.mapper")
@EnableCaching // 开启 Spring Cache 支持
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
