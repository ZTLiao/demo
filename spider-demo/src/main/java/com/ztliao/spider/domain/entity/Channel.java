package com.ztliao.spider.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: liaozetao
 * @date: 2021/7/31 12:51 PM
 * @description:
 */
@TableName("channel")
@Data
public class Channel {

    @TableId(type = IdType.UUID)
    private String id;

    private String title;

    private String link;

    private String description;

    private String language;

    private String lastBuildDate;

}
