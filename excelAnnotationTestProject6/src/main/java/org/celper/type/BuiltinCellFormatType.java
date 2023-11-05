package org.celper.type;

/**
 * The enum Builtin cell format type.
 */
public enum BuiltinCellFormatType {
    /**
     * General builtin cell format type.
     */
    GENERAL("General"),
    /**
     * General number builtin cell format type.
     */
    GENERAL_NUMBER("0"),
    /**
     * Decimal builtin cell format type.
     */
    DECIMAL("0.00"),
    /**
     * Thousand separator builtin cell format type.
     */
    THOUSAND_SEPARATOR("#,##0"),
    /**
     * Accounting builtin cell format type.
     */
    ACCOUNTING("_-₩* #,##0_-;-₩* #,##0_-;_-₩* \"-\"_-;_-@_-"),
    /**
     * Simple date builtin cell format type.
     */
    SIMPLE_DATE("yyyy-mm-dd"),
    /**
     * Percent builtin cell format type.
     */
    PERCENT("0%"),
    /**
     * Number to korean builtin cell format type.
     */
    NUMBER_TO_KOREAN("[DBNum4]");
    /**
     * The Cell format.
     */
    public final String cellFormat;
    BuiltinCellFormatType(String cellFormat) {
        this.cellFormat = cellFormat;
    }

    /**
     * Gets cell format.
     *
     * @return the cell format
     */
    public String getCellFormat() {
        return cellFormat;
    }
}
