package com.ztliao.spider;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.ztliao.spider.properties.TexasProperties;
import com.ztliao.spider.utils.ApplicationContextUtils;
import com.ztliao.spider.utils.SendMailUtils;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author: liaozetao
 * @date: 2021/7/29 1:05 PM
 * @description:
 */
@Slf4j
@SpringBootApplication
@EnableScheduling
@MapperScan("com.ztliao.spider.mapper")
public class SpiderDemoApplication {

    public static void main(String[] args) {
        ApplicationContextUtils.setApplicationContext(SpringApplication.run(SpiderDemoApplication.class, args));
        try {
            TexasProperties texasProperties = JSONUtil.toBean(FileUtil.readString(new File(System.getProperty("user.dir") + File.separator + args[0]), StandardCharsets.UTF_8), TexasProperties.class);
            ApplicationContextUtils.registerSingletonBean(TexasProperties.class.getSimpleName(), texasProperties);
            SendMailUtils.init(texasProperties.getHost(), texasProperties.getUsername(), texasProperties.getPassword());
        } catch (Exception e) {
            log.error("配置文件格式异常.");
            System.exit(-1);
        }
    }

}
