package org.example.formula.mixed_repeatable_formula;


import lombok.AllArgsConstructor;
import org.example.ExcelColumn;
import org.example.Formula;

@AllArgsConstructor
@Formula(expression =  SumFormula.class)
@Formula(expression = TexFormula.class)
public class TestDto {
    @ExcelColumn(headerName = "이름", columnIndex = 0)
    private String name;
    @ExcelColumn(headerName = "가격", columnIndex = 1)
    private int price;

}
