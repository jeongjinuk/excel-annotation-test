package org.example.resource;

import lombok.Getter;
import org.example.ExcelColumn;
import org.example.excel.formula.Formula;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Getter
public final class ExcelResource {
    private final Map<Field, ExcelColumn> fieldResource;
    private final List<? extends Formula> formulaResource;
    private final Map<String, Integer> fieldNameWithColumnIndexResource;
    private final ExcelStyleResource excelStyleResource;

    public ExcelResource(Map<Field, ExcelColumn> fieldResource, List<? extends Formula> formulaResource, Map<String, Integer> fieldNameWithColumnIndexResource, ExcelStyleResource excelStyleResource) {
        this.fieldResource = fieldResource;
        this.formulaResource = formulaResource;
        this.fieldNameWithColumnIndexResource = fieldNameWithColumnIndexResource;
        this.excelStyleResource = excelStyleResource;
    }
}
