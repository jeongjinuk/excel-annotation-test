package org.example.formula.mixed_repeatable_formula;

import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.example.ExcelColumn;
import org.example.Formula;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MixedFormulaTest {

    @DisplayName("만약 price에 대한 행방향 sum, 열방향 tex를 같이 표현해야하는 수식이 존재하면 필요한 DTO")
    @AllArgsConstructor
    @Formula(expression = SumFormula.class)
    @Formula(expression = TexFormula.class)
    public class MixedFormulas {
        @ExcelColumn(headerName = "이름", columnIndex = 0)
        private String name;
        @ExcelColumn(headerName = "가격", columnIndex = 1)
        private int price;
    }

    @Test
    @DisplayName("행방향, 열방향 수식이 함께 존재할 경우 테스트")
    void mixedFormulaTest() {
        List<MixedFormulas> mixedFormulasDtos = List.of(
                new MixedFormulas("엔젤인어스", 6000),
                new MixedFormulas("파스쿠치", 5000),
                new MixedFormulas("이디야커피", 4000),
                new MixedFormulas("스타벅스", 3000)
        );
        TestExcelFile testExcelFile = new TestExcelFile(mixedFormulasDtos, MixedFormulas.class);
        Workbook workbook = testExcelFile.getWorkbook();
        assertMixed(workbook);
    }

    void assertMixed(Workbook workbook) {
        Sheet sheetAt = workbook.getSheetAt(0);
        String texFormat = "=%s*1.1";
        String sumFormat = "=sum(B2:B5)";
        for (int i = 1; i < 5; i++) {
            Cell result = sheetAt.getRow(i).getCell(2);
            String cellFormula = result.getCellFormula();
            assertEquals(String.format(texFormat, sheetAt.getRow(i).getCell(1).getAddress()), cellFormula);
        }
        String cellFormula = sheetAt.getRow(5).getCell(1).getCellFormula();
        assertEquals(sumFormat, cellFormula);

    }

    @DisplayName("같은 price 필드에 대한 sum 수식이 존재하는데 DTO가 다를 경우 (A class, B class)")
    @Formula(expression = SumFormula.class)
    @AllArgsConstructor
    class A {
        @ExcelColumn(columnIndex = 0, headerName = "이름")
        private String name;
        @ExcelColumn(columnIndex = 1, headerName = "가격")
        private int price;
        @ExcelColumn(columnIndex = 2, headerName = "등급")
        private int grade;
    }

    @DisplayName("같은 price 필드에 대한 sum 수식이 존재하는데 DTO가 다를 경우 (A class, B class)")
    @Formula(expression = SumFormula.class)
    @AllArgsConstructor
    class B {
        @ExcelColumn(columnIndex = 0, headerName = "이름")
        private String name;
        @ExcelColumn(columnIndex = 1, headerName = "등급")
        private int grade;
        @ExcelColumn(columnIndex = 2, headerName = "가격")
        private int price;

    }

    @DisplayName("공통 A, B price 테스트 만약에 공통적으로 가격에 대한 =SUM(RANGE)를 적용해야한다. 하지만 A,B 객체를 엑셀시트로 만들때 두 객체의 '가격' 컬럼의 인덱스가 다르다면 이를 맞추기 힘들다. 이부분을 테스트하는 것")
    @Test
    void duplicateFormulaTest() {
        List<A> ADtos = List.of(
                new A("a", 6000, 1),
                new A("b", 5000, 2),
                new A("c", 4000, 3)
        );

        List<B> BDtos = List.of(
                new B("b", 2, 6000),
                new B("a", 1, 5000),
                new B("c", 3, 4000),
                new B("d", 3, 4000)

        );
        TestExcelFile aExcelFile = new TestExcelFile(ADtos, A.class);
        Workbook workbook1 = aExcelFile.getWorkbook();
        TestExcelFile bExcelFile = new TestExcelFile(BDtos, B.class);
        Workbook workbook2 = bExcelFile.getWorkbook();
        assertDuplicateFormula(workbook1, workbook2);
    }
    void assertDuplicateFormula(Workbook w1, Workbook w2) {
        String w1CellFormula = w1.getSheetAt(0).getRow(w1.getSheetAt(0).getLastRowNum()).getCell(1).getCellFormula();
        assertEquals("=SUM(B2:B4)", w1CellFormula.toUpperCase());

        String w2CellFormula = w2.getSheetAt(0).getRow(w2.getSheetAt(0).getLastRowNum()).getCell(2).getCellFormula();
        assertEquals("=SUM(C2:C5)", w2CellFormula.toUpperCase());
    }
}
