package org.celper.core;

import org.apache.poi.ss.usermodel.Row;
import org.celper.annotations.Column;
import org.celper.annotations.DefaultValue;
import org.celper.type.WorkBookType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultDataDefinedConstructTest {
    static class DTO{
        @Column(value = "field1")
        @DefaultValue("Default Value")
        private String val1;

        @Column("field2")
        protected String val2;

        @Column("field3")
        String val3;

        @Column("field4")
        public String val4;

        public DTO(String val2, String val3, String val4) {
            this.val2 = val2;
            this.val3 = val3;
            this.val4 = val4;
        }
    }
    @Test
    @DisplayName("@DefaultValue 정의 시에 null 값이면 들어가는지 테스트" +
            "숫자형은 기본값설정이 불가능 이유는 음수 값도 기본 값이 될 수 있음 그래서 문자와 관련된 것만 기본값 설정가능")
    void modelToSheet(){
        List<DTO> datas = new ArrayList<>();
        DTO dto = new DTO("val2", "val3", "val4");
        datas.add(dto);

        ExcelWorkBook excelWorkBook = new ExcelWorkBook(WorkBookType.XSSF);
        excelWorkBook.createSheet().modelToSheet(datas);

        ExcelSheet result = excelWorkBook.getSheetAt(0);
        Row header = result.getSheet().getRow(0);
        Row data = result.getSheet().getRow(1);
        assertHeader(header);
        assertData(data);
    }
    void assertHeader(Row header){
        assertEquals("field1",header.getCell(0).getStringCellValue());
        assertEquals("field2",header.getCell(1).getStringCellValue());
        assertEquals("field3",header.getCell(2).getStringCellValue());
        assertEquals("field4",header.getCell(3).getStringCellValue());
    }
    void assertData(Row data){
        assertEquals("Default Value",data.getCell(0).getStringCellValue());
        assertEquals("val2",data.getCell(1).getStringCellValue());
        assertEquals("val3",data.getCell(2).getStringCellValue());
        assertEquals("val4",data.getCell(3).getStringCellValue());
    }

}
