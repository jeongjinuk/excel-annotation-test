package org.example;

import org.example.annotation.ExcelColumn;

public class A {
    @ExcelColumn(headerName = "회사", columnIndex = 2)
    private String field1;

    private String field2;
    @ExcelColumn(headerName = "브랜드", columnIndex = 0)
    private int field3;

    public A(String field1, String field2, int field3) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }
}
