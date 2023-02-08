package org.example.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

public class DefaultBodyStyle implements Style{
    @Override
    public void setStyle(CellStyle cellStyle) {
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
    }
}
