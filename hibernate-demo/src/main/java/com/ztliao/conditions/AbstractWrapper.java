package com.ztliao.conditions;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.ztliao.conditions.enums.SqlKeyword;
import com.ztliao.conditions.interfaces.*;
import com.ztliao.conditions.interfaces.Join;
import com.ztliao.model.dao.AbstractDaoSupport;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author: liaozetao
 * @date: 2022/3/22 2:34 PM
 * @description:
 */
public abstract class AbstractWrapper<T, R, Children extends AbstractWrapper<T, R, Children>> extends AbstractDaoSupport<T> implements Compare<Children, R>, Func<Children, R>, Parameters<Children, R>, Query<Children, R>, Nested<Children, Children>, Join<Children> {

    protected static final String ONE_EQ_ONE = "1 = 1";
    protected final Children typedThis = (Children) this;

    protected StringBuffer sql;

    protected Class<T> entityClass;

    protected Map<String, Object> conditions;

    /**
     * 拼接首部的SQL
     */
    private String firstSql;

    /**
     * 拼接尾部的SQL
     */
    private String lastSql;

    /**
     * count语句
     */
    private String countSql;

    /**
     * 是否为HQL
     */
    protected boolean isHql = false;

    public Class<T> getEntityClass() {
        return this.entityClass;
    }

    /**
     * 子类返回一个自己的新对象
     */
    protected abstract Children instance();

    protected abstract String columnToString(R column);

    /**
     * 对sql片段进行组装
     *
     * @param condition   是否执行
     * @param sqlSegments sql片段数组
     * @return children
     */
    protected Children doIt(boolean condition, ISqlSegment... sqlSegments) {
        if (condition && sqlSegments != null && sqlSegments.length > 0) {
            for (ISqlSegment sqlSegment : sqlSegments) {
                appendSql(sqlSegment.getSqlSegment());
            }
        }
        return typedThis;
    }

    /**
     * 覆盖SQL
     */
    protected void newString() {
        newString(null);
    }

    /**
     * 覆盖SQL
     *
     * @param sql
     */
    protected void newString(String sql) {
        if (StrUtil.isNotEmpty(sql)) {
            this.sql = new StringBuffer(sql);
        } else {
            this.sql = new StringBuffer();
        }
    }

    /**
     * 多重嵌套查询条件
     *
     * @param condition 查询条件值
     */
    protected Children addNestedCondition(boolean condition, Consumer<Children> consumer) {
        if (condition) {
            final Children instance = instance();
            if (instance != null) {
                consumer.accept(instance);
                appendSql(SqlKeyword.LEFT_BRACKETS.getSqlSegment() + instance.getSqlSegment() + SqlKeyword.RIGHT_BRACKETS.getSqlSegment());
                putAll(true, instance.getConditions());
            }
        }
        return typedThis;
    }

    protected Children doIt(boolean condition, R column, Object val, SqlKeyword sqlKeyword) {
        if (condition) {
            prepareParam(this.sql, columnToString(column), columnToString(column), val, this.conditions, sqlKeyword.toLow());
        }
        return typedThis;
    }

    @Override
    public <V> Children allEq(boolean condition, Map<R, V> params, boolean null2IsNull) {
        if (condition && CollectionUtil.isNotEmpty(params)) {
            params.forEach((k, v) -> {
                if (ObjectUtil.isNotEmpty(v)) {
                    eq(k, v);
                } else {
                    if (null2IsNull) {
                        isNull(k);
                    }
                }
            });
        }
        return typedThis;
    }

    @Override
    public <V> Children allEq(boolean condition, BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull) {
        if (condition && CollectionUtil.isNotEmpty(params)) {
            params.forEach((k, v) -> {
                if (filter.test(k, v)) {
                    if (ObjectUtil.isNotEmpty(v)) {
                        eq(k, v);
                    } else {
                        if (null2IsNull) {
                            isNull(k);
                        }
                    }
                }
            });
        }
        return typedThis;
    }

    @Override
    public Children put(boolean condition, R column, Object val) {
        if (condition) {
            this.conditions.put(columnToString(column), val);
        }
        return typedThis;
    }

    @Override
    public Children putAll(boolean condition, Map<String, Object> params) {
        if (condition) {
            this.conditions.putAll(params);
        }
        return typedThis;
    }

    @Override
    public Children appendSql(boolean condition, String sql) {
        if (condition) {
            this.sql.append(StrUtil.SPACE).append(sql).append(StrUtil.SPACE);
        }
        return typedThis;
    }

    @Override
    public Children eq(boolean condition, R column, Object val) {
        return doIt(condition, column, val, SqlKeyword.EQ);
    }

    @Override
    public Children ne(boolean condition, R column, Object val) {
        return doIt(condition, column, val, SqlKeyword.NE);
    }

    @Override
    public Children gt(boolean condition, R column, Object val) {
        return doIt(condition, column, val, SqlKeyword.GT);
    }

    @Override
    public Children ge(boolean condition, R column, Object val) {
        return doIt(condition, column, val, SqlKeyword.GE);
    }

    @Override
    public Children lt(boolean condition, R column, Object val) {
        return doIt(condition, column, val, SqlKeyword.LT);
    }

    @Override
    public Children le(boolean condition, R column, Object val) {
        return doIt(condition, column, val, SqlKeyword.LE);
    }

    @Override
    public Children notLike(boolean condition, R column, Object val) {
        return doIt(condition, column, val, SqlKeyword.NOT_LIKE);
    }

    @Override
    public Children like(boolean condition, R column, Object val) {
        return doIt(condition, column, val, SqlKeyword.FULL_LIKE);
    }

    @Override
    public Children likeLeft(boolean condition, R column, Object val) {
        return doIt(condition, column, val, SqlKeyword.LEFT_LIKE);
    }

    @Override
    public Children likeRight(boolean condition, R column, Object val) {
        return doIt(condition, column, val, SqlKeyword.LIKE);
    }

    @Override
    public Children between(boolean condition, R column, Object val1, Object val2) {
        if (condition) {
            String columnName = this.columnToString(column);
            appendSql(SqlKeyword.AND.toLow() +
                    StrUtil.SPACE +
                    columnName +
                    StrUtil.SPACE +
                    SqlKeyword.BETWEEN.toLow() +
                    StrUtil.SPACE +
                    val1 +
                    StrUtil.SPACE +
                    SqlKeyword.AND.toLow() +
                    StrUtil.SPACE +
                    val2);
        }
        return typedThis;
    }

    @Override
    public Children notBetween(boolean condition, R column, Object val1, Object val2) {
        if (condition) {
            String columnName = this.columnToString(column);
            appendSql(SqlKeyword.AND.toLow() +
                    StrUtil.SPACE +
                    columnName +
                    StrUtil.SPACE +
                    SqlKeyword.NOT_BETWEEN.toLow() +
                    StrUtil.SPACE +
                    val1 +
                    StrUtil.SPACE +
                    SqlKeyword.AND.toLow() +
                    StrUtil.SPACE +
                    val2);
        }
        return typedThis;
    }

    @Override
    public Children in(boolean condition, R column, Collection<?> coll) {
        if (coll != null && !coll.isEmpty()) {
            doIt(condition, column, CollectionUtil.join(coll, StrUtil.COMMA), SqlKeyword.IN);
        }
        return typedThis;
    }


    @Override
    public Children notIn(boolean condition, R column, Collection<?> coll) {
        if (coll != null && !coll.isEmpty()) {
            doIt(condition, column, CollectionUtil.join(coll, StrUtil.COMMA), SqlKeyword.NOT_IN);
        }
        return typedThis;
    }

    @Override
    public Children inStr(boolean condition, R column, Object val) {
        return doIt(condition, column, val, SqlKeyword.IN);
    }

    @Override
    public Children notInStr(boolean condition, R column, Object val) {
        return doIt(condition, column, val, SqlKeyword.NOT_IN);
    }

    @Override
    public Children isNull(boolean condition, R column) {
        if (condition) {
            prepareParam(this.sql, columnToString(column), columnToString(column), null, this.conditions, SqlKeyword.IS_NULL.toLow());
        }
        return typedThis;
    }

    @Override
    public Children isNotNull(boolean condition, R column) {
        if (condition) {
            prepareParam(this.sql, columnToString(column), columnToString(column), null, this.conditions, SqlKeyword.IS_NOT_NULL.toLow());
        }
        return typedThis;
    }

    @Override
    public Children func(boolean condition, Consumer<Children> consumer) {
        if (condition) {
            consumer.accept(typedThis);
        }
        return typedThis;
    }

    @Override
    public Children last(boolean condition, String lastSql) {
        if (condition) {
            this.lastSql = lastSql;
        }
        return typedThis;
    }

    @Override
    public Children first(boolean condition, String firstSql) {
        if (condition) {
            this.firstSql = firstSql;
        }
        return typedThis;
    }

    @Override
    public Children exists(boolean condition, String existsSql) {
        if (condition) {
            prepareParam(this.sql, existsSql, existsSql, null, this.conditions, SqlKeyword.EXISTS.toLow());
        }
        return typedThis;
    }

    @Override
    public Children notExists(boolean condition, String notExistsSql) {
        if (condition) {
            prepareParam(this.sql, notExistsSql, notExistsSql, null, this.conditions, SqlKeyword.NOT_EXISTS.toLow());
        }
        return typedThis;
    }

    @Override
    public Children or() {
        return or(true).appendSql(ONE_EQ_ONE);
    }

    /**
     * 内部自用
     * <p>NOT 关键词</p>
     */
    protected Children not(boolean condition) {
        return doIt(condition, SqlKeyword.NOT::toLow);
    }

    /**
     * 内部自用
     * <p>拼接 AND</p>
     */
    protected Children and(boolean condition) {
        return doIt(condition, SqlKeyword.AND::toLow);
    }

    /**
     * 内部自用
     * <p>拼接 OR</p>
     */
    @Override
    public Children or(boolean condition) {
        return doIt(condition, SqlKeyword.OR::toLow);
    }

    @Override
    public Children and(boolean condition, Consumer<Children> consumer) {
        return and(condition).addNestedCondition(condition, consumer);
    }

    @Override
    public Children or(boolean condition, Consumer<Children> consumer) {
        return or(condition).addNestedCondition(condition, consumer);
    }

    @Override
    public Children nested(boolean condition, Consumer<Children> consumer) {
        return addNestedCondition(condition, consumer);
    }

    @Override
    public Children not(boolean condition, Consumer<Children> consumer) {
        return not(condition).addNestedCondition(condition, consumer);
    }

    @Override
    public Children orderBy(String orderBy) {
        changeQuerySort(this.sql, orderBy, null, null, this.isHql);
        return typedThis;
    }

    @Override
    public Children orderBy(boolean condition, boolean isHql, boolean isAsc, R... columns) {
        if (condition && columns != null && columns.length > 0) {
            String orderBy = Arrays.stream(columns).map(this::columnToString).map(v -> v + StrUtil.SPACE + (isAsc ? SqlKeyword.ASC.toLow() : SqlKeyword.DESC.toLow())).reduce((v1, v2) -> v1 + StrUtil.COMMA + v2).get();
            changeQuerySort(this.sql, orderBy, null, null, isHql);
        }
        return typedThis;
    }

    @Override
    public Children inSql(boolean condition, R column, String inValue) {
        return doIt(condition, column, inValue, SqlKeyword.IN);
    }

    @Override
    public Children notInSql(boolean condition, R column, String inValue) {
        return doIt(condition, column, inValue, SqlKeyword.NOT_IN);
    }

    @Override
    public Children groupBy(boolean condition, R... columns) {
        try {
            if (condition && columns != null && columns.length > 0) {
                List<String> columnNames = Arrays.stream(columns).map(this::columnToString).collect(Collectors.toList());
                //sql解析
                Statement statement = CCJSqlParserUtil.parse(this.sql.toString());
                PlainSelect plainSelect = ((Select) statement).getSelectBody(PlainSelect.class);
                GroupByElement groupByElement = plainSelect.getGroupBy();
                if (groupByElement == null) {
                    groupByElement = new GroupByElement();
                }
                List<Expression> expressions = new ArrayList<>();
                for (String columnName : columnNames) {
                    if (columnName.contains(StrUtil.COMMA)) {
                        for (String column : columnName.split(StrUtil.COMMA)) {
                            expressions.add(getColumn(column));
                        }
                    } else {
                        expressions.add(getColumn(columnName));
                    }
                }
                groupByElement.withGroupByExpressions(expressions);
                plainSelect.setGroupByElement(groupByElement);
                newString(plainSelect.toString());
            }
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        return typedThis;
    }

    @Override
    public Children select(R... columns) {
        try {
            if (columns != null && columns.length > 0) {
                List<String> columnNames = Arrays.stream(columns).map(this::columnToString).collect(Collectors.toList());
                if (this.isHql) {
                    newString("select" + StrUtil.SPACE + "*" + StrUtil.SPACE + this.sql.toString());
                }
                //sql解析
                Statement statement = CCJSqlParserUtil.parse(this.sql.toString());
                PlainSelect plainSelect = ((Select) statement).getSelectBody(PlainSelect.class);
                //查询字段
                List<SelectItem> selectItems = new ArrayList<>();
                //字段
                for (String columnName : columnNames) {
                    if (columnName.contains(StrUtil.COMMA)) {
                        for (String column : columnName.split(StrUtil.COMMA)) {
                            selectItems.add(getSelectItem(column));
                        }
                    } else {
                        selectItems.add(getSelectItem(columnName));
                    }
                }
                plainSelect.setSelectItems(selectItems);
                newString(plainSelect.toString());
            }
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        return typedThis;
    }

    @Override
    public Children having(boolean condition, String sqlHaving) {
        try {
            if (condition && StrUtil.isNotEmpty(sqlHaving)) {
                //sql解析
                Statement statement = CCJSqlParserUtil.parse(this.sql.toString());
                PlainSelect plainSelect = ((Select) statement).getSelectBody(PlainSelect.class);
                plainSelect.setHaving(CCJSqlParserUtil.parseExpression(sqlHaving));
                newString(plainSelect.toString());
            }
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        return typedThis;
    }

    /**
     * 生成查询字段
     *
     * @param columnName
     * @return
     */
    private SelectExpressionItem getSelectItem(String columnName) {
        Column column = getColumn(columnName);
        SelectExpressionItem selectItem = new SelectExpressionItem();
        selectItem.setExpression(column);
        return selectItem;
    }

    /**
     * 生成字段
     *
     * @param columnName
     * @return
     */
    private Column getColumn(String columnName) {
        Column column = new Column(columnName);
        if (columnName.contains(StrUtil.DOT)) {
            String[] columnArray = columnName.split("\\.");
            column.setTable(new Table().withAlias(new Alias(columnArray[0])));
            column.setColumnName(columnArray[1]);
        }
        return column;
    }

    @Override
    public String getSqlSelect() {
        StringBuilder selectSegment = new StringBuilder();
        Map<String, String> columnMap = formatSqlField(this.sql.toString());
        if (columnMap.isEmpty()) {
            return selectSegment.toString();
        }
        for (Map.Entry<String, String> entry : columnMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            selectSegment.append(value).append(StrUtil.SPACE).append("as").append(StrUtil.SPACE).append(key).append(StrUtil.COMMA);
        }
        //去除最后的逗号
        if (selectSegment.length() > 0) {
            selectSegment.deleteCharAt(selectSegment.length() - 1);
        }
        return selectSegment.toString();
    }

    @Override
    public Children selectCount(String countSql) {
        if (StrUtil.isEmpty(countSql)) {
            if (this.isHql && !this.sql.toString().toLowerCase().contains("select")) {
                this.countSql = "select count(*)";
            } else {
                newString(getSqlCountString(this.sql).toString());
            }
        } else {
            newString(getSimpleSqlCountString(this.sql, countSql).toString());
        }
        return typedThis;
    }

    @Override
    public String getSqlSegment() {
        String firstSql = "";
        String lastSql = "";
        String countSql = "";
        if (StrUtil.isNotEmpty(this.firstSql)) {
            firstSql = this.firstSql + StrUtil.SPACE;
        }
        if (StrUtil.isNotEmpty(this.lastSql)) {
            lastSql = StrUtil.SPACE + this.lastSql;
        }
        if (StrUtil.isNotEmpty(this.countSql)) {
            countSql = this.countSql + StrUtil.SPACE;
        }
        return firstSql + countSql + this.sql + lastSql;
    }

    @Override
    public Map<String, Object> getConditions() {
        return this.conditions;
    }

}
