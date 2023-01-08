package org.example.resource;

import org.example.FormulaTemplate;
import org.example.ExcelColumn;
import org.example.Formula;
import org.example.exception.DuplicateColumnIndexException;
import org.example.exception.NegativeColumnIndexPropertyException;
import org.example.exception.NotFoundExcelColumnAnnotationException;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class ExcelResourceFactory {
    private ExcelResourceFactory() {}
    public static ExcelResource prepareExcelResource(Class<?> type) {
        Map<Field, ExcelColumn> fieldResource = prepareFieldResource(type);
        fieldResourceValidation(fieldResource, type);

        List<? extends FormulaTemplate<?>> formulaResource = prepareFormulaResource(type);

        Map<String, Integer> fieldNameWithColumnIndexResource = prepareFieldNameWithColumnIndexResource(fieldResource);
        return new ExcelResource(fieldResource, formulaResource, fieldNameWithColumnIndexResource);
    }
    private static Map<Field, ExcelColumn> prepareFieldResource(Class<?> type) {
        return ReflectionUtils.findAllIncludingAnnotationFields(type, ExcelColumn.class).stream()
                .collect(
                        Collectors.toMap(
                                field -> field,
                                field -> field.getAnnotation(ExcelColumn.class),
                                (field, annotation) -> field,
                                LinkedHashMap :: new));
    }
    private static List<? extends FormulaTemplate<?>>  prepareFormulaResource(Class<?> type) {
        return ReflectionUtils.findAllClassAnnotations(type, Formula.class).stream()
                .map(annotation -> ((Formula) annotation))
                .map(formula -> ReflectionUtils.getClass(formula.expression()))
                .collect(Collectors.toList());
    }
    private static Map<String, Integer> prepareFieldNameWithColumnIndexResource(Map<Field, ExcelColumn> fieldResource) {
        return fieldResource.entrySet().stream().collect(Collectors.toMap(
                entry -> entry.getKey().getName(),
                entry -> entry.getValue().columnIndex(),
                (key, value) -> key,
                LinkedHashMap::new));
    }
    private static void fieldResourceValidation(Map<Field, ExcelColumn> resource, Class<?> type) {
        if (resource.isEmpty()) {
            throw new NotFoundExcelColumnAnnotationException(String.format("%s has not @ExcelColumn at all", type));
        }

        List<Integer> collect = resource.values().stream()
                .map(ExcelColumn :: columnIndex)
                .collect(Collectors.toList());

        long count = collect.stream().distinct().count();

        if (count != resource.size()) {
            throw new DuplicateColumnIndexException(String.format("%s has duplicate column index properties", type));
        }

        int min = collect.stream().min(Integer :: min).get();
        if (min < 0) {
            throw new NegativeColumnIndexPropertyException(String.format("%s has Negative column index property", type));
        }
    }
}
