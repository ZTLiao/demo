package com.ztliao.spider.task;

import com.ztliao.spider.service.TexasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: liaozetao
 * @date: 2021/7/29 1:11 PM
 * @description:
 */
@Slf4j
@Component
public class TexasTask {

    @Autowired
    private TexasService texasService;

    @Scheduled(cron = "0/10 * * * * ?")
    public void execute() {
        log.info("====开始TEXAS任务====");
        texasService.searchJob();
        log.info("====结束TEXAS任务====");
    }
}
