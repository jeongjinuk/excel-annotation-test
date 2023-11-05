package org.celper.core;

import org.celper.annotations.Column;
import org.support.TestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImportNameOptionTest {
    static class ImportNameOptionDTO {
        @Column(value = "이름", importNameOptions = {"성명"})
        private String name;

        public ImportNameOptionDTO() {
        }

        public ImportNameOptionDTO(String name) {
            this.name = name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "DTO{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    @Test
    @DisplayName("업로드시에 이름이 다른 경우 찾아줌" +
            "현실적으로 두가지 이상의 이름이 들어가면 제일 앞서 나온 값을 기준으로 찾아줌")
    void modelToSheet(){
        List<ImportNameOptionDTO> datas = new ArrayList<>();
        ImportNameOptionDTO dto = new ImportNameOptionDTO("홍길동");
        datas.add(dto);

        ExcelWorkBook excelWorkBook = new ExcelWorkBook(TestSupport.workBookInput("Import-Name-Student.xlsx"));
        ExcelSheet result = excelWorkBook.getSheetAt(0);
        List<ImportNameOptionDTO> dtos = result.sheetToModel(ImportNameOptionDTO.class);
        assertEquals(dto.toString(), dtos.get(0).toString());
    }
}
