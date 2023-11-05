package org.celper.core.style.builder;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;


public class FontStyleBuilder {
    private final Workbook _wb;
    private final CellStyle cellStyle;
    private final Font font;

    public FontStyleBuilder(Workbook workbook, CellStyle cellStyle) {
        this._wb = workbook;
        this.cellStyle = cellStyle;
        this.font = workbook.createFont();
    }

    public Font getFont() {
        return this.font;
    }

    public FontStyleBuilder setFontHeightInPoints(short height) {
        this.font.setFontHeightInPoints(height);
        this.cellStyle.setFont(this.font);
        return this;
    }

    public FontStyleBuilder setFontName(String fontName) {
        this.font.setFontName(fontName);
        this.cellStyle.setFont(this.font);
        return this;
    }

    public FontStyleBuilder isBold(boolean b) {
        this.font.setBold(b);
        this.cellStyle.setFont(this.font);
        return this;
    }

}
