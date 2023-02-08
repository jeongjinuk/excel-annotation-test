package org.example.excel.formula;

import org.apache.poi.ss.usermodel.Cell;
import org.example.excel.sheet.ExcelSheet;

import java.util.List;

public class SumFormula implements Formula {

    @Override
    public void validate(ExcelSheet sheet, int criteriaRowIndex, List<?> data) {}

    @Override
    public void render(ExcelSheet sheet, int criteriaRowIndex, List<?> data) {
        String start = sheet.getCellAddressByFieldName(criteriaRowIndex, "age");
        String end = sheet.getCellAddressByFieldName(data.size()-criteriaRowIndex, "age");
        Cell age = sheet.getCellByFieldName(data.size(), "age");
        age.setCellFormula(String.format("=sum(%s:%s)",start, end));
    }
}
