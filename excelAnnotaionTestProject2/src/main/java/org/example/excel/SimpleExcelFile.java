package org.example.excel;

import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.*;
import org.example.annotation.ExcelColumn;
import org.example.resource.ExcelRenderResource;
import org.example.resource.ExcelRenderResourceFactory;

import javax.xml.validation.Validator;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class SimpleExcelFile<T> {
    private static final int ROW_INDEX = 0;
    private Workbook workbook;
    private List<T> data;
    private Class<?> type;
    private ExcelRenderResource excelRenderResource;

    public SimpleExcelFile(Workbook workbook, List<T> data, Class<?> type) {
        validated(workbook, data);
        this.workbook = workbook;
        this.data = data;
        this.type = type;
        excelRenderResource = ExcelRenderResourceFactory.prepareRenderResource(type);
        render(this.workbook, this.data);
    }
    private void validated(Workbook workbook, List<T> data) {
        SpreadsheetVersion version = workbook.getSpreadsheetVersion();
        validatedMaxRow(version, data);
        validatedColumnRow(version, data);
    }

    private void validatedMaxRow(SpreadsheetVersion version, List<T> data) {
        int maxRows = version.getMaxRows();
        if (data.size() > maxRows) {
            throw new IllegalArgumentException(
                    String.format("this ExcelFile (Version : %s) does not support over %s rows", version, maxRows));
        }
    }

    private void validatedColumnRow(SpreadsheetVersion version, List<T> data) {
        int maxColumns = version.getMaxColumns();
        if (!data.isEmpty()) {
            T t = data.get(0);
            Field[] declaredFields = t.getClass().getDeclaredFields();
            if (declaredFields.length > maxColumns) {
                throw new IllegalArgumentException(
                        String.format("this ExcelFile (Version : %s) does not support over %s columns", version, maxColumns));
            }
        }
    }

    private void render(Workbook workbook, List<T> data) {
        Sheet sheet = workbook.createSheet();
        renderHeader(sheet);
        renderBody(sheet, data);
    }

    private void renderHeader(Sheet sheet) {
        Map<String, ExcelColumn> headerNames = excelRenderResource.getHeaderNames();
        Row row = sheet.createRow(ROW_INDEX);
        for (Map.Entry<String, ExcelColumn> entry : headerNames.entrySet()) {
            Cell cell = row.createCell(entry.getValue().columnIndex());
            String value = entry.getValue().headerName();
            setCellValue(cell, value);
        }
    }

    private void renderBody(Sheet sheet, List<T> data) {
        int rowIndex = ROW_INDEX + 1;
        Map<String, ExcelColumn> headerNames = excelRenderResource.getHeaderNames();
        for (T target : data) {
            Row row = sheet.createRow(rowIndex++);
            for (Map.Entry<String, ExcelColumn> entry : headerNames.entrySet()) {
                Cell cell = row.createCell(entry.getValue().columnIndex());
                try {
                    Field field = getField(target.getClass(), entry.getKey());
                    field.setAccessible(true);
                    Object o = field.get(target);
                    setCellValue(cell, o);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage(), e);
                }

            }
        }
    }

    private void setCellValue(Cell cell, Object value){
        if (value instanceof Number){
            Number number = (Number) value;
            cell.setCellValue(number.doubleValue());
            return;
        }
        cell.setCellValue(value == null ? "" : value.toString());
    }

    private Field getField(Class<?> clazz, String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        return field;
    }

}
