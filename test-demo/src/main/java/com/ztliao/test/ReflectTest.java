package com.ztliao.test;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author: liaozetao
 * @date: 2022/11/22 16:21
 * @description:
 */
public class ReflectTest {

    public static void main(String[] args) throws NoSuchFieldException {
        for (Method declaredMethod : ReflectTest.class.getDeclaredMethods()) {
            String methodName = declaredMethod.getName();
            System.out.println(methodName);
            for (Type genericParameterType : declaredMethod.getGenericParameterTypes()) {
                System.out.println(genericParameterType.getClass().getName());
                if (genericParameterType instanceof ParameterizedTypeImpl) {
                    ParameterizedTypeImpl parameterizedTypeImpl = (ParameterizedTypeImpl) (genericParameterType);
                    Type actualTypeArgument = parameterizedTypeImpl.getActualTypeArguments()[0];
                    System.out.println(actualTypeArgument.getTypeName());
                }
            }
        }
    }

    public String helloWorld(List<Long> list) {
        return "hello,world!";
    }
}
