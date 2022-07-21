package com.ztliao.conditions.enums;

import com.ztliao.conditions.ISqlSegment;

/**
 * @author: liaozetao
 * @date: 2022/3/22 2:28 PM
 * @description:
 */
public enum SqlKeyword implements ISqlSegment {

    AND("AND"),
    OR("OR"),
    NOT("NOT"),
    IN("IN"),
    NOT_IN("NOT IN"),
    LIKE("LIKE"),
    LEFT_LIKE("LEFT LIKE"),
    NOT_LIKE("NOT LIKE"),
    FULL_LIKE("FULLLIKE"),
    EQ("="),
    NE("!="),
    GT(">"),
    GE(">="),
    LT("<"),
    LE("<="),
    IS_NULL("IS NULL"),
    IS_NOT_NULL("IS NOT NULL"),
    GROUP_BY("GROUP BY"),
    HAVING("HAVING"),
    ORDER_BY("ORDER BY"),
    EXISTS("EXISTS"),
    NOT_EXISTS("NOT EXISTS"),
    BETWEEN("BETWEEN"),
    NOT_BETWEEN("NOT BETWEEN"),
    ASC("ASC"),
    DESC("DESC"),
    LEFT_BRACKETS("("),
    RIGHT_BRACKETS(")");

    private String keyword;

    SqlKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String getSqlSegment() {
        return this.keyword;
    }

    public String toLow() {
        return this.keyword.toLowerCase();
    }
}
