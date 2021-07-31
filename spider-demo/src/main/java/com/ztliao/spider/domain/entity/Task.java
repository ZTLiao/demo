package com.ztliao.spider.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: liaozetao
 * @date: 2021/7/31 8:32 PM
 * @description:
 */
@TableName("task")
@Data
public class Task {

    @TableId(type = IdType.UUID)
    private String id;

    private String content;

    private String flag = "N";

    private String lackStocks;
}
