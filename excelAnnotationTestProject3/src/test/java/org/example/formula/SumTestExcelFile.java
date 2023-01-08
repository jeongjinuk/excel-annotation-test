package org.example.formula;

import org.apache.poi.ss.usermodel.Sheet;
import org.example.excel.SXXFSExcelFile;

import java.util.List;

public class SumTestExcelFile<T> extends SXXFSExcelFile<T> {
    private static final int ROW_START_INDEX = 0;
    private static int currentRowIndex = ROW_START_INDEX;

    public SumTestExcelFile(List<T> data, Class<?> clazz) {
        super(data, clazz);
    }
    @Override
    protected void renderExcel(List<T> data) {
        Sheet sheet = workbook.createSheet();
        renderHeader(sheet, ROW_START_INDEX);
        for (T t : data) {
            renderBody(sheet, ++currentRowIndex, t);
        }
        renderFormula(sheet, ++currentRowIndex);
    }
}
