package com.ztliao.model.dao;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.ztliao.conditions.Wrapper;
import com.ztliao.conditions.enums.SqlKeyword;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AbstractDaoSupport<T> extends Wrapper<T> {

    /**
     * @param sql
     * @return Map<String, String> key:别名，val:sql字段
     * @description 查找原生sql中查询字段的别名
     */
    protected static Map<String, String> formatSqlField(String sql) {
        Map<String, String> map = new HashMap<>();
        if (StrUtil.isBlank(sql)) {
            return map;
        }
        String reg = "(?<=select[\\s\\n\\r]).+(?=[\\r\\n\\s]from)";
        Pattern p = Pattern.compile(reg, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(sql);
        if (!m.find()) {
            return map;
        }
        String selects = m.group().trim();
        String[] selectArray = selects.split(",");
        if (selectArray.length == 0) {
            return map;
        }
        reg = "[\\s\\n\\r]+as[\\s\\n\\r]+";
        p = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        Pattern emptyPattern = Pattern.compile("[\\s\\n\\r]+");
        for (String str : selectArray) {
            if (StrUtil.isBlank(str)) {
                continue;
            }
            m = p.matcher(str.trim());
            //包含as
            if (m.find()) {
                String[] arr = str.trim().split(m.group());
                if (arr.length != 2) {
                    continue;
                }
                map.put(arr[1].trim(), arr[0].trim());
                continue;
            }
            m = emptyPattern.matcher(str.trim());
            //不包含as
            if (m.find()) {
                String[] arr = str.split(m.group());
                if (arr.length != 2) {
                    continue;
                }
                map.put(arr[1].trim(), arr[0].trim());
                continue;
            }
            //无别名
            map.put(str.trim(), str.trim());
        }
        return map;
    }

    /**
     * @param clazz         查询的Hbm对象
     * @param jsonParam     查询条件
     *                      {
     *                      xxx:      等于
     *                      xxx_like: 包含( 关键词%)
     *                      xxx_fulllike: 包含( %关键词%)
     *                      xxx_lt:   小于
     *                      xxx_lte:  小于等于
     *                      xxx_gt:   大于
     *                      xxx_gte:  大于等于
     *                      xxx_in:   in
     *                      }
     * @param sql           sql
     * @param queryParamMap Hbm参数Map
     */
    public static void parperHbmParam(Class<?> clazz, JSONObject jsonParam, StringBuffer sql, Map<String, Object> queryParamMap) {
        if (jsonParam == null) {
            return;
        }
        if (queryParamMap == null) {
            queryParamMap = new HashMap<>();
        }
        Set<String> keys = jsonParam.keySet();
        Map<String, String> selectFields = formatSqlField(sql.toString());
        for (String key : keys) {
            if (StrUtil.isBlank(jsonParam.getString(key)) || "operatorUserId".equals(key)) {
                continue;
            }
            String queryAttr = key;
            String operator = SqlKeyword.EQ.toLow();
            if (key.endsWith("_like")) {
                //包含
                operator = SqlKeyword.LIKE.toLow();
                key = key.substring(0, key.lastIndexOf("_like"));
            } else if (key.endsWith("_fulllike")) {
                //包含
                operator = SqlKeyword.FULL_LIKE.toLow();
                key = key.substring(0, key.lastIndexOf("_fulllike"));
            } else if (key.endsWith("_lt")) {
                //小于
                operator = SqlKeyword.LT.toLow();
                key = key.substring(0, key.lastIndexOf("_lt"));
            } else if (key.endsWith("_lte")) {
                //小于等于
                operator = SqlKeyword.LE.toLow();
                key = key.substring(0, key.lastIndexOf("_lte"));
            } else if (key.endsWith("_gt")) {
                //大于
                operator = SqlKeyword.GT.toLow();
                key = key.substring(0, key.lastIndexOf("_gt"));
            } else if (key.endsWith("_gte")) {
                //大于等于
                operator = SqlKeyword.GE.toLow();
                key = key.substring(0, key.lastIndexOf("_gte"));
            } else if (key.endsWith("_in")) {
                //in
                operator = SqlKeyword.IN.toLow();
                key = key.substring(0, key.lastIndexOf("_in"));
            } else if (key.endsWith("_neq")) {
                //不等于
                operator = SqlKeyword.NE.toLow();
                key = key.substring(0, key.lastIndexOf("_neq"));
            } else if (key.endsWith("_notin")) {
                //not in
                operator = SqlKeyword.NOT_IN.toLow();
                key = key.substring(0, key.lastIndexOf("_notin"));
            } else if (key.endsWith("_isnull")) {
                //is null
                operator = SqlKeyword.IS_NULL.toLow();
                key = key.substring(0, key.lastIndexOf("_isnull"));
            } else if (key.endsWith("_isnotnull")) {
                //is not null
                operator = SqlKeyword.IS_NOT_NULL.toLow();
                key = key.substring(0, key.lastIndexOf("_isnotnull"));
            }
            String sqlField = selectFields.getOrDefault(key, key);
            try {
                Object attributeValue = jsonParam.get(queryAttr);
                if (attributeValue instanceof List) {
                    if (!selectFields.containsKey(key)) {
                        continue;
                    }
                    List<?> attrs = (List<?>) attributeValue;
                    if (!attrs.isEmpty()) {
                        sql.append(StrUtil.SPACE).append(SqlKeyword.AND.toLow()).append(StrUtil.SPACE).append(sqlField).append(" in (:").append(queryAttr).append(") \n");
                        queryParamMap.put(queryAttr, attrs);
                    }
                    continue;
                }
                if (clazz == null || SqlKeyword.FULL_LIKE.toLow().equals(operator) || SqlKeyword.LIKE.toLow().equals(operator) || SqlKeyword.IN.toLow().equals(operator) || SqlKeyword.NOT_IN.toLow().equals(operator) || attributeValue instanceof Date) {
                    prepareParam(sql, sqlField, queryAttr, attributeValue, queryParamMap, operator);
                    continue;
                }
                if (!ReflectUtil.hasField(clazz, key)) {
                    continue;
                }
                Field field = clazz.getDeclaredField(key);
                String fieldType = field.getType().getSimpleName();
                if (Integer.class.getSimpleName().equals(fieldType)) {
                    Integer attributeValueInteger = NumberUtil.binaryToInt(attributeValue.toString());
                    prepareParam(sql, sqlField, queryAttr, attributeValueInteger, queryParamMap, operator);
                } else if (Long.class.getSimpleName().equals(fieldType)) {
                    Long attributeValueInteger = Long.parseLong(attributeValue.toString());
                    prepareParam(sql, sqlField, queryAttr, attributeValueInteger, queryParamMap, operator);
                } else if (Date.class.getSimpleName().equals(fieldType)) {
                    String attributeValueStr = attributeValue.toString();
                    LocalDateTime dateTime = LocalDateTime.parse(attributeValueStr, DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN));
                    Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
                    if (SqlKeyword.LE.getSqlSegment().equals(operator) && attributeValueStr.length() == 10) {
                        //只有在日期格式为yyyy-MM-dd时，才需要加1天，< date + 1
                        date = Date.from(dateTime.plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
                        ;
                        operator = SqlKeyword.LT.getSqlSegment();
                    }
                    prepareParam(sql, sqlField, queryAttr, date, queryParamMap, operator);
                } else {
                    String attributeValueStr = attributeValue.toString();
                    prepareParam(sql, sqlField, queryAttr, attributeValueStr, queryParamMap, operator);
                }
            } catch (NumberFormatException | NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成Hbm条件
     *
     * @param clazz         查询的Hbm对象
     * @param jsonParam     入参
     * @param queryAttr     查询属性及从jsonParam取值的key
     * @param sb            HQL
     * @param queryParamMap Hbm参数Map
     * @param operator      查询符号：=、like、in
     */
    public static void parperHbmParam(Class<?> clazz, JSONObject jsonParam, String queryAttr, StringBuffer sb, Map<String, Object> queryParamMap, String operator) {
        parperHbmParam(clazz, jsonParam, queryAttr, queryAttr, sb, queryParamMap, operator);
    }

    /**
     * 生成Hbm条件
     *
     * @param clazz         查询的Hbm对象
     * @param jsonParam     入参
     * @param fieldName     查询属性
     * @param queryAttr     从jsonParam取值的Key
     * @param sb            HQL
     * @param queryParamMap Hbm参数Map
     * @param operator      查询符号：=、like、in
     */
    public static void parperHbmParam(Class<?> clazz, JSONObject jsonParam, String fieldName, String queryAttr, StringBuffer sb, Map<String, Object> queryParamMap, String operator) {
        try {
            if (null == jsonParam || !jsonParam.containsKey(queryAttr) || StrUtil.isBlank(jsonParam.getString(queryAttr))) {
                return;
            }
            Object attributeValue = jsonParam.get(queryAttr);
            if (!ReflectUtil.hasField(clazz, queryAttr)) {
                return;
            }
            Field field = clazz.getDeclaredField(queryAttr);
            String fieldType = field.getType().getSimpleName();
            if (SqlKeyword.IN.toLow().equals(operator) || SqlKeyword.NOT_IN.toLow().equals(operator)) {
                prepareParam(sb, fieldName, queryAttr, attributeValue, queryParamMap, operator);
            } else if (Integer.class.getSimpleName().equals(fieldType)) {
                Integer attributeValueInteger = NumberUtil.parseInt(attributeValue.toString());
                prepareParam(sb, fieldName, queryAttr, attributeValueInteger, queryParamMap, operator);
            } else if (Long.class.getSimpleName().equals(fieldType)) {
                Long attributeValueInteger = Long.parseLong(attributeValue.toString());
                prepareParam(sb, fieldName, queryAttr, attributeValueInteger, queryParamMap, operator);
            } else if (Date.class.getSimpleName().equals(fieldType)) {
                if (attributeValue instanceof Date) {
                    prepareParam(sb, fieldName, queryAttr, attributeValue, queryParamMap, operator);
                    return;
                }
                LocalDateTime dateTime = LocalDateTime.parse(attributeValue.toString(), DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN));
                Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
                prepareParam(sb, fieldName, queryAttr, attributeValue, queryParamMap, operator);
            } else {
                String attributeValueStr = attributeValue.toString();
                prepareParam(sb, fieldName, queryAttr, attributeValueStr, queryParamMap, operator);
            }
        } catch (NumberFormatException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成sql条件
     *
     * @param jsonParam     入参
     * @param queryAttr     查询字段及从jsonParam取值的key
     * @param sb            sql
     * @param queryParamMap sql参数Map
     * @param operator      查询符号：=、like、in
     */
    public static void parperParam(JSONObject jsonParam, String queryAttr, StringBuffer sb, Map<String, Object> queryParamMap, String operator) {
        parperParam(jsonParam, queryAttr, queryAttr, sb, queryParamMap, operator, false);
    }

    /**
     * 生成sql或hql条件，根据isHql判断，当传入的字段是数据库字段时使用
     *
     * @param jsonParam     入参
     * @param fieldName     查询字段
     * @param queryAttr     jsonParam取值的key
     * @param sb            sql
     * @param queryParamMap sql参数Map
     * @param operator      查询符号：=、like、in
     * @param isHql         是否根据字段查询
     * @author ZhangJun
     * @creteTime 2017-12-11
     */
    public static void parperParam(JSONObject jsonParam, String fieldName, String queryAttr, StringBuffer sb, Map<String, Object> queryParamMap, String operator, boolean isHql) {
        if (isHql) {
            if (fieldName.contains(StrUtil.DOT)) {
                //有别名，截取别名后面字段
                String alias = fieldName.substring(0, fieldName.indexOf(StrUtil.DOT));
                String newFieldName = fieldName.substring(fieldName.indexOf(StrUtil.DOT) + 1);
                newFieldName = remove_AndNextChar2UpperCase(newFieldName);
                if (StrUtil.isNotBlank(alias)) {
                    fieldName = alias + StrUtil.DOT + newFieldName;
                } else {
                    fieldName = newFieldName;
                }
            } else {
                fieldName = remove_AndNextChar2UpperCase(fieldName);
            }
        }
        parperParam(jsonParam, fieldName, queryAttr, sb, queryParamMap, operator);
    }

    /**
     * 生成sql条件
     *
     * @param jsonParam     入参
     * @param fieldName     查询字段
     * @param queryAttr     jsonParam取值的key
     * @param sb            sql
     * @param queryParamMap sql参数Map
     * @param operator      查询符号：=、like、in
     */
    public static void parperParam(JSONObject jsonParam, String fieldName, String queryAttr, StringBuffer sb, Map<String, Object> queryParamMap, String operator) {
        if (null == jsonParam) {
            return;
        }
        if (jsonParam.containsKey(queryAttr) && StrUtil.isNotBlank(queryAttr)) {
            Object attributeValue = jsonParam.get(queryAttr);
            if (attributeValue == null || StrUtil.isBlank(attributeValue.toString())) return;
            if (attributeValue instanceof Integer) {
                Integer attributeValueInteger = NumberUtil.parseInt(attributeValue.toString());
                prepareParam(sb, fieldName, queryAttr, attributeValueInteger, queryParamMap, operator);
            } else if (attributeValue instanceof Long) {
                long attributeValueInteger = Long.parseLong(attributeValue.toString());
                prepareParam(sb, fieldName, queryAttr, attributeValueInteger, queryParamMap, operator);
            } else if (NumberUtil.isNumber(attributeValue.toString())) {
                long attributeValueInteger = Long.parseLong(attributeValue.toString());
                if (attributeValueInteger > Integer.MAX_VALUE) {
                    prepareParam(sb, fieldName, queryAttr, attributeValueInteger, queryParamMap, operator);
                } else {
                    prepareParam(sb, fieldName, queryAttr, NumberUtil.parseInt(attributeValue.toString()), queryParamMap, operator);
                }
            } else {
                String attributeValueStr = attributeValue.toString();
                prepareParam(sb, fieldName, queryAttr, attributeValueStr, queryParamMap, operator);
            }
        }
    }

    /**
     * @param sb            sql或hql
     * @param fieldName     字段或属性
     * @param queryAttr     jsonParam取值的key
     * @param value         查询字段值
     * @param queryParamMap sql或hql参数Map
     * @param operator      查询符号：=、like、in
     * @author ZhangJun
     * @creteTime 2017-12-11
     */
    protected static void prepareParam(StringBuffer sb, String fieldName, String queryAttr, Object value, Map<String, Object> queryParamMap, String operator) {
        //转小写
        operator = operator.toLowerCase();
        if (SqlKeyword.IS_NULL.toLow().equals(operator) || SqlKeyword.IS_NOT_NULL.toLow().equals(operator)) {
            sb.append(StrUtil.SPACE).append(SqlKeyword.AND.toLow()).append(StrUtil.SPACE).append(fieldName).append(StrUtil.SPACE).append(operator).append(" \n");
        } else if (SqlKeyword.EXISTS.toLow().equals(operator) || SqlKeyword.NOT_EXISTS.toLow().equals(operator)) {
            sb.append(StrUtil.SPACE).append(SqlKeyword.AND.toLow()).append(StrUtil.SPACE).append(operator).append("(").append(fieldName).append(") \n");
        } else {
            //由于in判断直接赋值，不需要此语句，因此将此语句放入判断内
            if (StrUtil.isBlank(Objects.toString(value, ""))) {
                return;
            }
            if (SqlKeyword.LIKE.toLow().equals(operator)) {
                sb.append(StrUtil.SPACE).append(SqlKeyword.AND.toLow()).append(StrUtil.SPACE).append(fieldName).append(StrUtil.SPACE).append(operator).append(" '").append(value).append("%' \n");
            } else if (SqlKeyword.LEFT_LIKE.toLow().equals(operator)) {
                sb.append(StrUtil.SPACE).append(SqlKeyword.AND.toLow()).append(StrUtil.SPACE).append(fieldName).append(StrUtil.SPACE).append(operator).append(" '%").append(value).append("' \n");
            } else if (SqlKeyword.FULL_LIKE.toLow().equals(operator)) {
                sb.append(StrUtil.SPACE).append(SqlKeyword.AND.toLow()).append(StrUtil.SPACE).append(fieldName).append(StrUtil.SPACE).append(SqlKeyword.LIKE.toLow()).append(" '%").append(value).append("%' \n");
            } else if (SqlKeyword.NOT_LIKE.toLow().equals(operator)) {
                sb.append(StrUtil.SPACE).append(SqlKeyword.AND.toLow()).append(StrUtil.SPACE).append(fieldName).append(StrUtil.SPACE).append(operator).append(" '%").append(value).append("%' \n");
            } else if (SqlKeyword.IN.toLow().equals(operator) || SqlKeyword.NOT_IN.toLow().equals(operator)) {
                String inValue = value.toString();
                if (inValue.contains(StrUtil.COMMA)) {
                    inValue = "'" + inValue.replaceAll(StrUtil.COMMA, "','") + "'";
                } else {
                    inValue = "'" + inValue + "'";
                }
                sb.append(StrUtil.SPACE).append(SqlKeyword.AND.toLow()).append(StrUtil.SPACE).append(fieldName).append(StrUtil.SPACE).append(operator).append(" (").append(inValue).append(") \n");
            } else {
                sb.append(StrUtil.SPACE).append(SqlKeyword.AND.toLow()).append(StrUtil.SPACE).append(fieldName).append(StrUtil.SPACE).append(operator).append(" :").append(queryAttr).append(" \n");
                queryParamMap.put(queryAttr, value);
            }
        }
    }

    /**
     * 移除下划线，并将下划线后一位转换为大写
     *
     * @param param 带下划线字段
     * @return 字段属性
     * @author ZhangJun
     * @creteTime 2017-12-11
     */
    public static String remove_AndNextChar2UpperCase(String param) {
        boolean endFlag = false;
        if (param.endsWith("_")) {
            endFlag = true;
        }
        if ("".equals(param.trim())) {
            return "";
        }
        param = param.toLowerCase();
        if ('_' == param.charAt(0)) {
            param = param.substring(1);
        }
        if ('_' == param.charAt(param.length() - 1)) {
            param = param.substring(0, param.length() - 1);
        }
        StringBuilder sb = new StringBuilder(param);
        Matcher mc = Pattern.compile("_").matcher(param);
        int i = 0;
        while (mc.find()) {
            int position = mc.end() - (i++);
            sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
        }
        if (endFlag) {
            return sb + "_";
        } else {
            return sb.toString();
        }
    }

    /**
     * 格式转换：去除下划线、转变成驼峰格式
     *
     * @param jsonObjectList 目标json对象列表
     * @return 驼峰格式对象列表
     */
    public static List<JSONObject> remove_AndNextChar2UpperCase(List<JSONObject> jsonObjectList) {
        List<JSONObject> resultJsonList = new ArrayList<>();
        for (JSONObject object : jsonObjectList) {
            JSONObject jsonResult = new JSONObject();
            for (Map.Entry<String, Object> entry : object.entrySet()) {
                String mapKey = entry.getKey();
                Object mapValue = entry.getValue();
                mapKey = AbstractDaoSupport.remove_AndNextChar2UpperCase(mapKey);
                jsonResult.put(mapKey, mapValue);
            }
            resultJsonList.add(jsonResult);
        }
        return resultJsonList;
    }

    /**
     * 生成获取总数SQL（SQL语句）
     *
     * @param sql sql语句
     * @return 返回select count(1) from (sql参数) cntemp
     */
    public static StringBuffer getSqlCountString(StringBuffer sql) {
        if (sql.toString().contains("count(1)") || sql.toString().contains("count(*)")) {
            return sql;
        }
        return new StringBuffer("select count(1) from (" + sql + ") cntemp");
    }

    public static StringBuffer getSqlCountString(StringBuffer sql, JSONObject queryParamJSON) {
        StringBuffer countSql = new StringBuffer();
        if (!ObjectUtil.isNull(queryParamJSON.get("isSearchCount")) && !queryParamJSON.getBooleanValue("isSearchCount")) {
            countSql.append("select 1 from dual");
        } else {
            countSql.append("select count(1) from (").append(sql).append(") cntemp");
        }
        return countSql;
    }

    /**
     * 生成获取总数SQL（SQL语句） 加索引列
     *
     * @param sql
     * @param countParam
     * @return
     */
    public static StringBuffer getSimpleSqlCountString(StringBuffer sql, String countParam) {
        String countSql = sql.toString();
        String lowerCountSql = countSql.toLowerCase();
        int index = lowerCountSql.indexOf(" from ");
        if (index == -1) {
            index = lowerCountSql.indexOf("\nfrom ");
        }
        if (index == -1) {
            index = lowerCountSql.indexOf("from\n");
        }
        if (index == -1) {
            index = lowerCountSql.indexOf("\nfrom\n");
        }
        StringBuffer newSql = new StringBuffer("select " + countParam + "\n");
        newSql.append(countSql.substring(index));
        return newSql;
    }

    /**
     * 设置排序字段
     *
     * @param queryParamJSON 参数
     * @param sql            SQL或HQL语句
     * @param defaultOrderBy 设置默认的排序，如：order_no asc,user_name desc
     * @param isHql          是否HQL语句
     * @author ZhangJun
     * @creteTime 2017-12-11
     */
    public static void changeQuerySort(JSONObject queryParamJSON, StringBuffer sql, String defaultOrderBy, boolean isHql) {
        String orderBy = defaultOrderBy;
        String sort = SqlKeyword.ASC.toLow();
        if (queryParamJSON != null) {
            orderBy = queryParamJSON.getString("orderBy");
            sort = queryParamJSON.getString("sort");
        }
        changeQuerySort(sql, defaultOrderBy, orderBy, sort, isHql);
    }

    /**
     * 设置排序字段
     *
     * @param sql
     * @param defaultOrderBy
     * @param orderBy
     * @param sort
     * @param isHql
     */
    public static void changeQuerySort(StringBuffer sql, String defaultOrderBy, String orderBy, String sort, boolean isHql) {
        if (StrUtil.isBlank(orderBy)) {
            if (StrUtil.isNotBlank(defaultOrderBy)) {
                if (isHql) {
                    String alias = "";
                    String field = defaultOrderBy;
                    //如果是hql语句，需要将字段名转换为属性
                    if (StrUtil.indexOf(defaultOrderBy, '.') != -1) {
                        alias = defaultOrderBy.substring(0, defaultOrderBy.indexOf("."));
                        field = defaultOrderBy.substring(defaultOrderBy.indexOf(".") + 1);
                    }
                    field = StrUtil.toCamelCase(field);
                    if (StrUtil.isNotBlank(alias)) {
                        defaultOrderBy = alias + "." + field;
                    } else {
                        defaultOrderBy = field;
                    }
                }
                sql.append(" ").append(SqlKeyword.ORDER_BY.toLow()).append(" ").append(defaultOrderBy);
            }
        } else {
            if (isHql) {
                //如果是hql语句，需要将字段名转换为属性
                orderBy = remove_AndNextChar2UpperCase(orderBy);
            }
            if (StrUtil.isBlank(sort)) {
                sort = SqlKeyword.ASC.toLow();
            }
            if (StrUtil.isNotBlank(orderBy)) {
                sql.append(" ").append(SqlKeyword.ORDER_BY.toLow()).append(" ").append(orderBy).append(" ").append(sort);
            }
        }
    }

    /**
     * ResultSet转list
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    public static List<JSONObject> resultSetToList(ResultSet rs) throws SQLException {
        List<JSONObject> list = new ArrayList<>();
        //得到结果集(rs)的结构信息，比如字段数、字段名等
        ResultSetMetaData md = rs.getMetaData();
        //返回此 ResultSet 对象中的列数
        int columnCount = md.getColumnCount();
        while (rs.next()) {
            JSONObject rowData = new JSONObject();
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnLabel(i) == null ? md.getColumnName(i) : md.getColumnLabel(i), rs.getObject(i));
            }
            list.add(rowData);
        }
        return list;
    }

    /**
     * 构造in语句，若valueList超过1000时，该函数会自动拆分成多个in语句
     *
     * @param item      字段
     * @param valueList 值集合
     * @return item in (valueList)
     * @author ZhangJun
     * @createTime 2018/3/13
     */
    public static <T> String buildLogicIN(String item, List<T> valueList) {
        int n = (valueList.size() - 1) / 1000;
        StringBuilder rtnStr = new StringBuilder();
        Object obj = valueList.get(0);
        boolean isString = obj instanceof Character || obj instanceof String;
        String tmpStr;
        for (int i = 0; i <= n; i++) {
            int size = i == n ? valueList.size() : (i + 1) * 1000;
            if (i > 0) {
                rtnStr.append(" or ");
            }
            rtnStr.append(item).append(" in (");
            if (isString) {
                StringBuilder tmpBuf = new StringBuilder();
                for (int j = i * 1000; j < size; j++) {
                    tmpStr = valueList.get(j).toString().replaceAll("'", "''");
                    tmpBuf.append(",'").append(tmpStr).append("'");
                }
                tmpStr = tmpBuf.substring(1);
            } else {
                tmpStr = valueList.subList(i * 1000, size).toString();
                tmpStr = tmpStr.substring(1, tmpStr.length() - 1);
            }
            rtnStr.append(tmpStr);
            rtnStr.append(")");
        }
        if (n > 0) {
            return "(" + rtnStr + ")";
        } else {
            return rtnStr.toString();
        }
    }

    @Override
    public Map<String, Object> getConditions() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getSqlSegment() {
        throw new UnsupportedOperationException();
    }

}
