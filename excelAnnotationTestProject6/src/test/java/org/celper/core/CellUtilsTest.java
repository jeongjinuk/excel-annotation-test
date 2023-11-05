package org.celper.core;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.celper.type.WorkBookType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CellUtilsTest {
    private Sheet sheet;

    @BeforeEach
    void setExcelWorkBook(){
        this.sheet = new ExcelWorkBook(WorkBookType.XSSF).createSheet().getSheet();
        Row row = sheet.createRow(0);
        row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue("1");
        row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue("2");
    }
    @Test
    @DisplayName("row 생성 테스트")
    void createRow() {
        ExcelSheet sheet = new ExcelWorkBook(WorkBookType.XSSF).createSheet();
        int cellSize = 10;
        Row row = CellUtils.createRow(sheet.getSheet(), 0, cellSize);
        assertEquals(cellSize, row.getLastCellNum());
    }

    @Test
    @DisplayName("cell 가져오기 테스트")
    void getValue() {
        assertEquals("1", CellUtils.getValue(sheet,0,0));
        assertEquals("2", CellUtils.getValue(sheet,0,1));
    }

    @Test
    @DisplayName("cell set 테스트")
    void setValue() {
        String cellValue = "val";
        Row row = CellUtils.createRow(sheet, 1, 1);
        Cell cell = row.getCell(0);
        CellUtils.setValue(cell,cellValue);

        Object value = CellUtils.getValue(sheet, 1, 0);
        assertEquals(cellValue, value);
    }

}