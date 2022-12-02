package com.ztliao.test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDescription;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: liaozetao
 * @date: 2022/11/22 16:21
 * @description:
 */
public class ReflectTest {

    public static void main(String[] args) throws NoSuchFieldException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        TypeDescription.Generic build = TypeDescription.Generic.Builder.parameterizedType(List.class, String.class).build();
        System.out.println(build.asGenericType());
        TypeDescription.Generic generic = TypeDescription.Generic.Builder.parameterizedType(
                        new TypeDescription.ForLoadedType(TypeReference.class),
                        build)
                .build();
        Class<?> loaded = new ByteBuddy()
                .subclass(generic)
                .name(ReflectTest.class.getPackage().getName() + ".ListTypeReference")
                .make()
                .load(Thread.currentThread().getContextClassLoader())
                .getLoaded();
        System.out.println(loaded.getName());
        String json = "[\"1\"]";
        List<String> list1 = JSONObject.parseObject(json, (TypeReference) loaded.newInstance());
        System.out.println("list1 = " + list1.getClass().getName());
        for (String s : list1) {
            System.out.println("list1 = " + s);
        }
        List<String> list2 = JSONObject.parseObject(json, new TypeReference<List<String>>() {
        });
        for (String s : list2) {
            System.out.println("list2 = " + s);
        }
        List<String> strings = getObject(json, String.class);
        for (String string : strings) {
            System.out.println(string);
        }
        List<Object> objects = new ArrayList<>();
        objects.add("string");
        objects.add(1L);
        objects.add(1.1D);
        objects.add(true);
        objects.add(1F);
        for (Object object : objects) {
            System.out.println(object.getClass());
        }
    }

    public static <T> List<Object> parseObject(String json, List<Class<?>> classes) {
        return JSONObject.parseObject(json, new TypeReference<List<T>>() {
        });
    }

    public static <T> List<T> getObject(String json, Class<T> innerClass) {
        try {
            TypeDescription.Generic build = TypeDescription.Generic.Builder.parameterizedType(List.class, innerClass).build();
            System.out.println(build.asGenericType());
            TypeDescription.Generic generic = TypeDescription.Generic.Builder.parameterizedType(
                            new TypeDescription.ForLoadedType(TypeReference.class),
                            build)
                    .build();
            Class<?> loaded = new ByteBuddy()
                    .subclass(generic)
                    .name(ReflectTest.class.getPackage().getName() + "." + List.class.getSimpleName() + "TypeReference")
                    .make()
                    .load(Thread.currentThread().getContextClassLoader())
                    .getLoaded();
            return JSONObject.parseObject(json, (TypeReference) loaded.newInstance());
        } catch (Exception ignored) {
            return null;
        }
    }

    public static class ListTypeReference extends TypeReference<List<String>> {
        public ListTypeReference() {
            super();
        }

        public ListTypeReference(Type... actualTypeArguments) {
            super(actualTypeArguments);
        }
    }

    public static void getGeneric(Class<?> clazz) {
        for (Method declaredMethod : clazz.getDeclaredMethods()) {
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
}
