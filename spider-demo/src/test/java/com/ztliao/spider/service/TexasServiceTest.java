package com.ztliao.spider.service;

import com.ztliao.spider.domain.enums.TexasInstruments;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void qgpNs(){
        texasService.qgpNs(TexasInstruments.MICROCONTROLLERS_MCUS_PROCESSORS);
    }

    @Test
    public void inventory(){
        texasService.inventory("AM6526");
    }
}
