package org.example.excel;

import org.apache.poi.ss.usermodel.*;
import org.example.ExcelColumn;
import org.example.exception.ExcelException;
import org.example.resource.StyleLocation;
import org.example.templates.SheetAdapter;
import org.example.resource.ExcelResource;
import org.example.resource.ExcelResourceFactory;
import org.example.templates.style.StyleTemplate;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class ExcelFileProvider<T> {
    private ExcelResource excelResource;
    protected Workbook workbook;

    protected ExcelFileProvider(List<T> data, Class<?> clazz, Workbook workbook) {
        validate(data);
        this.excelResource = ExcelResourceFactory.prepareExcelResource(clazz);
        this.workbook = workbook;
        renderExcel(data);
    }

    public Workbook getWorkbook() {
        return this.workbook;
    }

    protected void validate(List<T> data) {
    }

    protected abstract void renderExcel(List<T> data);

    protected void renderHeader(Sheet sheet, int rowIndex) {
        Row row = sheet.createRow(rowIndex);
        Set<Map.Entry<Field, ExcelColumn>> entries = excelResource.getFieldResource().entrySet();
//        CellStyle style = excelResource.getStyleResource()
//                .get(StyleLocation.HEADER)
//                .apply(workbook);
        for (Map.Entry<Field, ExcelColumn> entry : entries) {
            ExcelColumn value = entry.getValue();
            Cell cell = row.createCell(value.columnIndex());
//            cell.setCellStyle(style);
            renderCellValue(cell, value.headerName());
        }
    }

    protected void renderBody(Sheet sheet, int rowIndex, T data) {
        Set<Map.Entry<Field, ExcelColumn>> entries = excelResource.getFieldResource().entrySet();
        Row row = sheet.createRow(rowIndex);
//        CellStyle style = excelResource.getStyleResource()
//                .get(StyleLocation.BODY)
//                .apply(workbook);
        for (Map.Entry<Field, ExcelColumn> entry : entries) {
            Field field = entry.getKey();
            Cell cell = row.createCell(entry.getValue().columnIndex());
//            cell.setCellStyle(style);
            renderCellValue(field, cell, data);
        }
    }

    protected void renderFormula(Sheet sheet, int criteriaRowIndex, List<T> data) {
        Map<String, Integer> fieldNameWithColumnIndexResource = excelResource.getFieldNameWithColumnIndexResource();
        SheetAdapter sheetAdapter = new SheetAdapter(sheet);
        excelResource.getFormulaResource().stream()
                .forEach(formula -> formula.renderFormula(sheetAdapter, fieldNameWithColumnIndexResource, criteriaRowIndex, data));
    }

    private void renderCellValue(Field field, Cell cell, T data) {
        try {
            field.setAccessible(true);
            Object o = field.get(data);
            renderCellValue(cell, o);
        } catch (IllegalAccessException e) {
            throw new ExcelException(e.getMessage(), e);
        }
    }

    private void renderCellValue(Cell cell, Object o) {
        if (o instanceof Number) {
            Number number = (Number) o;
            cell.setCellValue(number.doubleValue());
            return;
        }
        cell.setCellValue(o == null ? "" : o.toString());
    }
}
