package org.example.formula.mixed_repeatable_formula;

import org.apache.poi.ss.usermodel.Sheet;
import org.example.excel.SXXFSExcelFile;

import java.util.List;

public class TestExcelFile<T> extends SXXFSExcelFile<T> {

    public TestExcelFile(List<T> data, Class<?> clazz) {
        super(data, clazz);
    }
    @Override
    protected void renderExcel(List<T> data) {
        Sheet sheet = workbook.createSheet();
        renderHeader(sheet, 0);
        int currentRowIndex = 1;
        for (T t : data) {
            renderBody(sheet, currentRowIndex++, t);
        }
        renderFormula(sheet, 1, data);
    }
}
