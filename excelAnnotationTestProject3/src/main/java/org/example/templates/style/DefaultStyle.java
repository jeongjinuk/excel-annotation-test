package org.example.templates.style;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

public class DefaultStyle extends StyleTemplate{

    @Override
    public void configure(StyleConfigurer configurer) {
        configurer
                .and(cellStyle -> cellStyle.setAlignment(HorizontalAlignment.CENTER))
                .and(cellStyle -> cellStyle.setBorderBottom(BorderStyle.HAIR))
                .and(cellStyle -> cellStyle.setBorderLeft(BorderStyle.HAIR))
                .and(cellStyle -> cellStyle.setBorderRight(BorderStyle.HAIR))
                .and(cellStyle -> cellStyle.setBorderTop(BorderStyle.HAIR));
    }
}
