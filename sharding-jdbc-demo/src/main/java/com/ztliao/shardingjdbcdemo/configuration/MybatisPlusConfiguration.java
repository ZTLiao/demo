package com.ztliao.shardingjdbcdemo.configuration;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.ztliao.shardingjdbcdemo.support.handler.LocalDateTimeTypeHandler;
import com.ztliao.shardingjdbcdemo.support.handler.LocalDateTypeHandler;
import com.ztliao.shardingjdbcdemo.support.handler.LocalTimeTypeHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author: liaozetao
 * @date: 2021/3/12 9:42 PM
 * @description:
 */
@Slf4j
@Configuration
public class MybatisPlusConfiguration {

    public MybatisPlusConfiguration(MybatisPlusProperties mybatisPlusProperties) {
        log.info(" mybatisPlusProperties : {}", mybatisPlusProperties);
        MybatisConfiguration configuration = mybatisPlusProperties.getConfiguration();
        GlobalConfig globalConfig = mybatisPlusProperties.getGlobalConfig();
        GlobalConfigUtils.setGlobalConfig(configuration, globalConfig);
        configuration.getTypeHandlerRegistry().register(LocalDateTime.class, new LocalDateTimeTypeHandler());
        configuration.getTypeHandlerRegistry().register(LocalDate.class, new LocalDateTypeHandler());
        configuration.getTypeHandlerRegistry().register(LocalTime.class, new LocalTimeTypeHandler());
        GlobalConfigUtils.setGlobalConfig(configuration, globalConfig);
    }

}