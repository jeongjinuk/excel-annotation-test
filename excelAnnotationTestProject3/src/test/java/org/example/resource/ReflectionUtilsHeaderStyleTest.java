package org.example.resource;

import org.example.BodyStyle;
import org.example.ExcelColumn;
import org.example.HeaderStyle;
import org.example.templates.style.DefaultStyle;
import org.junit.jupiter.api.Test;

class ReflectionUtilsHeaderStyleTest {

    @HeaderStyle(style = DefaultStyle.class)
    @BodyStyle(style = DefaultStyle.class)
    class StyleTestDto{

        @ExcelColumn(headerName = "이름", columnIndex = 0)
        private String name;

        @ExcelColumn(headerName = "나이", columnIndex = 1)
        private String age;
    }


    @Test
    void findAllClassAnnotations() {
    }
}