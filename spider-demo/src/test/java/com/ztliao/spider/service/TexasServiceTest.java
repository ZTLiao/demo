package com.ztliao.spider.service;

import cn.hutool.http.HttpGlobalConfig;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.ztliao.spider.domain.enums.TexasInstruments;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

/**
 * @author: liaozetao
 * @date: 2021/7/31 6:18 PM
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TexasServiceTest {

    @Autowired
    private TexasService texasService;

    @Test
    public void qgpNs() {
        texasService.qgpNs(TexasInstruments.MICROCONTROLLERS_MCUS_PROCESSORS);
    }

    @Test
    public void inventory() {
        texasService.inventory("AM6526");
    }

    @Test
    public void search() {
        System.out.println(HttpRequest.post("https://www.ti.com.cn/storeservices/cart/inventory")
                .header("Set-Cookie", "ti_geo=country=CN|city=GUANGZHOU|continent=AS|tc_ip=121.32.88.181; path=/; domain=.ti.com.cn; secure")
                .timeout(HttpGlobalConfig.getTimeout())
                .body(JSONUtil.toJsonStr("AM6526BACDXEAF"))
                .execute()
                .body());
    }
}
