package com.ztliao.spider.mapper;

import com.ztliao.spider.domain.dto.ChannelDto;
import com.ztliao.spider.domain.dto.ItemDto;
import com.ztliao.spider.domain.dto.RssDto;
import com.ztliao.spider.domain.entity.Guid;
import com.ztliao.spider.utils.TexasUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: liaozetao
 * @date: 2021/7/31 1:00 PM
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ChannelMapperTest {

    @Resource
    private ChannelMapper channelMapper;

    @Resource
    private ItemMapper itemMapper;

    @Resource
    private GuidMapper guidMapper;

    @Test
    public void insertChannel(){
        RssDto rssDto = TexasUtils.scNewProducts();
        assert rssDto != null;
        ChannelDto channelDto = rssDto.getChannel();
        System.out.println(" channel : " + channelDto);
        assert channelDto != null;
        channelMapper.insert(channelDto);
        List<ItemDto> item = channelDto.getItem();
        assert item != null;
        for (ItemDto itemDto : item) {
            itemDto.setChannelId(channelDto.getId());
            itemMapper.insert(itemDto);
            List<String> guidList = itemDto.getGuid();
            if(guidList != null){
                for (String guidStr : guidList) {
                    Guid guid = new Guid();
                    guid.setItemId(itemDto.getId());
                    guid.setGuid(guidStr);
                    guidMapper.insert(guid);
                }
            }
        }
    }

}
