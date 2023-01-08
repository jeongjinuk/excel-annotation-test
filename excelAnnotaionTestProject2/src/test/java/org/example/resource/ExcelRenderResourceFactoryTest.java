package org.example.resource;

import org.example.annotation.ExcelColumn;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExcelRenderResourceFactoryTest {

    @Test
    void prepareRenderResourceTest() {
        ExcelRenderResource excelRenderResource = ExcelRenderResourceFactory.prepareRenderResource(A.class);
        Map<String, ExcelColumn> headerNames =
                excelRenderResource.getHeaderNames();
        System.out.println(headerNames);
    }

    class A{
        @ExcelColumn(columnIndex = 1, headerName = "회사")
        private String field1;
        String field2;
        @ExcelColumn(headerName = "브랜드", columnIndex = 2)
        protected int field3;
        public double field4;
    }
}