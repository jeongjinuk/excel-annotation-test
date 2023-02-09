package org.example.workbook;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.example.dto.TestDTO;
import org.example.style.DefaultBodyStyle;
import org.example.style.DefaultHeaderStyle;
import org.example.style.custom_field_style.DoubleBorderLine;
import org.example.style.custom_field_style.IndigoBackgroundColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkBookTest {
    private List<TestDTO> list;

    @BeforeEach
    void init(){
        String email = "@test.com";
        this.list = IntStream.rangeClosed(0,50)
                .mapToObj(operand ->
                        TestDTO.builder()
                                .name(String.valueOf(operand))
                                .address(String.format("%s, %s%s - %s", operand,operand,operand,operand))
                                .email(operand + email)
                                .age(operand)
                                .is(operand%2 == 1)
                                .build()
                ).collect(Collectors.toList());
    }
    @Test
    @DisplayName("엑셀 DTO 설정 정상동작 여부 확인 테스트")
    void SXXFSExcelFileTest(){
        MySXXFS<TestDTO> mySXXFS = new MySXXFS<>(list, TestDTO.class);
        Workbook workbook = mySXXFS.getWorkbook();
        assertThat(workbook);
    }

    void assertThat(Workbook workbook){
        assertStyle(workbook);
        assertFormula(workbook);
    }
    @DisplayName("formula 정상 동작 테스트, 수식에 countif(range,'*true') 수식이 정상적으로 들어가있는지 확인")
    void assertFormula(Workbook workbook){
        Sheet sheet = workbook.getSheetAt(0);
        String counterFormula = "COUNTIF(D2:D51,\"*true\")";
        String cellFormula = sheet.getRow(list.size()).getCell(3).getCellFormula();
        assertEquals(counterFormula, cellFormula);
    }
    @DisplayName("DTO에 설정된 style이 정상적으로 동작하는지 확인 : 확인 필드 목록 [name, address, is]")
    void assertStyle(Workbook workbook){
        Sheet sheet = workbook.getSheetAt(0);
        // default
        CellStyle headerName = sheet.getRow(0).getCell(0).getCellStyle();
        CellStyle bodyName = sheet.getRow(1).getCell(0).getCellStyle();
        CellStyle headerAge = sheet.getRow(0).getCell(2).getCellStyle();
        CellStyle bodyAge = sheet.getRow(1).getCell(2).getCellStyle();

        //each
        CellStyle headerAddress = sheet.getRow(0).getCell(1).getCellStyle();
        CellStyle headerIs = sheet.getRow(0).getCell(3).getCellStyle();
        CellStyle bodyIs = sheet.getRow(1).getCell(3).getCellStyle();

        // style
        CellStyle defaultHeaderStyle = workbook.createCellStyle();
        CellStyle defaultBodyStyle = workbook.createCellStyle();
        CellStyle doubleBorderStyle = workbook.createCellStyle();
        CellStyle indigoBackgroundStyle = workbook.createCellStyle();

        new DefaultHeaderStyle().setStyle(defaultHeaderStyle);
        new DefaultBodyStyle().setStyle(defaultBodyStyle);
        new DefaultHeaderStyle().setStyle(defaultHeaderStyle);
        new DefaultBodyStyle().setStyle(defaultBodyStyle);
        new DoubleBorderLine().setStyle(doubleBorderStyle);
        new IndigoBackgroundColor().setStyle(indigoBackgroundStyle);

        // assert
        // default [name, age] field
        assertEquals(defaultHeaderStyle, headerName);
        assertEquals(defaultHeaderStyle, headerAge);
        assertEquals(defaultBodyStyle, bodyName);
        assertEquals(defaultBodyStyle, bodyAge);
        //each [address, is] field
        assertEquals(doubleBorderStyle, headerAddress);
        assertEquals(doubleBorderStyle, headerIs);
        assertEquals(indigoBackgroundStyle, bodyIs);
    }
}
