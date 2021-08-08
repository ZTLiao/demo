package com.ztliao.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: liaozetao
 * @date: 2021/8/5 2:17 PM
 * @description:
 */
@Slf4j
public class SeleniumTest {


    @Test
    public void create() throws MalformedURLException {
        String testName = "testName";
        String hubUrl = "https://www.ti.com.cn/zh-cn/ordering-resources/buying-tools/quick-add-to-cart.html";
        log.debug("create() - create chrome web driver");
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("disable-infobars");
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
        chromeOptions.setCapability("screenResolution", "1920x1080");
        chromeOptions.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
        chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        final Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("download_prompt_for_download", false);
        chromeOptions.setExperimentalOption("prefs", prefs);
        final LoggingPreferences loggingPreferences = new LoggingPreferences();
        chromeOptions.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);
        chromeOptions.setCapability("name", testName);
        chromeOptions.setCapability("tz", "America/Montreal");
        log.info("create() - hub url is {}", hubUrl);
        final RemoteWebDriver remoteWebDriver = new RemoteWebDriver(new URL(hubUrl), chromeOptions);
        remoteWebDriver.get(hubUrl);
    }

}
