package com.ztliao.spider.domain.dto;

import lombok.Data;

/**
 * @author: liaozetao
 * @date: 2021/7/31 6:09 PM
 * @description:
 */
@Data
public class InventoryDto {

    private String orderable_number;

    private Integer quantity;

    private String purchase_flag;
}
