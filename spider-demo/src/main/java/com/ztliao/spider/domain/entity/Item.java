package com.ztliao.spider.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: liaozetao
 * @date: 2021/7/31 1:59 PM
 * @description:
 */
@TableName("item")
@Data
public class Item {

    @TableId(type = IdType.UUID)
    private String id;

    private String channelId;

    private String title;

    private String link;

    private String pubDate;

    private String description;
}
