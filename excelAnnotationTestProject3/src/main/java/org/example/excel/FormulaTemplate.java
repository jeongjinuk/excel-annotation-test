package org.example.excel;

import java.util.List;
import java.util.Map;
public interface FormulaTemplate<T> {
    default void renderFormula(SheetAdapter sheet, Map<String, Integer> fieldNameWithColumnIndexMap, int criteriaRowIndex, List<T> data){
        validate(sheet, fieldNameWithColumnIndexMap, criteriaRowIndex, data);
        render(sheet, fieldNameWithColumnIndexMap, criteriaRowIndex, data);
    }
    default void validate(SheetAdapter sheet, Map<String, Integer> fieldNameWithColumnIndexMap, int criteriaRowIndex, List<T> data){};
    void render(SheetAdapter sheet, Map<String, Integer> fieldNameWithColumnIndexMap, int criteriaRowIndex, List<T> data);
}

