package org.example.excel;

import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.example.support.TestSupport;
import org.example.annotations.ExcelColumn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SXXFSExcelFileTest {
    private List<Cafe> cafes;

    @AllArgsConstructor
    class Cafe{
        @ExcelColumn(headerName = "브랜드", columnIndex = 1)
        private String name;
        @ExcelColumn(headerName = "제품명", columnIndex = 2)
        private String productName;
        @ExcelColumn(headerName = "가격", columnIndex = 3)
        private int price;
        @ExcelColumn(headerName = "맛 등급", columnIndex = 4)
        private double grade;
    }
    static class MyExcelFile<T> extends SXXFSExcelFile<T>{
        private static final int ROW_START_INDEX = 0;
        private static int currentRowIndex = ROW_START_INDEX;

        public MyExcelFile(List<T> data, Class<?> clazz) {
            super(data, clazz);
        }
        @Override
        protected void renderExcel(List<T> data) {
            Sheet sheet = workbook.createSheet();
            renderHeader(sheet, ROW_START_INDEX);
            for (T t : data) {
                renderBody(sheet, ++currentRowIndex, t);
            }
            renderFormula(sheet,0, data);
        }

    }

    @BeforeEach
    void init(){
        cafes = List.of(
                new Cafe("엔젤인어스", "콜드브루", 6200, 1.2),
                new Cafe("파스쿠치", "콜드브루아메리카노", 5800, 2.4),
                new Cafe("이디야", "콜드브루", 5500, 3.4),
                new Cafe("할리스커피", "콜드브루", 6000, 2.0),
                new Cafe("스타벅스", "콜드브루라떼", 6300, 4)
        );
    }

    @Test
    @DisplayName("정상적으로 SXXFSWorkBook이 반환되는지 확인")
    void workBookTest(){
//        SXSSFWorkbook actual = new SXSSFWorkbook();
        SXXFSExcelFile<Cafe> sxxfsExcelFile = new MyExcelFile(cafes, Cafe.class);
        Workbook workbook = sxxfsExcelFile.getWorkbook();
        TestSupport.workBookOutput(workbook);
//        assertEquals(workbook.getClass(), actual.getClass());
    }

}