package com.ztliao.spider.test;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ztliao.spider.domain.dto.RssDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author: liaozetao
 * @date: 2021/7/29 1:16 PM
 * @description:
 */
public class TestMain {

    @Test
    public void test1() {
        System.out.println(HttpUtil.get("https://www.ti.com/general/docs/readAkamaiHeaders0.tsp"));

    }

    @Test
    public void test2() {
        System.out.println(HttpUtil.get("https://www.ti.com.cn/search/coveo/newToken"));
    }

    @Test
    public void test3() {
        System.out.println(HttpUtil.get("https://www.ti.com.cn/productmodel/OPA333/currencyExchangeRate"));
    }

    @Test
    public void test4() {
        System.out.println(HttpUtil.get("https://www.ti.com/81Wfn4droGvoFmKb0jWO8Mg56zk/iuOcpXhwi9/EyJvR24z/Bn4mY/18YfWc"));
    }

    @Test
    public void test5() {
        System.out.println(BeanUtil.mapToBean(XmlUtil.xmlToMap(HttpUtil.get("https://www.ti.com.cn/rss/cn/sc_new_products.xml")), RssDto.class, true));
    }

    @Test
    public void test6() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm:ss Z (z Z)", Locale.US);
        System.out.println(LocalDateTime.parse("Fri, 25 Jun 2021 00:00:00 -0500 (GMT -0500)", dateTimeFormatter));
    }

    @Test
    public void test7() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM yyyy :mm:ss Z (z Z)", Locale.US);
        System.out.println(simpleDateFormat.parse("Tue, 29 Jun 2021 :10:01 -0500 (GMT -0500)"));
    }

    @Test
    public void test8() {
        System.out.println(HttpUtil.get("https://www.ti.com.cn/foldercomponents/docs/comp/restResponse.tsp?uiTemplateId=REST_RST_T&param=familyId~1"));
    }

    @Test
    public void test9() {
        //Integer[] familyIds = {1, 2, 4, 44, 57, 64, 72, 78, 82, 100, 105};
        List<Integer> familyIds = new ArrayList<>();
        for (int i = 100; i < 200; i++) {
            String result = HttpUtil.get("https://www.ti.com.cn/selectiontool/paramdata/family/" + i + "/criteria?lang=cn&output=json");
            JSONObject jsonObject = JSONUtil.parseObj(result);
            //System.out.println(jsonObject.toString());
            if (!result.contains("errorCode")) {
                System.out.println(jsonObject.toString());
                familyIds.add(i);
                System.out.println(i);
            }
        }
        System.out.println(familyIds);
    }

    @Test
    public void test10() {
        System.out.println(HttpUtil.get("https://www.ti.com.cn/selectiontool/paramdata/family/2004/results?lang=cn&output=json"));
    }

    @Test
    public void test11() {
        System.out.println(HttpUtil.get("https://product-odt-panel.ti.com/panels/AM6526"));
    }

    @Test
    public void test12() {
        String html = HttpUtil.get("https://www.ti.com.cn/product/cn/AM2431");
        System.out.println(html);
        List<String> partNumbers = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByAttributeValue("name", "PartNumber");
        if (!elements.isEmpty()) {
            Element element = elements.get(0);
            String content = element.attr("content");
            if (StrUtil.isNotEmpty(content)) {
                String[] partNumberArray = content.split(StrUtil.COMMA);
                for (int i = 0; i < partNumberArray.length; i++) {
                    String partNumber = partNumberArray[i];
                    if (i != 0) {
                        partNumbers.add(partNumber);
                    }
                }
            }
        }
        if(!partNumbers.isEmpty()){
            System.out.println(HttpUtil.post("https://www.ti.com.cn/storeservices/cart/inventory", JSONUtil.toJsonStr(partNumbers)));
        }
    }

}
