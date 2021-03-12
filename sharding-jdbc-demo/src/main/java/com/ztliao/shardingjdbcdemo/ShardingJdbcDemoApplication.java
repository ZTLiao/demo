package com.ztliao.shardingjdbcdemo;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: liaozetao
 * @date: 2021/3/12 9:21 PM
 * @description:
 */
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class})
@MapperScan("com.ztliao.shardingjdbcdemo.mapper")
public class ShardingJdbcDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcDemoApplication.class, args);
    }
}
