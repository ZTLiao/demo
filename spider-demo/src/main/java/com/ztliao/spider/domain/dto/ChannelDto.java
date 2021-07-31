package com.ztliao.spider.domain.dto;

import com.ztliao.spider.domain.entity.Channel;
import lombok.Data;

import java.util.List;

/**
 * @author: liaozetao
 * @date: 2021/7/30 1:10 PM
 * @description:
 */
@Data
public class ChannelDto extends Channel {

    private List<ItemDto> item;
}
