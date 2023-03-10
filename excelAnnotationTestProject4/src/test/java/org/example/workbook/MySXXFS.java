package org.example.workbook;

import org.apache.poi.ss.usermodel.Sheet;
import org.example.excel.SXXFSExcelFile;

import java.util.List;
import java.util.stream.IntStream;

public class MySXXFS<T> extends SXXFSExcelFile<T> {

    public MySXXFS(List<T> data, Class clazz) {
        super(data, clazz);
    }

    @Override
    protected void renderExcel(List<T> data) {
        Sheet sheet = getWorkbook().createSheet();
        renderHeader(sheet,0);

        IntStream.rangeClosed(1, data.size() - 1)
                .forEach(i -> renderBody(sheet, i, data.get(i)));

        renderFormula(sheet, 1, data);
    }
}
