package org.example.excel;

import org.apache.poi.ss.usermodel.*;
import org.example.support.TestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class SXXFSExcelFileTest {


    private List<TestDTO> list;

    @BeforeEach
    void init(){
        String email = "@test.com";
        this.list = IntStream.rangeClosed(0,100)
                .mapToObj(operand ->
                        TestDTO.builder()
                                .name(String.valueOf(operand))
                                .email(operand + email)
                                .age(operand)
                                .is(operand%2 == 1)
                                .build()
                ).collect(Collectors.toList());
    }


    @Test
    @DisplayName("엑셀 파일 생성 테스트")
    void SXXFSExcelFileTest(){
        MySXXFS<TestDTO> workbook = new MySXXFS<>(list, TestDTO.class);
        TestSupport.workBookOutput(workbook.getWorkbook());
    }

}