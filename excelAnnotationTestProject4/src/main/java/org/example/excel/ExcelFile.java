package org.example.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.example.excel.style.StyleLocation;
import org.example.resource.ExcelResource;
import org.example.resource.ExcelResourceFactory;

import java.util.List;

public abstract class ExcelFile<T> {
    private ExcelResource excelResource;
    private Workbook workbook;
    private ExcelSheetHelper excelSheet;

    protected ExcelFile(List<T> data, Class<?> clazz, Workbook workbook) {
        validate(data);
        this.excelResource = ExcelResourceFactory.prepareExcelResource(clazz, workbook);
        this.workbook = workbook;
        this.excelSheet = new ExcelSheetHelper(excelResource.getFieldNameWithColumnIndexResource(), excelResource.getExcelStyleResource());
        renderExcel(data);
    }

    public Workbook getWorkbook() {
        return this.workbook;
    }

    protected void validate(List<T> data) {
    }

    protected abstract void renderExcel(List<T> data);

    protected void renderHeader(Sheet sheet, int rowIndex) {
        excelSheet.setSheet(sheet);
        excelResource.getFieldResource().forEach((field, excelColumn) -> {
            Cell cell = excelSheet.getCellByFieldName(rowIndex, field.getName());
            excelSheet.setStyle(cell, field.getName(), StyleLocation.HEADER);
            excelSheet.setCellValue(cell, excelColumn.headerName());
        });
    }


    protected void renderBody(Sheet sheet, int rowIndex, T data) {
        excelSheet.setSheet(sheet);
        excelResource.getFieldResource().forEach((field, excelColumn) -> {
            Cell cell = excelSheet.getCellByFieldName(rowIndex, field.getName());
            excelSheet.setStyle(cell, field.getName(), StyleLocation.BODY);
            excelSheet.setCellValue(field, cell, data);
        });
    }


    protected void renderFormula(Sheet sheet, int criteriaRowIndex, List<T> data) {
        excelSheet.setSheet(sheet);
        excelResource.getFormulaResource()
                .forEach(formula -> formula.renderFormula(excelSheet, criteriaRowIndex, data));
    }
}
