package org.example.resource;

import lombok.Getter;
import org.example.templates.formula.FormulaTemplate;
import org.example.ExcelColumn;
import org.example.templates.style.StyleTemplate;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Getter
public final class ExcelResource {
    private final Map<Field, ExcelColumn> fieldResource;
    private final Map<StyleLocation, ? extends StyleTemplate> styleResource;
    private final List<? extends FormulaTemplate> formulaResource;
    private final Map<String, Integer> fieldNameWithColumnIndexResource;

    public ExcelResource(Map<Field, ExcelColumn> fieldResource,
                         Map<StyleLocation, ? extends StyleTemplate> styleResource,
                         List<? extends FormulaTemplate> formulaResource,
                         Map<String, Integer> fieldNameWithColumnIndexResource) {
        this.fieldResource = fieldResource;
        this.styleResource = styleResource;
        this.formulaResource = formulaResource;
        this.fieldNameWithColumnIndexResource = fieldNameWithColumnIndexResource;
    }

}
