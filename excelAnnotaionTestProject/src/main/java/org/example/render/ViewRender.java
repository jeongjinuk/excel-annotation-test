package org.example.render;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.example.util.HeaderColumnInformation;

import java.util.List;

public abstract class ViewRender<T> {

    protected abstract void renderHeader(Sheet sheet, Row row);
    protected abstract void renderBody(Row row, T dto);

    protected void renderCell(Row row, HeaderColumnInformation headerColumnInformation, String value){
        Cell cell = row.createCell(headerColumnInformation.getColumnIndex());
        cell.setCellValue(value);
    }

    protected void renderCell(Row row, HeaderColumnInformation headerColumnInformation, int value){
        Cell cell = row.createCell(headerColumnInformation.getColumnIndex());
        cell.setCellValue(value);
    }

    protected void renderCell(Row row, HeaderColumnInformation headerColumnInformation, double value){
        Cell cell = row.createCell(headerColumnInformation.getColumnIndex());
        cell.setCellValue(value);
    }

    public void render(Workbook workbook, List<T> t){
        Sheet sheet = workbook.createSheet();
        int rowIndex = 0;
        renderHeader(sheet, sheet.createRow(rowIndex++));
        for (T dto : t) {
            Row row = sheet.createRow(rowIndex++);
            renderBody(row, dto);
        }
    }


}

