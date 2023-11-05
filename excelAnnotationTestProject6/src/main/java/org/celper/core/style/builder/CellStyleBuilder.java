package org.celper.core.style.builder;

import org.apache.poi.ss.usermodel.*;

public class CellStyleBuilder {
    private final Workbook _wb;
    private final CellStyle cellStyle;

    public CellStyleBuilder(Workbook workbook, CellStyle cellStyle) {
        this._wb = workbook;
        this.cellStyle = cellStyle;
    }

    public CellStyleBuilder setAllOfBorder(BorderStyle borderStyle) {
        this.cellStyle.setBorderBottom(borderStyle);
        this.cellStyle.setBorderTop(borderStyle);
        this.cellStyle.setBorderLeft(borderStyle);
        this.cellStyle.setBorderRight(borderStyle);
        return this;
    }

    public CellStyleBuilder setAlignment(HorizontalAlignment alignment) {
        this.cellStyle.setAlignment(alignment);
        return this;
    }

    public CellStyleBuilder setVerticalAlignment(VerticalAlignment verticalAlignment) {
        this.cellStyle.setVerticalAlignment(verticalAlignment);
        return this;
    }

    public CellStyleBuilder isWrapText(boolean b) {
        this.cellStyle.setWrapText(b);
        return this;
    }

    public CellStyleBuilder setFillBackgroundColor(short colorIndex, FillPatternType patternType) {
        this.cellStyle.setFillForegroundColor(colorIndex);
        this.cellStyle.setFillPattern(patternType);
        return this;
    }

    public FontStyleBuilder font() {
        return new FontStyleBuilder(this._wb, this.cellStyle);
    }
}
