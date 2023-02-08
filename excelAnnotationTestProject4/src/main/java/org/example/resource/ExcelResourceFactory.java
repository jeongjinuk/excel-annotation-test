package org.example.resource;

import org.apache.poi.ss.usermodel.Workbook;
import org.example.ExcelColumn;
import org.example.ExcelFormula;
import org.example.ExcelStyle;
import org.example.excel.formula.Formula;
import org.example.exception.NotFoundExcelColumnAnnotationException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public final class ExcelResourceFactory {
    private ExcelResourceFactory() {
    }

    public static ExcelResource prepareExcelResource(Class<?> type, Workbook workbook) {
        Map<Field, ExcelColumn> fieldResource = prepareFieldResource(type);

        fieldResourceValidate(fieldResource, type);

        List<? extends Formula> formulaResource = prepareFormulaResource(type);

        Map<String, Integer> fieldNameWithColumnIndexResource = prepareFieldNameWithColumnIndexResource(fieldResource);

        ExcelStyleResource excelStyleResource = prepareStyleResource(type, workbook);

        return new ExcelResource(fieldResource,
                formulaResource,
                fieldNameWithColumnIndexResource,
                excelStyleResource);
    }

    private static Map<Field, ExcelColumn> prepareFieldResource(Class<?> type) {
        return ReflectionUtils.getFieldWithAnnotationList(type, ExcelColumn.class, true).stream()
                .collect(
                        toMap(
                                field -> field,
                                field -> field.getAnnotation(ExcelColumn.class),
                                (field, annotation) -> field,
                                LinkedHashMap :: new));
    }

    private static List<? extends Formula> prepareFormulaResource(Class<?> type) {
        return ReflectionUtils.getClassAnnotationList(type, ExcelFormula.class, false).stream()
                .map(annotation -> ((ExcelFormula) annotation).formulaClass())
                .flatMap(Arrays :: stream)
                .map(ReflectionUtils :: getInstance)
                .collect(Collectors.toList());
    }

    private static Map<String, Integer> prepareFieldNameWithColumnIndexResource(Map<Field, ExcelColumn> fieldResource) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        return fieldResource.entrySet().stream().collect(
                toMap(
                        entry -> entry.getKey().getName(),
                        entry -> atomicInteger.getAndIncrement(),
                        (key, value) -> key,
                        LinkedHashMap :: new));
    }

    private static ExcelStyleResource prepareStyleResource(Class<?> type, Workbook workbook) {
        ExcelStyleResource excelStyleResource = new ExcelStyleResource();
        // type에 붙은 @ExcelStyle찾기
        putDefaultStyle(type, workbook, excelStyleResource);
        // @style 어노테이션이 붙은 필드목록
        putStyle(type, workbook, excelStyleResource);
        return excelStyleResource;
    }

    private static void putDefaultStyle(Class<?> type, Workbook workbook, ExcelStyleResource excelStyleResource) {
        ReflectionUtils.getClassAnnotationList(type, ExcelStyle.class, false).stream()
                .map(annotation -> (ExcelStyle) annotation)
                .findAny()
                .ifPresent(excelStyle ->
                        ExcelStyleResourceFactory.put(workbook :: createCellStyle, excelStyleResource, excelStyle));
    }

    private static void putStyle(Class<?> type, Workbook workbook, ExcelStyleResource excelStyleResource) {
        ReflectionUtils.getFieldWithAnnotationList(type, ExcelStyle.class, false).stream()
                .forEach(field ->
                        ExcelStyleResourceFactory.put(
                                workbook :: createCellStyle,
                                excelStyleResource,
                                field.getDeclaredAnnotation(ExcelStyle.class),
                                field.getName()));
    }

    private static void fieldResourceValidate(Map<Field, ExcelColumn> resource, Class<?> type) {
        if (resource.isEmpty()) {
            throw new NotFoundExcelColumnAnnotationException(String.format("%s has not @ExcelColumn at all", type));
        }
    }
}
