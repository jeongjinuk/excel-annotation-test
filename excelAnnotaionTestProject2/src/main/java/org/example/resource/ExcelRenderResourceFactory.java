package org.example.resource;

import org.example.annotation.ExcelColumn;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ExcelRenderResourceFactory {
    public static ExcelRenderResource prepareRenderResource(Class<?> type){
        Map<String, ExcelColumn> headerInformations = new LinkedHashMap<>();
        Field[] declaredFields = type.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(ExcelColumn.class)){
                headerInformations.put(field.getName(), field.getAnnotation(ExcelColumn.class));
            }
        }
        return new ExcelRenderResource(headerInformations);
    }
}
