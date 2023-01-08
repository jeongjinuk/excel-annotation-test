package org.example.resource;

import lombok.AllArgsConstructor;
import org.example.ExcelColumn;
import org.example.exception.DuplicateColumnIndexException;
import org.example.exception.NegativeColumnIndexPropertyException;
import org.example.exception.NotFoundExcelColumnAnnotationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExcelResourceFactoryTest {
    class NegativeColumnDto{
        @ExcelColumn(headerName = "field0", columnIndex = -1)
        private int field0;
    }
    class DuplicateColumnDto{
        @ExcelColumn(headerName = "field4", columnIndex = 4)
        private int field3;
        @ExcelColumn(headerName = "field4", columnIndex = 4)
        private int field4;
    }
    class EmptyFormulaAnnotationDto{
        @ExcelColumn(headerName = "", columnIndex = 1)
        private String field1;
    }

    @Test
    @DisplayName("@Formula 어노테이션이 없는 경우 빈 컬렉션 반환")
    void emptyFormulaAnnotation() {
        ExcelResource excelResource = ExcelResourceFactory.prepareExcelResource(EmptyFormulaAnnotationDto.class);
        assertTrue(excelResource.getFormulaResource().isEmpty());
    }

    @Test
    @DisplayName("@ExcelColumn 어노테이션이 없는 경우 noExcelColumnException 발생")
    void noExcelColumnException() {
        assertThrows(NotFoundExcelColumnAnnotationException.class, () ->ExcelResourceFactory.prepareExcelResource(ReflectionUtils.class));
    }

    @Test
    @DisplayName("중복된 열 인덱스를 넣을 경우 duplicateColumnIndexException 발생")
    void duplicateColumnIndexException() {
        assertThrows(DuplicateColumnIndexException.class, () -> ExcelResourceFactory.prepareExcelResource(DuplicateColumnDto.class));
    }

    @Test
    @DisplayName("음수를 열 인덱스로 넣을 경우 negativeIndexException 발생 ")
    void negativeIndexException() {
        assertThrows(NegativeColumnIndexPropertyException.class, () -> ExcelResourceFactory.prepareExcelResource(NegativeColumnDto.class));
    }

}