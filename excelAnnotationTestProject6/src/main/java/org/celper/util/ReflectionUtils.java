package org.celper.util;

import org.celper.exception.ExcelException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Reflection utils.
 */
public final class ReflectionUtils {

    private ReflectionUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Gets declared fields.
     *
     * @param type the type
     * @return the declared fields
     */
    public static List<Field> getDeclaredFields(Class<?> type) {
        return Arrays.stream(type.getDeclaredFields()).collect(Collectors.toList());
    }

    /**
     * Gets instance.
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return the instance
     */
    public static <T> T getInstance(Class<T> clazz) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            return (T) constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            throw new ExcelException(e.getMessage(), e);
        }
    }
}
