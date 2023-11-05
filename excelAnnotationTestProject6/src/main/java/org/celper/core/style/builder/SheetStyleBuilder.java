package org.celper.core.style.builder;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * The type Sheet style builder.
 */
public class SheetStyleBuilder {
    private final Workbook _wb;
    private final Sheet sheet;
    private final CellStyle cellStyle;

    /**
     * Instantiates a new Sheet style builder.
     *
     * @param workbook  the workbook
     * @param sheet     the sheet
     * @param cellStyle the cell style
     */
    public SheetStyleBuilder(Workbook workbook, Sheet sheet, CellStyle cellStyle) {
        this._wb = workbook;
        this.sheet = sheet;
        this.cellStyle = cellStyle;
    }

    /**
     * Is vertically center sheet style builder.
     *
     * @param b the b
     * @return the sheet style builder
     */
    public SheetStyleBuilder isVerticallyCenter(boolean b) {
        this.sheet.setVerticallyCenter(b);
        return this;
    }

    /**
     * Is fit to page sheet style builder.
     *
     * @param b the b
     * @return the sheet style builder
     */
    public SheetStyleBuilder isFitToPage(boolean b) {
        this.sheet.setFitToPage(b);
        return this;
    }

    /**
     * Sets default row height.
     *
     * @param rowHeight the row height
     * @return the default row height
     */
    public SheetStyleBuilder setDefaultRowHeight(short rowHeight) {
        this.sheet.setDefaultRowHeight(rowHeight);
        return this;
    }

    /**
     * Sets default column width.
     *
     * @param columnWidth the column width
     * @return the default column width
     */
    public SheetStyleBuilder setDefaultColumnWidth(int columnWidth) {
        this.sheet.setDefaultColumnWidth(columnWidth);
        return this;
    }

    /**
     * Cell style builder cell style builder.
     *
     * @return the cell style builder
     */
    public CellStyleBuilder cellStyleBuilder() {
        return new CellStyleBuilder(_wb, this.cellStyle);
    }

    /**
     * Gets cell style.
     *
     * @return the cell style
     */
    public CellStyle getCellStyle() {
        return this.cellStyle;
    }
}
