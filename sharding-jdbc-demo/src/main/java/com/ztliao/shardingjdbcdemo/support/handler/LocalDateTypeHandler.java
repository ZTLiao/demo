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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class LocalDateTypeHandler extends BaseTypeHandler<LocalDate> {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN);

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDate parameter, JdbcType jdbcType)
            throws SQLException {
        log.info("1. register LocalDateTypeHandler ");
        if (parameter != null) {
            ps.setString(i, dateTimeFormatter.format(parameter));
        }
    }

    @Override
    public LocalDate getNullableResult(ResultSet rs, String columnName) throws SQLException {
        log.info("2. register LocalDateTypeHandler ");
        String target = rs.getString(columnName);
        if (StrUtil.isEmpty(target)) {
            return null;
        } else {
            return LocalDate.parse(target, dateTimeFormatter);
        }
    }

    @Override
    public LocalDate getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        log.info("3. register LocalDateTypeHandler ");
        String target = rs.getString(columnIndex);
        if (StrUtil.isEmpty(target)) {
            return null;
        }
        return LocalDate.parse(target, dateTimeFormatter);
    }

    @Override
    public LocalDate getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        log.info("4. register LocalDateTypeHandler ");
        String target = cs.getString(columnIndex);
        if (StrUtil.isEmpty(target)) {
            return null;
        }
        return LocalDate.parse(target, dateTimeFormatter);
    }
}