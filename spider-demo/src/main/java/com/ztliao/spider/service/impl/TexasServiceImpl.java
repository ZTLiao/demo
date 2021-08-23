package com.ztliao.spider.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ztliao.spider.domain.dto.*;
import com.ztliao.spider.domain.entity.*;
import com.ztliao.spider.domain.enums.TexasInstruments;
import com.ztliao.spider.mapper.*;
import com.ztliao.spider.properties.TexasProperties;
import com.ztliao.spider.service.TexasService;
import com.ztliao.spider.utils.ApplicationContextUtils;
import com.ztliao.spider.utils.SendMailUtils;
import com.ztliao.spider.utils.TexasUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @author: liaozetao
 * @date: 2021/7/31 3:17 PM
 * @description:
 */
@Slf4j
@Service
public class TexasServiceImpl implements TexasService {

    @Resource
    private ChannelMapper channelMapper;

    @Resource
    private ItemMapper itemMapper;

    @Resource
    private GuidMapper guidMapper;

    @Resource
    private QgpnMapper partNumberMapper;

    @Resource
    private InventoryMapper inventoryMapper;

    @Resource
    private TaskMapper taskMapper;

    @Override
    public void newProduct() {
        try {
            RssDto rssDto = TexasUtils.scNewProducts();
            assert rssDto != null;
            ChannelDto channelDto = rssDto.getChannel();
            assert channelDto != null;
            String lastBuildDateStr = channelDto.getLastBuildDate();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM yyyy :mm:ss Z (z Z)", Locale.US);
            assert StrUtil.isNotEmpty(lastBuildDateStr);
            LocalDateTime lastBuildDate = simpleDateFormat.parse(lastBuildDateStr).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            List<Channel> channels = channelMapper.selectList(Wrappers.emptyWrapper());
            if (channels != null && !channels.isEmpty()) {
                LocalDateTime oldLastBuildDate = channels.stream().map(channel -> {
                    try {
                        return simpleDateFormat.parse(channel.getLastBuildDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    } catch (ParseException e) {
                        log.error(e.getMessage(), e);
                    }
                    return null;
                }).filter(Objects::nonNull).reduce((v1, v2) -> v1.isAfter(v2) ? v1 : v2).orElseGet(null);
                if (lastBuildDate.isEqual(oldLastBuildDate)) {
                    log.info("====未发布新产品====");
                    return;
                }
            }
            channelMapper.insert(channelDto);
            List<ItemDto> item = channelDto.getItem();
            assert item != null;
            for (ItemDto itemDto : item) {
                itemDto.setChannelId(channelDto.getId());
                itemMapper.insert(itemDto);
                List<String> guidList = itemDto.getGuid();
                if (guidList != null) {
                    for (String guidStr : guidList) {
                        Guid guid = new Guid();
                        guid.setItemId(itemDto.getId());
                        guid.setGuid(guidStr);
                        guidMapper.insert(guid);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void qgpNs(TexasInstruments instruments) {
        try {
            Integer familyId = instruments.getFamilyId();
            log.info("====获取产品分类====");
            List<String> qgpNs = TexasUtils.getQGPNs(familyId);
            assert qgpNs != null && !qgpNs.isEmpty();
            System.out.println(qgpNs);
            for (String qgpNStr : qgpNs) {
                Qgpn qgpn = new Qgpn();
                qgpn.setFamilyId(String.valueOf(familyId));
                qgpn.setQgpn(qgpNStr);
                partNumberMapper.insert(qgpn);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void inventory(String qgpn) {
        try {
            List<String> partNumbers = TexasUtils.getPartNumbers(qgpn);
            OpnListDto opnListDto = TexasUtils.getInventory(partNumbers);
            assert opnListDto != null;
            List<InventoryDto> opnList = opnListDto.getOpn_list();
            assert opnList != null && !opnList.isEmpty();
            for (InventoryDto inventoryDto : opnList) {
                Inventory inventory = new Inventory();
                inventory.setPartNumber(inventoryDto.getOrderable_number());
                inventory.setQgpn(qgpn);
                inventory.setQuantity(inventoryDto.getQuantity());
                inventoryMapper.insert(inventory);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public List<Inventory> search(List<String> partNumbers) {
        List<Inventory> inventories = new ArrayList<>();
        try {
            for (int i = 0, len = partNumbers.size(); i < len; i++) {
                String opn = partNumbers.get(i);
                InventoryDto inventoryDto = TexasUtils.opnInventory(opn);
                Inventory inventory = new Inventory();
                inventory.setPartNumber(inventoryDto.getOrderable_number());
                inventory.setQuantity(inventoryDto.getInventory());
                inventoryMapper.insert(inventory);
                inventories.add(inventory);
                log.info("开始等候10秒...");
                Thread.sleep(10 * 1000);
                log.info("结束等候10秒...");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return inventories;
    }

    @Override
    public void searchJob() {
        try {
            List<String> partNumberList = new ArrayList<>();
            TexasProperties texasProperties = ApplicationContextUtils.getApplicationContext().getBean(TexasProperties.class);
            log.info("texasProperties : {}", texasProperties);
            String partNumbers = texasProperties.getPartNumbers();
            assert StrUtil.isNotEmpty(partNumbers) && partNumbers.contains(StrUtil.COMMA);
            Integer count = taskMapper.selectCount(Wrappers.<Task>lambdaQuery()
                    .eq(Task::getContent, partNumbers)
                    .eq(Task::getFlag, "Y"));
            if (count > 0) {
                log.info("任务已执行成功,请重新配置型号.");
                return;
            }
            Task task = new Task();
            task.setContent(partNumbers);
            List<Task> tasks = taskMapper.selectList(Wrappers.<Task>lambdaQuery()
                    .eq(Task::getContent, partNumbers));
            if (tasks != null && !tasks.isEmpty()) {
                task.setId(tasks.get(0).getId());
                task.setFlag(tasks.get(0).getFlag());
                String lackStockStr = tasks.get(0).getLackStocks();
                if (StrUtil.isNotEmpty(lackStockStr)) {
                    partNumberList.addAll(Arrays.asList(lackStockStr.split(StrUtil.COMMA)));
                }
            } else {
                taskMapper.insert(task);
                partNumberList.addAll(Arrays.asList(partNumbers.split(StrUtil.COMMA)));
            }
            List<Inventory> inventories = search(partNumberList);
            log.info("inventories : {}", inventories);
            assert !inventories.isEmpty();
            List<String> lackStocks = new ArrayList<>();
            StringBuilder content = new StringBuilder();
            for (Inventory inventory : inventories) {
                Integer quantity = inventory.getQuantity();
                if (quantity <= 0) {
                    lackStocks.add(inventory.getPartNumber());
                    continue;
                }
                content.append("型号").append(StrUtil.SPACE).append(StrUtil.COLON).append(StrUtil.SPACE).append(inventory.getPartNumber()).append(StringPool.CRLF);
                content.append("库存").append(StrUtil.SPACE).append(StrUtil.COLON).append(StrUtil.SPACE).append(inventory.getQuantity()).append("件").append(StringPool.CRLF);
            }
            if (content.length() > 0) {
                SendMailUtils.sendText(texasProperties.getUsername(), "TEXAS产品库存通知", content.toString(), () -> {
                    log.info("content : {}", content);
                    if (lackStocks.isEmpty()) {
                        task.setFlag("Y");
                    }
                    return Boolean.TRUE;
                }, null);
            }
            if (!lackStocks.isEmpty()) {
                String lackStocksStr = CollectionUtil.join(lackStocks, StrUtil.COMMA);
                task.setLackStocks(lackStocksStr);
                log.info("型号 : {} 库存不足.", lackStocksStr);
            }
            taskMapper.updateById(task);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
