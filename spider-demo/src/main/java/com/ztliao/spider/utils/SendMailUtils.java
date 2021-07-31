package com.ztliao.spider.utils;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.URLDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/**
 * @author: liaozetao
 * @date: 2019/12/31 11:10 AM
 * @description:
 */
@Slf4j
public class SendMailUtils {

    private static String USERNAME;

    private static String PASSWORD;

    private static Session SESSION;

    public static void init(String host, String username, String password) {
        if (StrUtil.isEmpty(host) && StrUtil.isEmpty(username) && StrUtil.isEmpty(password)) {
            log.info("未配置邮箱.");
            return;
        }
        log.info("邮箱账号 : {}", username);
        USERNAME = username;
        PASSWORD = password;
        Properties properties = new Properties();
        properties.setProperty("mail.host", host);// 主机名
        properties.setProperty("mail.transport.protocol", "smtp");// 连接协议
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.port", "465");// 端口号
        properties.setProperty("mail.smtp.socketFactory.port", "465");// 端口号
        properties.setProperty("mail.smtp.ssl.enable", "true");// 设置是否使用ssl安全连接 ---一般都使用
        //properties.setProperty("mail.debug", "true");// 设置是否显示debug信息 true 会在控制台显示相关信息
        // 得到回话对象
        SESSION = Session.getInstance(properties);
    }


    /**
     * 文本消息
     *
     * @param receive
     * @param subject
     * @param text
     */
    public static void sendText(String receive, String subject, String text) {
        sendText(receive, subject, text, null, null);
    }

    /**
     * 文本消息
     *
     * @param receive
     * @param subject
     * @param text
     * @param success
     * @param error
     */
    public static void sendText(String receive, String subject, String text, BooleanSupplier success, Consumer<Throwable> error) {
        log.info("====开始发送====");
        try {
            // 获取邮件对象
            Message message = new MimeMessage(SESSION);
            // 设置发件人邮箱地址
            message.setFrom(new InternetAddress(USERNAME));
            // 设置收件人邮箱地址
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(receive));//一个收件人
            // 设置邮件标题
            message.setSubject(subject);
            // 设置邮件内容
            message.setText(text);
            // 得到邮差对象
            Transport transport = SESSION.getTransport();
            // 连接自己的邮箱账户
            transport.connect(USERNAME, PASSWORD);
            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            //失败执行
            if (error != null) {
                error.accept(e);
            }
            log.error(e.getMessage(), e);
        }
        //成功执行
        if (success != null) {
            success.getAsBoolean();
        }
        log.info("====发送成功====");
    }

    /**
     * 发送带附件(文件路径)
     *
     * @param receive
     * @param subject
     * @param text
     * @param filePaths
     */
    public static void sendAttachForPath(String receive, String subject, String text, Map<String, String> filePaths, BooleanSupplier success, Consumer<Throwable> error) {
        sendAttach(receive, subject, text, filePaths, success, error, null);
    }

    /**
     * 发送带附件(文件路径)
     *
     * @param receive
     * @param subject
     * @param text
     * @param bytes
     */
    public static void sendAttachForByte(String receive, String subject, String text, BooleanSupplier success, Consumer<Throwable> error, Map<String, byte[]> bytes) {
        sendAttach(receive, subject, text, null, success, error, bytes);
    }

    /**
     * 发送带附件
     *
     * @param receive
     * @param subject
     * @param text
     * @param filePaths
     * @param success
     * @param error
     * @param bytes
     */
    public static void sendAttach(String receive, String subject, String text, Map<String, String> filePaths, BooleanSupplier success, Consumer<Throwable> error, Map<String, byte[]> bytes) {
        log.info("====开始发送====");
        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(SESSION);
            // Set From: 头部头字段
            message.setFrom(new InternetAddress(USERNAME));
            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receive));
            // Set Subject: 主题文字
            message.setSubject(subject);
            // 创建消息部分
            BodyPart messageBodyPart = new MimeBodyPart();
            // 消息
            messageBodyPart.setText(text);
            // 创建多重消息
            Multipart multipart = new MimeMultipart();
            // 设置文本消息部分
            multipart.addBodyPart(messageBodyPart);
            if (filePaths != null && !filePaths.isEmpty()) {
                filePaths.keySet().forEach(fileName -> {
                    try {
                        // 附件部分
                        MimeBodyPart mimeBodyPart = new MimeBodyPart();
                        // 设置要发送附件的文件路径
                        DataSource source = new URLDataSource(new URL(filePaths.get(fileName)));
                        mimeBodyPart.setDataHandler(new DataHandler(source));
                        // 处理附件名称中文（附带文件路径）乱码问题
                        mimeBodyPart.setFileName(MimeUtility.encodeText(fileName));
                        multipart.addBodyPart(mimeBodyPart);
                    } catch (MessagingException | UnsupportedEncodingException | MalformedURLException e) {
                        //失败执行
                        if (error != null) {
                            error.accept(e);
                        }
                        log.error(e.getMessage(), e);
                    }
                });
            }
            if (bytes != null && !bytes.isEmpty()) {
                bytes.keySet().forEach(fileName -> {
                    try {
                        // 附件部分
                        MimeBodyPart mimeBodyPart = new MimeBodyPart();
                        // 设置要发送附件的文件路径
                        DataSource source = new ByteArrayDataSource(bytes.get(fileName), "application/vnd.ms-excel");
                        mimeBodyPart.setDataHandler(new DataHandler(source));
                        // 处理附件名称中文（附带文件路径）乱码问题
                        mimeBodyPart.setFileName(MimeUtility.encodeText(fileName));
                        multipart.addBodyPart(mimeBodyPart);
                    } catch (MessagingException | UnsupportedEncodingException e) {
                        //失败执行
                        if (error != null) {
                            error.accept(e);
                        }
                        log.error(e.getMessage(), e);
                    }
                });
            }
            // 发送完整消息
            message.setContent(multipart);
            // 得到邮差对象
            Transport transport = SESSION.getTransport();
            // 连接自己的邮箱账户
            transport.connect(USERNAME, PASSWORD);
            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            //失败执行
            if (error != null) {
                error.accept(e);
            }
            log.error(e.getMessage(), e);
        }
        //成功执行
        if (success != null) {
            success.getAsBoolean();
        }
        log.info("====发送成功====");
    }

}
