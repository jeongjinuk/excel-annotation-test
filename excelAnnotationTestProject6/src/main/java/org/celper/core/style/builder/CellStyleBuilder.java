package org.celper.core.style.builder;

import org.apache.poi.ss.usermodel.*;

/**
 * The type Cell style builder.
 */
public class CellStyleBuilder {
    private final Workbook _wb;
    private final CellStyle cellStyle;

    /**
     * Instantiates a new Cell style builder.
     *
     * @param workbook  the workbook
     * @param cellStyle the cell style
     */
    public CellStyleBuilder(Workbook workbook, CellStyle cellStyle) {
        this._wb = workbook;
        this.cellStyle = cellStyle;
    }

    /**
     * Sets all of border.
     *
     * @param borderStyle the border style
     * @return the all of border
     */
    public CellStyleBuilder setAllOfBorder(BorderStyle borderStyle) {
        this.cellStyle.setBorderBottom(borderStyle);
        this.cellStyle.setBorderTop(borderStyle);
        this.cellStyle.setBorderLeft(borderStyle);
        this.cellStyle.setBorderRight(borderStyle);
        return this;
    }

    /**
     * Sets alignment.
     *
     * @param alignment the alignment
     * @return the alignment
     */
    public CellStyleBuilder setAlignment(HorizontalAlignment alignment) {
        this.cellStyle.setAlignment(alignment);
        return this;
    }

    /**
     * Sets vertical alignment.
     *
     * @param verticalAlignment the vertical alignment
     * @return the vertical alignment
     */
    public CellStyleBuilder setVerticalAlignment(VerticalAlignment verticalAlignment) {
        this.cellStyle.setVerticalAlignment(verticalAlignment);
        return this;
    }

    /**
     * Is wrap text cell style builder.
     *
     * @param b the b
     * @return the cell style builder
     */
    public CellStyleBuilder isWrapText(boolean b) {
        this.cellStyle.setWrapText(b);
        return this;
    }

    /**
     * Sets fill background color.
     *
     * @param colorIndex  the color index
     * @param patternType the pattern type
     * @return the fill background color
     */
    public CellStyleBuilder setFillBackgroundColor(short colorIndex, FillPatternType patternType) {
        this.cellStyle.setFillForegroundColor(colorIndex);
        this.cellStyle.setFillPattern(patternType);
        return this;
    }

    /**
     * Font font style builder.
     *
     * @return the font style builder
     */
    public FontStyleBuilder font() {
        return new FontStyleBuilder(this._wb, this.cellStyle);
    }
}
