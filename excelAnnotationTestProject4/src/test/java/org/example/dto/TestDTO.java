package org.example.dto;

import lombok.Builder;
import org.example.ExcelColumn;
import org.example.ExcelFormula;
import org.example.ExcelStyle;
import org.example.formula.Counter;
import org.example.style.DefaultBodyStyle;
import org.example.style.DefaultHeaderStyle;
import org.example.style.custom_field_style.DoubleBorderLine;
import org.example.style.custom_field_style.IndigoBackgroundColor;

@Builder
@ExcelFormula(formulaClass = Counter.class)
@ExcelStyle(headerStyleClass = DefaultHeaderStyle.class, bodyStyleClass = DefaultBodyStyle.class)
public class TestDTO {

    @ExcelColumn(headerName = "이름")
    private String name;

    @ExcelStyle(headerStyleClass = DoubleBorderLine.class)
    @ExcelColumn(headerName = "주소")
    private String address;

    @ExcelStyle(headerStyleClass = DoubleBorderLine.class)
    private String email;

    @ExcelColumn(headerName = "나이")
    private int age;

    @ExcelStyle(headerStyleClass = DoubleBorderLine.class, bodyStyleClass = IndigoBackgroundColor.class)
    @ExcelColumn(headerName = "여부")
    private boolean is;
}
