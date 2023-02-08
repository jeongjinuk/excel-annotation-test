package org.example.excel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.example.ExcelColumn;
import org.example.ExcelFormula;
import org.example.ExcelStyle;
import org.example.excel.formula.SumFormula;
import org.example.excel.style.DefaultBodyStyle;
import org.example.excel.style.DefaultHeaderStyle;
import org.example.excel.style.NameStyle;

@AllArgsConstructor
@Builder
@ExcelFormula(formulaClass = SumFormula.class)
@ExcelStyle(headerStyleClass = DefaultHeaderStyle.class, bodyStyleClass = DefaultBodyStyle.class)
public class TestDTO {

    @ExcelStyle(bodyStyleClass = NameStyle.class)
    @ExcelColumn(headerName = "이름")
    private String name;

    @ExcelColumn(headerName = "이메일")
    private String email;

    @ExcelColumn(headerName = "나이")
    private int age;

    @ExcelStyle(headerStyleClass = NameStyle.class)
    @ExcelColumn(headerName = "여부")
    private boolean is;
}
