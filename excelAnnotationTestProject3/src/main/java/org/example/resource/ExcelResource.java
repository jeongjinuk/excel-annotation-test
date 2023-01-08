package org.example.resource;

import lombok.Getter;
import org.example.FormulaTemplate;
import org.example.ExcelColumn;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Getter
public final class ExcelResource {
    private final Map<Field, ExcelColumn> fieldResource;
    private final List<? extends FormulaTemplate<?>> formulaResource;
    private final Map<String, Integer> fieldNameWithColumnIndexResource;

    public ExcelResource(Map<Field, ExcelColumn> fieldResource,
                         List<? extends FormulaTemplate<?>> formulaResource,
                         Map<String, Integer> fieldNameWithColumnIndexResource) {
        this.fieldResource = fieldResource;
        this.formulaResource = formulaResource;
        this.fieldNameWithColumnIndexResource = fieldNameWithColumnIndexResource;
    }
}
