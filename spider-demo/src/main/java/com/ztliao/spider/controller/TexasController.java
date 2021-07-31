package com.ztliao.spider.controller;

import com.ztliao.spider.domain.entity.Inventory;
import com.ztliao.spider.domain.vo.TexasSearchQuery;
import com.ztliao.spider.service.TexasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: liaozetao
 * @date: 2021/7/31 7:22 PM
 * @description:
 */
@Slf4j
@RequestMapping("/texas")
@RestController
public class TexasController {

    @Autowired
    private TexasService texasService;

    @PostMapping("/search")
    public ResponseEntity<List<Inventory>> search(@RequestBody TexasSearchQuery query) {
        return ResponseEntity.ok(texasService.search(query.getPartNumbers()));
    }

}
