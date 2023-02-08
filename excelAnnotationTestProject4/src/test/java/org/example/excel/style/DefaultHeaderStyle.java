package org.example.excel.style;


import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;

public class DefaultHeaderStyle implements Style {
    @Override
    public void setStyle(CellStyle cellStyle) {
        cellStyle.setBorderLeft(BorderStyle.HAIR);
        cellStyle.setBorderBottom(BorderStyle.HAIR);
        cellStyle.setBorderRight(BorderStyle.HAIR);
        cellStyle.setBorderTop(BorderStyle.HAIR);
    }
}
