package org.example.formula;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.ExcelColumn;
import org.example.Formula;

@AllArgsConstructor
@Getter
@Formula(expression = AverageFormula.class)
@Formula(expression = SumFormula.class)
public class TestDto {
    @ExcelColumn(headerName = "등급", columnIndex = 1)
    private String field1;
    @ExcelColumn(headerName = "가격", columnIndex = 2)
    private int field2;
}