package org.example.formula;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormulaTest {

    @Test
    @DisplayName("sum, average 수식 동작여부 테스트 (수식이 정상적으로 세팅되었는지 파악하면 된다)")
    void formulaTest() {
        List<TestDto> rowTestDtos = List.of(
                new TestDto("1등급", 10000),
                new TestDto("5등급", 3000),
                new TestDto("3등급", 500),
                new TestDto("3등급", 20000),
                new TestDto("2등급", 15000),
                new TestDto("5등급", 12400),
                new TestDto("1등급", 10200)
                );

        SumTestExcelFile<TestDto> rowTestDtoSXXFSExcelFile = new SumTestExcelFile<>(rowTestDtos,TestDto.class);
        Workbook workbook = rowTestDtoSXXFSExcelFile.getWorkbook();
        assertFormula(workbook);
    }

    void assertFormula(Workbook workbook){
        String sumFormula = workbook.getSheetAt(0).getRow(8).getCell(1).getCellFormula();
        String averageFormula = workbook.getSheetAt(0).getRow(8).getCell(2).getCellFormula();

        String sumFormulaActual = "=sum(C2:C8)";
        String averageFormulaActual = "=AVERAGE(C2:C8)";

        assertEquals(sumFormula, sumFormulaActual);
        assertEquals(averageFormula, averageFormulaActual);
    }


}
