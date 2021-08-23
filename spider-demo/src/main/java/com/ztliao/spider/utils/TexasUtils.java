package com.ztliao.spider.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ztliao.spider.domain.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: liaozetao
 * @date: 2021/7/31 12:42 PM
 * @description:
 */
@Slf4j
public class TexasUtils {

    /**
     * 查询新产品
     *
     * @return
     */
    public static RssDto scNewProducts() {
        RssDto rssDto = BeanUtil.mapToBean(XmlUtil.xmlToMap(HttpUtil.get("https://www.ti.com.cn/rss/cn/sc_new_products.xml")), RssDto.class, true);
        assert rssDto != null;
        return rssDto;
    }

    public static List<String> getQGPNs(Integer familyId) {
        assert familyId != null;
        ParametricResultsDto parametricResultsDto = JSONUtil.toBean(HttpUtil.get("https://www.ti.com.cn/selectiontool/paramdata/family/" + familyId + "/results?lang=cn&output=json"), ParametricResultsDto.class);
        assert parametricResultsDto != null;
        List<ParametricResultDto> parametricResults = parametricResultsDto.getParametricResults();
        assert parametricResults != null;
        return parametricResults.stream().map(ParametricResultDto::getO1).collect(Collectors.toList());
    }

    public static List<String> getPartNumbers(String qgpN) {
        assert StrUtil.isNotEmpty(qgpN);
        List<String> partNumbers = new ArrayList<>();
        String html = HttpUtil.get("https://www.ti.com.cn/product/cn/" + qgpN);
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByAttributeValue("name", "PartNumber");
        if (!elements.isEmpty()) {
            Element element = elements.get(0);
            String content = element.attr("content");
            if (StrUtil.isNotEmpty(content)) {
                String[] partNumberArray = content.split(StrUtil.COMMA);
                for (int i = 0; i < partNumberArray.length; i++) {
                    if (i != 0) {
                        partNumbers.add(partNumberArray[i]);
                    }
                }
            }
        }
        return partNumbers;
    }

    public static OpnListDto getInventory(List<String> qgpns) {
        assert qgpns != null && !qgpns.isEmpty();
        OpnListDto opnListDto = JSONUtil.toBean(HttpUtil.post("https://www.ti.com.cn/storeservices/cart/inventory", JSONUtil.toJsonStr(qgpns)), OpnListDto.class);
        assert opnListDto != null;
        return opnListDto;
    }

    public static InventoryDto opnInventory(String opn) {
        assert StringUtils.isNotEmpty(opn);
        InventoryDto inventoryDto = JSONUtil.toBean(HttpUtil.get("https://www.ti.com/storeservices/cart/opninventory?opn=" + opn), InventoryDto.class);
        assert inventoryDto != null;
        return inventoryDto;
    }
}
