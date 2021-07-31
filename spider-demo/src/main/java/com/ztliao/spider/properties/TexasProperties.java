package com.ztliao.spider.properties;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: liaozetao
 * @date: 2021/7/31 8:09 PM
 * @description:
 */
@Data
@AllArgsConstructor
public class TexasProperties {

    private String host;

    private String username;

    private String password;

    private String partNumbers;

    @Override
    public String toString() {
        return "TexasProperties{" +
                "host='" + host + '\'' +
                ", username='" + username + '\'' +
                ", partNumbers='" + partNumbers + '\'' +
                '}';
    }
}
