package org.example.templates.style;

import lombok.ToString;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

@ToString
public abstract class StyleTemplate {
    private StyleConfigurer configurer = new StyleConfigurer();
    public StyleTemplate() {
        configure(configurer);
    }
    public abstract void configure(StyleConfigurer configurer);
    public CellStyle apply(Workbook workbook){
        CellStyle cellStyle = workbook.createCellStyle();
        configurer.configurer.accept(cellStyle);
        return cellStyle;
    }
}
