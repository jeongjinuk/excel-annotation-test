package org.celper.core;

import org.apache.poi.ss.usermodel.Row;
import org.celper.annotations.Column;
import org.celper.type.WorkBookType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriorityConstructTest {

    static class DTO{
        @Column(value = "last",priority = 10)
        private String val1;

        @Column("field2")
        protected String val2;

        @Column("field3")
        String val3;

        @Column("field4")
        public String val4;

        public DTO(String val1, String val2, String val3, String val4) {
            this.val1 = val1;
            this.val2 = val2;
            this.val3 = val3;
            this.val4 = val4;
        }
    }

    @Test
    @DisplayName("priority 정의 테스트 " +
            "[field2, field3, field4, last]" +
            "[val2, val3, val4, last]"
    )
    void modelToSheet(){
        List<DTO> datas = new ArrayList<>();
        DTO dto = new DTO("val1", "val2", "val3", "val4");
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
        assertEquals("field2",header.getCell(0).getStringCellValue());
        assertEquals("field3",header.getCell(1).getStringCellValue());
        assertEquals("field4",header.getCell(2).getStringCellValue());
        assertEquals("last",header.getCell(3).getStringCellValue());
    }
    void assertData(Row data){
        assertEquals("val2",data.getCell(0).getStringCellValue());
        assertEquals("val3",data.getCell(1).getStringCellValue());
        assertEquals("val4",data.getCell(2).getStringCellValue());
        assertEquals("val1",data.getCell(3).getStringCellValue());

    }
}
