package org.celper.core.style.builder;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;


/**
 * The type Font style builder.
 */
public class FontStyleBuilder {
    private final Workbook _wb;
    private final CellStyle cellStyle;
    private final Font font;

    /**
     * Instantiates a new Font style builder.
     *
     * @param workbook  the workbook
     * @param cellStyle the cell style
     */
    public FontStyleBuilder(Workbook workbook, CellStyle cellStyle) {
        this._wb = workbook;
        this.cellStyle = cellStyle;
        this.font = workbook.createFont();
    }

    /**
     * Gets font.
     *
     * @return the font
     */
    public Font getFont() {
        return this.font;
    }

    /**
     * Sets font height in points.
     *
     * @param height the height
     * @return the font height in points
     */
    public FontStyleBuilder setFontHeightInPoints(short height) {
        this.font.setFontHeightInPoints(height);
        this.cellStyle.setFont(this.font);
        return this;
    }

    /**
     * Sets font name.
     *
     * @param fontName the font name
     * @return the font name
     */
    public FontStyleBuilder setFontName(String fontName) {
        this.font.setFontName(fontName);
        this.cellStyle.setFont(this.font);
        return this;
    }

    /**
     * Is bold font style builder.
     *
     * @param b the b
     * @return the font style builder
     */
    public FontStyleBuilder isBold(boolean b) {
        this.font.setBold(b);
        this.cellStyle.setFont(this.font);
        return this;
    }

}
