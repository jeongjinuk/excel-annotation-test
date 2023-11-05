package org.celper.core;

import org.celper.annotations.Column;
import org.celper.core.model.ClassModel;
import org.celper.core.style.SheetStyleConfigurer;
import org.celper.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * The type Class model registrator.
 */
public final class ClassModelRegistrator {
    private static final ConcurrentHashMap<Class<?>, List<ClassModel>> CLASS_MODEL_MAP = new ConcurrentHashMap<>();

    private ClassModelRegistrator() {
        throw new IllegalStateException("Registrator Class");
    }

    /**
     * Add.
     *
     * @param clazz the clazz
     */
    public static void add(Class<?> clazz) {
        CLASS_MODEL_MAP.put(clazz, List.of(createClassModels(clazz)));
    }

    /**
     * Gets or default.
     *
     * @param clazz the clazz
     * @return the or default
     */
    static List<ClassModel> getOrDefault(Class<?> clazz) {
        List<ClassModel> classModels = CLASS_MODEL_MAP.get(clazz);
        return  Objects.nonNull(classModels) ? classModels : List.of(createClassModels(clazz));
    }

    /**
     * Create class models class model [ ].
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return the class model [ ]
     */
    static <T> ClassModel[] createClassModels(Class<T> clazz) {
        Function<Field, ClassModel> classModelSupplier = ClassModel::new;
        SheetStyleConfigurer sheetStyle = ClassModel.createSheetStyle(clazz);
        AtomicInteger atomicInteger = new AtomicInteger(0);

        return ReflectionUtils.getDeclaredFields(clazz)
                .stream()
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(classModelSupplier)
                .map(ClassModel :: setColumn)
                .map(ClassModel :: setDefaultValue)
                .map(ClassModel :: setFieldName)
                .map(classModel -> classModel.setPriority(atomicInteger.getAndIncrement()))
                .map(classModel -> classModel.setSheetStyleConfigurer(sheetStyle))
                .map(ClassModel :: setCellStyleConfigurer)
                .map(ClassModel :: setCellFormat)
                .toArray(ClassModel[] :: new);
    }
}
