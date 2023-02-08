package org.example.excel.formula;

import org.example.excel.sheet.ExcelSheet;

import java.util.List;
public interface Formula {
    default void renderFormula(ExcelSheet sheet, int criteriaRowIndex, List<?> data){
        validate(sheet, criteriaRowIndex, data);
        render(sheet, criteriaRowIndex, data);
    }
    void validate(ExcelSheet sheet, int criteriaRowIndex, List<?> data);
    void render(ExcelSheet sheet, int criteriaRowIndex, List<?> data);
}