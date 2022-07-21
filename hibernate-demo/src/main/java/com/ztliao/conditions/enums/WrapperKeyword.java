package com.ztliao.conditions.enums;

import com.ztliao.conditions.ISqlSegment;

/**
 * wrapper 内部使用枚举
 *
 * @author miemie
 * @since 2018-07-30
 */
public enum WrapperKeyword implements ISqlSegment {

    /**
     * 只用作于辨识,不用于其他
     */
    APPLY(null);


    private String keyword;

    WrapperKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String getSqlSegment() {
        return keyword;
    }
}