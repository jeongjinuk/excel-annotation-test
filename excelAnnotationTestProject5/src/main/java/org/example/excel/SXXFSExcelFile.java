package org.example.excel;

import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.List;

public abstract class SXXFSExcelFile<T> extends ExcelFile<T> {

    public SXXFSExcelFile(List<T> data, Class<?> clazz) {
        super(data, clazz, new SXSSFWorkbook());
    }
    @Override
    protected void validate(List<T> data) {
        SpreadsheetVersion excel2007 = SpreadsheetVersion.EXCEL2007;
        if (excel2007.getMaxRows() < data.size()){
            throw  new IllegalArgumentException(String.format("%s Excel does not support over %s rows", excel2007, excel2007.getMaxRows()));
        }
    }
}
