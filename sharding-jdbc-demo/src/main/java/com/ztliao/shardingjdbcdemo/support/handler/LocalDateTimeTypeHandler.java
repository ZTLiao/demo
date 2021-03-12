package com.ztliao.shardingjdbcdemo.support.handler;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class LocalDateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN);

    private final DateTimeFormatter dateTimeMsFormatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_MS_PATTERN);

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType)
            throws SQLException {
        log.info("1. register LocalDateTimeTypeHandler ");
        if (parameter != null) {
            ps.setString(i, dateTimeFormatter.format(parameter));
        }
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        log.info("2. register LocalDateTimeTypeHandler ");
        String target = rs.getString(columnName);
        if (StrUtil.isEmpty(target)) {
            return null;
        }
        if (target.contains(StrUtil.DOT)) {
            return LocalDateTime.parse(target, dateTimeMsFormatter);
        }
        return LocalDateTime.parse(target, dateTimeFormatter);
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        log.info("3. register LocalDateTimeTypeHandler ");
        String target = rs.getString(columnIndex);
        if (StrUtil.isEmpty(target)) {
            return null;
        }
        if (target.contains(StrUtil.DOT)) {
            return LocalDateTime.parse(target, dateTimeMsFormatter);
        }
        return LocalDateTime.parse(target, dateTimeFormatter);
    }

    @Override
    public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        log.info("4. register LocalDateTimeTypeHandler ");
        String target = cs.getString(columnIndex);
        if (StrUtil.isEmpty(target)) {
            return null;
        }
        if (target.contains(StrUtil.DOT)) {
            return LocalDateTime.parse(target, dateTimeMsFormatter);
        }
        return LocalDateTime.parse(target, dateTimeFormatter);
    }
}
