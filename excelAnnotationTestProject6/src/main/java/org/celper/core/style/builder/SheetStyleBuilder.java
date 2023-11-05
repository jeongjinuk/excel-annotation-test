package org.celper.core.style.builder;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class SheetStyleBuilder {
    private final Workbook _wb;
    private final Sheet sheet;
    private final CellStyle cellStyle;

    public SheetStyleBuilder(Workbook workbook, Sheet sheet, CellStyle cellStyle) {
        this._wb = workbook;
        this.sheet = sheet;
        this.cellStyle = cellStyle;
    }

    public SheetStyleBuilder isVerticallyCenter(boolean b) {
        this.sheet.setVerticallyCenter(b);
        return this;
    }

    public SheetStyleBuilder isFitToPage(boolean b) {
        this.sheet.setFitToPage(b);
        return this;
    }

    public SheetStyleBuilder setDefaultRowHeight(short rowHeight) {
        this.sheet.setDefaultRowHeight(rowHeight);
        return this;
    }

    public SheetStyleBuilder setDefaultColumnWidth(int columnWidth) {
        this.sheet.setDefaultColumnWidth(columnWidth);
        return this;
    }

    public CellStyleBuilder cellStyleBuilder() {
        return new CellStyleBuilder(_wb, this.cellStyle);
    }

    public CellStyle getCellStyle() {
        return this.cellStyle;
    }
}
