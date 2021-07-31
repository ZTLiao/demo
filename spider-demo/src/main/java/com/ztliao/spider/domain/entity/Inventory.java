package com.ztliao.spider.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: liaozetao
 * @date: 2021/7/31 6:32 PM
 * @description:
 */
@TableName("inventory")
@Data
public class Inventory {

    @TableId(type = IdType.UUID)
    private String id;

    private String qgpn;

    private String partNumber;

    private Integer quantity;
}
