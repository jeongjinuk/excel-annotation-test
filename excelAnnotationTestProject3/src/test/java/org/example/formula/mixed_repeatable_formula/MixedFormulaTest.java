package org.example.formula.mixed_repeatable_formula;

import org.example.support.TestSupport;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MixedFormulaTest {

    @Test
    void mixedFormulaTest(){
        List<TestDto> testDtos = List.of(
                new TestDto("엔젤인어스", 6000),
                new TestDto("파스쿠치", 5000),
                new TestDto("이디야커피", 4000),
                new TestDto("스타벅스", 3000)
        );
        TestExcelFile testExcelFile = new TestExcelFile(testDtos, TestDto.class);
        TestSupport.workBookOutput(testExcelFile.getWorkbook());
    }
}
