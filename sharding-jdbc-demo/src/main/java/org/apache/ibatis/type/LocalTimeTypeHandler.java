package org.apache.ibatis.type;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class LocalTimeTypeHandler extends BaseTypeHandler<LocalTime> {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN);

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalTime parameter, JdbcType jdbcType) throws SQLException {
        log.info("1. override LocalTimeTypeHandler ");
        if (parameter != null) {
            ps.setString(i, dateTimeFormatter.format(parameter));
        }
    }

    @Override
    public LocalTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        log.info("2. override LocalTimeTypeHandler ");
        String target = rs.getString(columnName);
        if (StrUtil.isEmpty(target)) {
            return null;
        } else {
            return LocalTime.parse(target, dateTimeFormatter);
        }
    }

    @Override
    public LocalTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        log.info("3. override LocalTimeTypeHandler ");
        String target = rs.getString(columnIndex);
        if (StrUtil.isEmpty(target)) {
            return null;
        }
        return LocalTime.parse(target, dateTimeFormatter);
    }

    @Override
    public LocalTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        log.info("4. override LocalTimeTypeHandler ");
        String target = cs.getString(columnIndex);
        if (StrUtil.isEmpty(target)) {
            return null;
        }
        return LocalTime.parse(target, dateTimeFormatter);
    }
}
