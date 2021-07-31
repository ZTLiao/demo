package com.ztliao.spider.service;

import com.ztliao.spider.domain.entity.Inventory;
import com.ztliao.spider.domain.enums.TexasInstruments;

import java.util.List;

/**
 * @author: liaozetao
 * @date: 2021/7/31 3:17 PM
 * @description:
 */
public interface TexasService {

    void newProduct();

    void qgpNs(TexasInstruments instruments);

    void inventory(String qgpn);

    List<Inventory> search(List<String> partNumbers);

    void searchJob();
}
