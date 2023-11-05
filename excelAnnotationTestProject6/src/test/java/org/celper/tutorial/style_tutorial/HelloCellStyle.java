package org.celper.tutorial.style_tutorial;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.celper.core.style.CellStyleConfigurer;
import org.celper.core.style.builder.CellStyleBuilder;

public class HelloCellStyle implements CellStyleConfigurer {
    @Override
    public void config(CellStyleBuilder builder) {
        builder
                .setAllOfBorder(BorderStyle.DOUBLE)
        .font()
                .setFontName("명조")
                .isBold(true);
    }
}
