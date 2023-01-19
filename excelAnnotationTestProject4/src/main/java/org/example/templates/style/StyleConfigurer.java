package org.example.templates.style;

import org.apache.poi.ss.usermodel.CellStyle;

import java.util.function.Consumer;

public class StyleConfigurer {

    protected Consumer<CellStyle> configurer = cellStyle -> {};

    public StyleConfigurer and(Consumer<CellStyle> excelStyle){
        this.configurer = this.configurer.andThen(excelStyle);
        return this;
    }
}
