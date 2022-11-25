package com.ztliao.test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

/**
 * @author: liaozetao
 * @date: 2022/11/22 16:21
 * @description:
 */
public class ReflectTest {

    public static void main(String[] args) throws NoSuchFieldException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        Package aPackage = ReflectTest.class.getPackage();
        TypeReference<List<String>> typeReference = new TypeReference<List<String>>() {
        };
        TypeDescription.Generic build = TypeDescription.Generic.Builder.parameterizedType(List.class, String.class).build();
        System.out.println(build.asGenericType());
        TypeDescription.Generic ownerTypeGeneric = Optional.ofNullable(TypeReference.class.getDeclaringClass())
                .map(TypeDefinition.Sort::describe)
                .orElse(null);
        TypeDescription.Generic generic = TypeDescription.Generic.Builder.parameterizedType(
                        new TypeDescription.ForLoadedType(TypeReference.class),
                        ownerTypeGeneric,
                        build)
                .build();

        Class<?> loaded = new ByteBuddy()
                .subclass(generic)
                .name(aPackage.getName() + ".ListTypeReference")
                .make()
                .load(Thread.currentThread().getContextClassLoader())
                .getLoaded();
        System.out.println(loaded.getName());
        List<String> list = JSONObject.parseObject("[\"1\"]", (TypeReference) loaded.newInstance());
        Object obj = JSONObject.parseObject("[\"1\"]", (TypeReference) loaded.newInstance());
        System.out.println(obj.getClass().getName());
        for (String s : list) {
            System.out.println(s);
        }
//        new ByteBuddy()
//                .rebase(TypeReference.class)
//                .declaredTypes(List.class, Long.class);
//        Class<?> clazz = new ByteBuddy()
//                .makeInterface(TypeDescription.Generic.Builder.parameterizedType(TypeReference.class, List.class).build())
//                .name(TypeReference.class.getName())
//                .make()
//                .load(Thread.currentThread().getContextClassLoader())
//                .getLoaded();
//        getGeneric(clazz);
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
