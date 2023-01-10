package org.example.resource;

import org.example.exception.InvalidExcelFormulaClassException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public final class ReflectionUtils {
    private ReflectionUtils() {}
    static List<Field> findAllIncludingAnnotationFields(Class<?> clazz, final Class<? extends Annotation> annotation) {
        return findAllIncludingSuperClasses(clazz).stream()
                .map(c -> c.getDeclaredFields())
                .flatMap(Arrays :: stream)
                .filter(field -> field.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }
    static List<? extends Annotation> findAllClassAnnotations(Class<?> clazz, final Class<? extends Annotation> annotation) {
        return findAllIncludingSuperClasses(clazz).stream()
                .map(c -> c.getAnnotationsByType(annotation))
                .flatMap(Arrays :: stream)
                .collect(Collectors.toList());
    }
    static <T> T getClass(Class<T> clazz) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            return (T) constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            throw new InvalidExcelFormulaClassException(e.getMessage(), e);
        }
    }
    private static List<Class<?>> findAllIncludingSuperClasses(Class<?> clazz) {
        List<Class<?>> classes = new ArrayList<>();
        while (clazz != null) {
            classes.add(clazz);
            clazz = clazz.getSuperclass();
        }
        return classes;
    }
}
