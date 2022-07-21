/*
 * Copyright (c) 2011-2021, baomidou (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ztliao.conditions.interfaces;

import java.io.Serializable;

/**
 * @author miemie
 * @since 2018-12-12
 */
public interface Query<Children, R> extends Serializable {

    /**
     * 设置查询字段
     *
     * @param columns 字段数组
     * @return children
     */
    Children select(R... columns);

    /**
     * 查询条件 SQL 片段
     */
    String getSqlSelect();

    default Children selectCount() {
        return selectCount(null);
    }

    Children selectCount(String countSql);
}
