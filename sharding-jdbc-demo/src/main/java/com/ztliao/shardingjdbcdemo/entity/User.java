package com.ztliao.shardingjdbcdemo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: liaozetao
 * @date: 2021/3/12 9:42 PM
 * @description:
 */
@TableName("user")
@Data
public class User {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
