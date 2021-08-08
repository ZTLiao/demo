package com.ztliao.spider.test;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.HttpGlobalConfig;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ztliao.spider.domain.dto.RssDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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
        if (!partNumbers.isEmpty()) {
            System.out.println(HttpUtil.post("https://www.ti.com.cn/storeservices/cart/inventory", JSONUtil.toJsonStr(partNumbers)));
        }
    }

    @Test
    public void test13() {
        System.out.println(HttpRequest.post("https://www.ti.com.cn/storeservices/cart/inventory")
                .header("Cookie", "CONSENTMGR=ts:1627478114651%7Cconsent:true; tiSessionID=017aed4141600021533b2829f29c03079001607100942; user_pref_language=\"zh-CN\"; alias=homepageproduct; last-domain=www.ti.com.cn; ti_geo=country=CN|city=GUANGZHOU|continent=AS|tc_ip=121.32.88.181; ti_ua=Mozilla%2f5.0%20(Macintosh%3b%20Intel%20Mac%20OS%20X%2010_15_7)%20AppleWebKit%2f537.36%20(KHTML,%20like%20Gecko)%20Chrome%2f92.0.4515.131%20Safari%2f537.36; bm_sz=842D415249398821A254D07CAD2A90BA~YAAQzIvytskJoLZ6AQAAv4uTFAxtnXp1JM5YUjsgKbE4dC8Xe4a3YZLKlMWNpXGEmxIfNFVLb+aTAMHZwdTA0b8WlgZTL1ptKByuceBbRZ7AEXxqY8w23+RW54g24EbWZxFsieSHJd2uTfbVq7rfmMA+5xlE2QkrhFbpup5Kt4Scsfjpd1wXKlQfwOIcaijyATMNzUHm6MKUktwLXu5ss2hioKEc6oUrjf4Oc4XvaLUPql49FHS4GcMd7OOvw31N05nZ9gkC5se05IS+9oIxP1IyQYl1U2P+jQpd3Nn/AFQv+g==~3551286~3293753; user_pref_currency=\"USD\"; ga_ab=enabled; ticontent=%2Ftistore; ga_content_cookie=%2Ftistore; bm_mi=1561F8769BD04A4FEE2FBC03DBE30FAB~WHuXCaD7Lq/35HBm/MAYYIyKC79yr2cldrdk819RTN5jPu9OqtgHd0R6a0Ge6f2pFXSzv6fQpQ+ak0fwTODgaJH/frNgKhFVO6p4jjRUD07LnecVyO4YnX4nWn6NBqnAjQeA1lCRcWANWFK07AJZZQZw/5BiPO4N4fx5n1kW5uZ4Ci0zq2tX9mCawL2GnAyTv4nSW3m5OCwCSU0oiPqWKrzaWXJe4fPxv7OrI1rKr6E1lx6b7TD0AQfRnsa7IXZ3O+/z1s4R/STLrQ6q2GawQtPQCJ8TLksZynFPURq3YnB7QLI6k+Fbl7Q2XmekwdaW; tipage=%2Ftistore%2Fquick%20add%20to%20cart-en; tipageshort=quick%20add%20to%20cart-en; ga_page_cookie=quick%20add%20to%20cart-en; ABTasty=uid=0dyry1z8dvvt69ks&fst=1627478116668&pst=1627727912462&cst=1628137837691&ns=7&pvt=109&pvis=4&th=; ABTastySession=mrasn=&sen=3&lp=https%253A%252F%252Fwww.ti.com.cn%252F; utag_main=v_id:017aed4141600021533b2829f29c03079001607100942$_sn:7$_ss:0$_st:1628139692359$free_trial:false$_pn:4%3Bexp-session$ses_id:1628137835482%3Bexp-session; ti_bm=; bm_sv=A1C8567DE2EBB5E254326EFF5F24D2E9~kauEZfjynOL8l/fl/d0u2zuq9PVnJxtxq5LlbYnnyJ5O1ffLPWx/LtphY71SYrTcWWtoeoUcuxUH1uycfzDhuARk7T/tVNqGX0OUHEsRhRwu8EfG7qU8E+VXRKgqEUMS9sr1bN/XZLBlW7ORgI5n2Y1lTwPfLaSbJcfOKbynagw=; ak_bmsc=4B4180C5659823462E0DDC24187E3966~000000000000000000000000000000~YAAQZ8U8t5vf7gt7AQAARnuqFAzcJYrW9EsySK7RvVZHCy38AJySOI6JBkWMjW+MYp89vDQ4LG/hrmiRqWWQkClKh+w5AJ8JHyKf0krBpTHgPermyAxEXNYEDkvCgYN+cmmBJV/dqtxlHVZr1dE2VuyHNzi+PB12Ohk5PLnjtGkDiMjvYdvBgpPeYZ/fFAn3+CUuPQebnxuJADO350+mjHObLP4VK/mHCKbb27hlf1zyiipFnv+btmfaMlJD00vY0+V92BC0FxoOC5WP8fu8OBvUuReLUHK3Fb54nkX9Lrf/4/gkVgcaLCFOn8y891b3u4/YyjIUaho+kJlPG6AD6SgAZG+Ctwns6kJ4Al56eNGvaGkzHWH9P0u80H/MOK59c2qDGbA=; ti_rid=8345f75; _abck=0F7D23CECF69EFA6B2BBD5308877922B~0~YAAQZ8U8t1f37gt7AQAA2+GqFAYSeGlGbcaEFvy5Lls2Wapnb9oYWWBazmmZt62t520dNo4+dZ9G3XU+u0389v0zwD/VO0JLvZ0nVQX4EDT6un+ThXuT+ANIK1Bc6QuSRu2h6X6LTCcopubc0Gin5enKOZlzqQSvQZTQAkv/EvC5+stwU5mEURs9JnRkPm59YdJu2voEDfeZQqfuOyzWaUFsh75cpFIXM22ZnZNuDtAY8ItGVqysI7/J0knUTq8PJ8eNKhlavhJSqN9WNAtirrcpZtsB6zzojHFYnnOO2btT6m7B6LOmS+KQ5nGxCqXSUqfEK6Oh5D8v9PQ8JR4tfNM87c6filwVYVGFCGioJAJ/BezrMVIkFI3Nw4VvfWuAgezHdfDNZjVdXQgYsVBJT+/qbtYn25Q=~-1~||||-1~-1; sec_cpt=6FF6799664D08B346A330893FFA95B7F~2~YAAQZ8U8t1j37gt7AQAA2+GqFAXbHvl28eVSBRtNeEsNdNkUUwpMTvySIZjuHO6q1H8X7JJPsPnd/+u7rCHtgw9T9b46DW9xH4hKvCO8vj1Nmu8cJPy16otxr376rCHrOiBY/6XRLNBU9RSXNf7jDTzr0oqIWGHdXameLFKMRFw7D2Hlgx5B1LcnRFYB+QIjiU2ei/LZt8Ck90Hks+kZ2q6/EBNb4ZRt8PEribL8O71G9vmfjVMhH1FvuA03UHGsMxXX/eW4AjSHaBGOxj7wlIZ3Ngw0QpD7gHdIed/ohjctskd7HACEHLOF4123asBmxeHMpnCzQwjWz81JxRx/rp+oHr90fuDJ8zvvG0u8rEB9tay/KKnvQEgYMLR8PjwmQBNtQ/0ceseF/WkrMJk8J4+KMYzfb86FGHbktzE66DFnTRRDcQ5WP4bYVsta3yMRHYTF6jVyUZSTUIi7PLDkqSdRznviPYgDBfoOFe/Wmu9kRaQu4E6TrJ4SyqTKkC7H7Ma3DEW/h44DRcKC9/a5y5bWdAQmKI16Njc70yNfH/7wAXyOW0ezFSBqHzVV6CducWdFWCTPGdQ=")
                .header("sec-ch-ua", "\"Chromium\";v=\"92\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"92\"")
                .header("Referer", "https://www.ti.com.cn/zh-cn/ordering-resources/buying-tools/quick-add-to-cart.html")
                .header("Origin", "https://www.ti.com.cn")
                .header("Host", "www.ti.com.cn")
                .header("Expires", "0")
                .header("sec-ch-ua-mobile", "?0")
                .header("Sec-Fetch-Mode", "cors")
                .header("Sec-Fetch-Site", "same-origin")
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Safari/537.36")
                .header("X-Sec-Clge-Req-Type", "ajax")
                .timeout(HttpGlobalConfig.getTimeout())
                .body(JSONUtil.toJsonStr("AM6526BACDXEAF"))
                .execute()
                .body());
    }

    @Test
    public void test14() {
        System.out.println(HttpRequest.get("https://www.ti.com.cn/_sec/cp_challenge/verify")
                .header("Cookie", "CONSENTMGR=ts:1627478114651%7Cconsent:true; tiSessionID=017aed4141600021533b2829f29c03079001607100942; user_pref_language=\"zh-CN\"; alias=homepageproduct; last-domain=www.ti.com.cn; ti_geo=country=CN|city=GUANGZHOU|continent=AS|tc_ip=121.32.88.181; ti_ua=Mozilla%2f5.0%20(Macintosh%3b%20Intel%20Mac%20OS%20X%2010_15_7)%20AppleWebKit%2f537.36%20(KHTML,%20like%20Gecko)%20Chrome%2f92.0.4515.131%20Safari%2f537.36; bm_sz=842D415249398821A254D07CAD2A90BA~YAAQzIvytskJoLZ6AQAAv4uTFAxtnXp1JM5YUjsgKbE4dC8Xe4a3YZLKlMWNpXGEmxIfNFVLb+aTAMHZwdTA0b8WlgZTL1ptKByuceBbRZ7AEXxqY8w23+RW54g24EbWZxFsieSHJd2uTfbVq7rfmMA+5xlE2QkrhFbpup5Kt4Scsfjpd1wXKlQfwOIcaijyATMNzUHm6MKUktwLXu5ss2hioKEc6oUrjf4Oc4XvaLUPql49FHS4GcMd7OOvw31N05nZ9gkC5se05IS+9oIxP1IyQYl1U2P+jQpd3Nn/AFQv+g==~3551286~3293753; user_pref_currency=\"USD\"; ga_ab=enabled; ticontent=%2Ftistore; ga_content_cookie=%2Ftistore; bm_mi=1561F8769BD04A4FEE2FBC03DBE30FAB~WHuXCaD7Lq/35HBm/MAYYIyKC79yr2cldrdk819RTN5jPu9OqtgHd0R6a0Ge6f2pFXSzv6fQpQ+ak0fwTODgaJH/frNgKhFVO6p4jjRUD07LnecVyO4YnX4nWn6NBqnAjQeA1lCRcWANWFK07AJZZQZw/5BiPO4N4fx5n1kW5uZ4Ci0zq2tX9mCawL2GnAyTv4nSW3m5OCwCSU0oiPqWKrzaWXJe4fPxv7OrI1rKr6E1lx6b7TD0AQfRnsa7IXZ3O+/z1s4R/STLrQ6q2GawQtPQCJ8TLksZynFPURq3YnB7QLI6k+Fbl7Q2XmekwdaW; tipage=%2Ftistore%2Fquick%20add%20to%20cart-en; tipageshort=quick%20add%20to%20cart-en; ga_page_cookie=quick%20add%20to%20cart-en; ABTasty=uid=0dyry1z8dvvt69ks&fst=1627478116668&pst=1627727912462&cst=1628137837691&ns=7&pvt=109&pvis=4&th=; ABTastySession=mrasn=&sen=3&lp=https%253A%252F%252Fwww.ti.com.cn%252F; utag_main=v_id:017aed4141600021533b2829f29c03079001607100942$_sn:7$_ss:0$_st:1628139692359$free_trial:false$_pn:4%3Bexp-session$ses_id:1628137835482%3Bexp-session; ti_bm=; bm_sv=A1C8567DE2EBB5E254326EFF5F24D2E9~kauEZfjynOL8l/fl/d0u2zuq9PVnJxtxq5LlbYnnyJ5O1ffLPWx/LtphY71SYrTcWWtoeoUcuxUH1uycfzDhuARk7T/tVNqGX0OUHEsRhRwu8EfG7qU8E+VXRKgqEUMS9sr1bN/XZLBlW7ORgI5n2Y1lTwPfLaSbJcfOKbynagw=; ak_bmsc=4B4180C5659823462E0DDC24187E3966~000000000000000000000000000000~YAAQZ8U8t5vf7gt7AQAARnuqFAzcJYrW9EsySK7RvVZHCy38AJySOI6JBkWMjW+MYp89vDQ4LG/hrmiRqWWQkClKh+w5AJ8JHyKf0krBpTHgPermyAxEXNYEDkvCgYN+cmmBJV/dqtxlHVZr1dE2VuyHNzi+PB12Ohk5PLnjtGkDiMjvYdvBgpPeYZ/fFAn3+CUuPQebnxuJADO350+mjHObLP4VK/mHCKbb27hlf1zyiipFnv+btmfaMlJD00vY0+V92BC0FxoOC5WP8fu8OBvUuReLUHK3Fb54nkX9Lrf/4/gkVgcaLCFOn8y891b3u4/YyjIUaho+kJlPG6AD6SgAZG+Ctwns6kJ4Al56eNGvaGkzHWH9P0u80H/MOK59c2qDGbA=; ti_rid=8345f75; _abck=0F7D23CECF69EFA6B2BBD5308877922B~0~YAAQZ8U8t1f37gt7AQAA2+GqFAYSeGlGbcaEFvy5Lls2Wapnb9oYWWBazmmZt62t520dNo4+dZ9G3XU+u0389v0zwD/VO0JLvZ0nVQX4EDT6un+ThXuT+ANIK1Bc6QuSRu2h6X6LTCcopubc0Gin5enKOZlzqQSvQZTQAkv/EvC5+stwU5mEURs9JnRkPm59YdJu2voEDfeZQqfuOyzWaUFsh75cpFIXM22ZnZNuDtAY8ItGVqysI7/J0knUTq8PJ8eNKhlavhJSqN9WNAtirrcpZtsB6zzojHFYnnOO2btT6m7B6LOmS+KQ5nGxCqXSUqfEK6Oh5D8v9PQ8JR4tfNM87c6filwVYVGFCGioJAJ/BezrMVIkFI3Nw4VvfWuAgezHdfDNZjVdXQgYsVBJT+/qbtYn25Q=~-1~||||-1~-1; sec_cpt=6FF6799664D08B346A330893FFA95B7F~2~YAAQZ8U8t1j37gt7AQAA2+GqFAXbHvl28eVSBRtNeEsNdNkUUwpMTvySIZjuHO6q1H8X7JJPsPnd/+u7rCHtgw9T9b46DW9xH4hKvCO8vj1Nmu8cJPy16otxr376rCHrOiBY/6XRLNBU9RSXNf7jDTzr0oqIWGHdXameLFKMRFw7D2Hlgx5B1LcnRFYB+QIjiU2ei/LZt8Ck90Hks+kZ2q6/EBNb4ZRt8PEribL8O71G9vmfjVMhH1FvuA03UHGsMxXX/eW4AjSHaBGOxj7wlIZ3Ngw0QpD7gHdIed/ohjctskd7HACEHLOF4123asBmxeHMpnCzQwjWz81JxRx/rp+oHr90fuDJ8zvvG0u8rEB9tay/KKnvQEgYMLR8PjwmQBNtQ/0ceseF/WkrMJk8J4+KMYzfb86FGHbktzE66DFnTRRDcQ5WP4bYVsta3yMRHYTF6jVyUZSTUIi7PLDkqSdRznviPYgDBfoOFe/Wmu9kRaQu4E6TrJ4SyqTKkC7H7Ma3DEW/h44DRcKC9/a5y5bWdAQmKI16Njc70yNfH/7wAXyOW0ezFSBqHzVV6CducWdFWCTPGdQ=")
                .execute()
                .body());
        //System.out.println(HttpUtil.get("https://www.ti.com.cn/_sec/cp_challenge/verify"));
    }

    @Test
    public void test15() throws MalformedURLException {
        File file = new File("/Users/liaozetao/IdeaProjects/demo/spider-demo/src/main/resources/chromedriver");
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        //DesiredCapabilities.chrome();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBinary(file);
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.ti.com.cn/zh-cn/ordering-resources/buying-tools/quick-add-to-cart.html");
    }

}
