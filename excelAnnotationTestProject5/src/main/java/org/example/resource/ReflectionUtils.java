package org.example.resource;

import org.example.exception.ExcelException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public final class ReflectionUtils {
    private ReflectionUtils() {}
    //어노테이션이 붙은 필드 목록
    static List<Field> getFieldWithAnnotationList(Class<?> clazz, final Class<? extends Annotation> annotation, boolean superClasses) {
        return getAllClassesIncludingSuperClasses(clazz, superClasses).stream()
                .map(Class::getDeclaredFields)
                .flatMap(Arrays :: stream)
                .filter(field -> field.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }
    //클래스에 붙은 어노테이션 목록
    static List<? extends Annotation> getClassAnnotationList(Class<?> clazz, final Class<? extends Annotation> annotation, boolean superClasses) {
        return getAllClassesIncludingSuperClasses(clazz, superClasses).stream()
                .map(c -> c.getAnnotationsByType(annotation))
                .flatMap(Arrays :: stream)
                .collect(Collectors.toList());
    }

    static <T> T getInstance(Class<T> clazz) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            return (T) constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            throw new ExcelException(e.getMessage(), e);
        }
    }
    private static List<Class<?>> getAllClassesIncludingSuperClasses(Class<?> clazz, boolean superClasses) {
        List<Class<?>> classes = new ArrayList<>();
        while (clazz != null) {
            classes.add(clazz);
            clazz = superClasses ? clazz.getSuperclass() : null;
        }
        return classes;
    }
}
