package org.celper.core;

import org.celper.annotations.Column;
import org.celper.exception.DataListEmptyException;
import org.celper.exception.NoSuchFieldException;
import org.celper.type.WorkBookType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


class ExcelSheetExceptionTest {
    static class EmptyClass1 {
        @Column("empty")
        private int emptyField;
    }
    static class EmptyClass2 {
        @Column("empty")
        private int emptyField;
    }


    @Test
    @DisplayName("model to sheet 빈 리스트 넣을때 - DataListEmptyException")
    void modelToSheet() {
        ExcelWorkBook excelWorkBook = new ExcelWorkBook(WorkBookType.XSSF);
        ExcelSheet sheet = excelWorkBook.createSheet();
        List<EmptyClass1> emptyClassList = null;
        Assertions.assertThrows(DataListEmptyException.class, () -> sheet.modelToSheet(emptyClassList));
    }

    @Test
    @DisplayName("multi model to sheet 빈 리스트 넣을때 - DataListEmptyException")
    void multiModelToSheet() {
        ExcelWorkBook excelWorkBook = new ExcelWorkBook(WorkBookType.XSSF);
        ExcelSheet sheet = excelWorkBook.createSheet();
        List<EmptyClass1> emptyClassList1 = new ArrayList<>();
        List<EmptyClass2> emptyClassList2 = null;
        Assertions.assertThrows(DataListEmptyException.class, () -> sheet.multiModelToSheet(emptyClassList1, emptyClassList2));
    }

    @Test
    @DisplayName("sheet to model 매칭되는 sheet의 컬럼이 없을때 - NoSuchFieldException")
    void sheetToModel(){
        ExcelWorkBook excelWorkBook = new ExcelWorkBook(WorkBookType.XSSF);
        ExcelSheet sheet = excelWorkBook.createSheet();
        Assertions.assertThrows(NoSuchFieldException.class, () -> sheet.sheetToModel(EmptyClass1.class));
    }

}