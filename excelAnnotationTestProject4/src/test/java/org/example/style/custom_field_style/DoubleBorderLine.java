package org.example.style.custom_field_style;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.example.excel.style.Style;

public class DoubleBorderLine implements Style {
    /**
     * 테두리 : double
     */
    @Override
    public void setStyle(CellStyle cellStyle) {
        cellStyle.setBorderTop(BorderStyle.DOUBLE);
        cellStyle.setBorderBottom(BorderStyle.DOUBLE);
        cellStyle.setBorderRight(BorderStyle.DOUBLE);
        cellStyle.setBorderLeft(BorderStyle.DOUBLE);

    }
}
