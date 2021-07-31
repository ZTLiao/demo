package com.ztliao.spider.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author: liaozetao
 * @date: 2021/7/31 1:53 PM
 * @description:
 */
@Configuration
public class SpiderDemoConfiguration {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        jdbcTemplate.execute("drop table if exists channel;");
        jdbcTemplate.execute("drop table if exists item;");
        jdbcTemplate.execute("drop table if exists guid;");
        jdbcTemplate.execute("drop table if exists qgpn;");
        jdbcTemplate.execute("drop table if exists inventory;");
        jdbcTemplate.execute("drop table if exists task;");
        jdbcTemplate.execute("create table channel(id text, title text, link text, description text, language text, last_build_date text);");
        jdbcTemplate.execute("create table item(id text, channel_id text, title text, link text, pub_date text, description text);");
        jdbcTemplate.execute("create table guid(id text, item_id text, guid text);");
        jdbcTemplate.execute("create table qgpn(id text, family_id text, qgpn text);");
        jdbcTemplate.execute("create table inventory(id text, qgpn text, part_number text, quantity text);");
        jdbcTemplate.execute("create table task(id text, content text, flag text, lack_stocks text);");
    }
}
