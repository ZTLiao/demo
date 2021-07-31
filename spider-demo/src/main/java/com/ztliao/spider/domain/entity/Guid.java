package com.ztliao.spider.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: liaozetao
 * @date: 2021/7/31 2:00 PM
 * @description:
 */
@TableName("guid")
@Data
public class Guid {

    @TableId(type = IdType.UUID)
    private String id;

    private String itemId;

    private String guid;
}
