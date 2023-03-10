package org.example.resource;

import lombok.Getter;
import org.example.ExcelColumn;
import org.example.excel.formula.Formula;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

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

    public Map<Field, ExcelColumn> getFieldResource() {
        return fieldResource;
    }

    public List<? extends Formula> getFormulaResource() {
        return formulaResource;
    }

    public Map<String, Integer> getFieldNameWithColumnIndexResource() {
        return fieldNameWithColumnIndexResource;
    }

    public ExcelStyleResource getExcelStyleResource() {
        return excelStyleResource;
    }
}
