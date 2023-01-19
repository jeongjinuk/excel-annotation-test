package org.example.resource;

import org.example.ExcelColumn;
import org.example.Formula;
import org.example.exception.DuplicateColumnIndexException;
import org.example.exception.NegativeColumnIndexPropertyException;
import org.example.exception.NotFoundExcelColumnAnnotationException;
import org.example.templates.formula.FormulaTemplate;
import org.example.templates.style.StyleTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public final class ExcelResourceFactory {
    private ExcelResourceFactory() {
    }
    public static ExcelResource prepareExcelResource(Class<?> type) {
        Map<Field, ExcelColumn> fieldResource = prepareFieldResource(type);
        fieldResourceValidate(fieldResource, type);

        Map<StyleLocation, ? extends StyleTemplate> styleResource= prepareDefaultStyleResource(type);

        List<? extends FormulaTemplate> formulaResource = prepareFormulaResource(type);

        Map<String, Integer> fieldNameWithColumnIndexResource = prepareFieldNameWithColumnIndexResource(fieldResource);
        return new ExcelResource(fieldResource, styleResource, formulaResource, fieldNameWithColumnIndexResource);
    }
    private static Map<Field, ExcelColumn> prepareFieldResource(Class<?> type) {
        return ReflectionUtils.findAllIncludingAnnotationFields(type, ExcelColumn.class).stream()
                .collect(
                        toMap(
                                field -> field,
                                field -> field.getAnnotation(ExcelColumn.class),
                                (field, annotation) -> field,
                                LinkedHashMap :: new));
    }
    private static List<? extends FormulaTemplate> prepareFormulaResource(Class<?> type) {
        return ReflectionUtils.findAllClassAnnotations(type, Formula.class).stream()
                .map(annotation -> ((Formula) annotation).expression())
                .flatMap(Arrays :: stream)
                .map(formulaTemplate -> ReflectionUtils.getClass(formulaTemplate))
                .collect(Collectors.toList());
    }
    private static Map<String, Integer> prepareFieldNameWithColumnIndexResource(Map<Field, ExcelColumn> fieldResource) {
        return fieldResource.entrySet().stream().collect(
                toMap(
                        entry -> entry.getKey().getName(),
                        entry -> entry.getValue().columnIndex(),
                        (key, value) -> key,
                        LinkedHashMap :: new));
    }
    private static Map<StyleLocation, ? extends StyleTemplate> prepareDefaultStyleResource(Class<?> type) {
        // 스타일 map 만들어주기
        Map<StyleLocation, StyleTemplate> map = new LinkedHashMap<>();
        Arrays.stream(StyleLocation.values())
                .forEach(styleLocation -> stylePut(styleLocation, type, map));
        return map;
    }
    private static void stylePut(StyleLocation styleLocation, Class<?> targetClass, Map<StyleLocation, StyleTemplate> map){
        // 스타일을 클래스에 찾아서 검사 및 put 시켜주는 작업 과 인스턴스 제작 단편화
        // 인스턴만들기
        // put 객체를 따로 만들어야할듯
        // 스타일이 클래스에 붙을 수 있고 스타일이 어노테이션에 포함되있을 수 있음
        Optional<? extends Annotation> classAnnotation = ReflectionUtils.findClassAnnotation(targetClass, styleLocation.getType());
        if (classAnnotation.isEmpty()){
            return;
        }
        StyleTemplate instance = ReflectionUtils.getClass(styleLocation.getStyleClassType(classAnnotation.get()));
        map.put(styleLocation, instance);
    }
    private static void fieldResourceValidate(Map<Field, ExcelColumn> resource, Class<?> type) {
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

        int min = collect.stream().min(Math :: min).get();
        if (min < 0) {
            throw new NegativeColumnIndexPropertyException(String.format("%s has Negative column index property", type));
        }
    }
}
