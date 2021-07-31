package com.ztliao.spider.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: liaozetao
 * @date: 2021/7/31 6:23 PM
 * @description:
 */
@TableName("qgpn")
@Data
public class Qgpn {

    @TableId(type = IdType.UUID)
    private String id;

    private String familyId;

    private String qgpn;
}
