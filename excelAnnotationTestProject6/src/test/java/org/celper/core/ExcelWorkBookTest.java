package org.celper.core;

import org.celper.type.WorkBookType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExcelWorkBookTest {

    @Test
    @DisplayName("시트 이름으로 생성 테스트")
    void createSheetTest() {
        ExcelWorkBook excelWorkBook = new ExcelWorkBook(WorkBookType.XSSF);
        String sheetName = "테스트 1";
        ExcelSheet sheet = excelWorkBook.createSheet(sheetName);
        assertEquals(sheetName, excelWorkBook.getSheetByName(sheetName).getName());
    }
}