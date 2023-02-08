package org.example.excel.style;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;

public class NameStyle implements Style{
    @Override
    public void setStyle(CellStyle cellStyle) {
        cellStyle.setBorderLeft(BorderStyle.DOUBLE);
        cellStyle.setBorderRight(BorderStyle.DOUBLE);
    }
}
