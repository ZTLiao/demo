package com.ztliao.spider.domain.dto;

import com.ztliao.spider.domain.entity.Item;
import lombok.Data;

import java.util.List;

/**
 * @author: liaozetao
 * @date: 2021/7/30 1:15 PM
 * @description:
 */
@Data
public class ItemDto extends Item {

    private List<String> guid;
}
