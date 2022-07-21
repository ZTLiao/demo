package com.ztliao.conditions;

import cn.hutool.core.util.StrUtil;
import com.ztliao.util.lambda.SFunction;
import com.ztliao.util.lambda.SerializedLambda;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: liaozetao
 * @date: 2022/6/6 10:03
 * @description:
 */
public abstract class AbstractLambdaWrapper<T, Children extends AbstractLambdaWrapper<T, Children>> extends AbstractWrapper<T, SFunction<T, ?>, Children> {

    private static final Map<String, WeakReference<SerializedLambda>> FUNC_CACHE = new ConcurrentHashMap<>();

    private boolean isInit = false;

    public AbstractLambdaWrapper() {
        this(null, null, true);
    }

    public AbstractLambdaWrapper(String sql) {
        this(sql, null, false);
    }

    public AbstractLambdaWrapper(String sql, Class<T> entityClass, boolean isHql) {
        this.entityClass = entityClass;
        if (isHql) {
            if (this.entityClass != null) {
                newString(String.format(getHqlFormat(), this.entityClass.getSimpleName()));
            } else if (StrUtil.isNotEmpty(sql)) {
                newString(sql);
            } else {
                newString();
            }
        } else {
            if (StrUtil.isNotEmpty(sql)) {
                newString(sql);
            } else {
                newString();
            }
        }
        this.conditions = new HashMap<>();
        this.isHql = isHql;
    }

    public abstract String getHqlFormat();

    @Override
    protected String columnToString(SFunction<T, ?> column) {
        String name = column.getClass().getName();
        if (!FUNC_CACHE.containsKey(name)) {
            FUNC_CACHE.put(name, new WeakReference<>(SerializedLambda.resolve(column)));
        }
        WeakReference<SerializedLambda> weakReference = FUNC_CACHE.get(name);
        if (weakReference == null || weakReference.get() == null) {
            FUNC_CACHE.put(name, new WeakReference<>(SerializedLambda.resolve(column)));
            weakReference = FUNC_CACHE.get(name);
        }
        if (weakReference != null) {
            SerializedLambda serializedLambda = weakReference.get();
            if (serializedLambda != null) {
                return getColumn(serializedLambda);
            }
        }
        return StrUtil.EMPTY;
    }

    private String getColumn(SerializedLambda lambda) {
        if (!this.isInit) {
            if (this.entityClass == null) {
                this.entityClass = (Class<T>) lambda.getInstantiatedType();
                if (this.sql.length() == 0) {
                    this.sql.append(String.format(getHqlFormat(), this.entityClass.getSimpleName()));
                }
            }
            this.isInit = true;
        }
        String property = methodToProperty(lambda.getImplMethodName());
        if (!this.isHql) {
            property = StrUtil.toUnderlineCase(property);
        }
        return property;
    }

    /**
     * 将get和set方法转为属性
     *
     * @param name
     * @return
     */
    public static String methodToProperty(String name) {
        if (name.startsWith("is")) {
            name = name.substring(2);
        } else {
            if (!name.startsWith("get") && !name.startsWith("set")) {
                throw new RuntimeException("Error parsing property name '" + name + "'.  Didn't start with 'is', 'get' or 'set'.");
            }
            name = name.substring(3);
        }
        if (name.length() == 1 || name.length() > 1 && !Character.isUpperCase(name.charAt(1))) {
            name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
        }
        return name;
    }
}
