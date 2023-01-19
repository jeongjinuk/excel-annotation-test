package org.example.templates.formula;

import org.example.templates.SheetAdapter;

import java.util.List;
import java.util.Map;
public interface FormulaTemplate {
    default void renderFormula(SheetAdapter sheet, Map<String, Integer> fieldNameWithColumnIndexMap, int criteriaRowIndex, List<?> data){
        validate(sheet, fieldNameWithColumnIndexMap, criteriaRowIndex, data);
        render(sheet, fieldNameWithColumnIndexMap, criteriaRowIndex, data);
    }
    default void validate(SheetAdapter sheet, Map<String, Integer> fieldNameWithColumnIndexMap, int criteriaRowIndex, List<?> data){};
    void render(SheetAdapter sheet, Map<String, Integer> fieldNameWithColumnIndexMap, int criteriaRowIndex, List<?> data);
}