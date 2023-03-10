package org.example.resource;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.ExcelColumn;
import org.example.dto.TestDTO;
import org.example.excel.formula.Formula;
import org.example.formula.Counter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ExcelResourceFactoryTest {
    private List<String> fieldList = Arrays.asList("name","address","age","is");
    private String notExistFieldName = "email";
    private Counter counter = new Counter();

    @Test
    @DisplayName("리소스 팩토리에서 만드는 엑셀리소스가 정상적인지 확인하는 테스트, style은 optional을 반환하기 때문에 검사할게 따로없다.")
    void prepareExcelResource() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        ExcelResource excelResource = ExcelResourceFactory.prepareExcelResource(TestDTO.class, workbook);
        assertThat(excelResource);
    }

    void assertThat(ExcelResource excelResource){
        Map<Field, ExcelColumn> fieldResource = excelResource.getFieldResource(); // 필드가 존재하는지 검사
        Formula actualCounter = excelResource.getFormulaResource().get(0); // counter가 존재하는지만 검사하면됨
        Map<String, Integer> columnIndexMap = excelResource.getFieldNameWithColumnIndexResource(); // 컬럼인덱스가 존재하기만 하면됨
        List<String> actualFieldList = fieldResource.keySet().stream().map(Field :: getName).collect(Collectors.toList());

        assertEquals(fieldList, actualFieldList);
        assertFalse(actualFieldList.contains(notExistFieldName));
        assertEquals(1,excelResource.getFormulaResource().size());
        assertEquals(counter.getClass(), actualCounter.getClass());
        fieldList.forEach(s -> assertTrue(columnIndexMap.containsKey(s)));
        assertFalse(columnIndexMap.containsKey(notExistFieldName));
    }
}