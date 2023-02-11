package org.example.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;

public final class NoStyle implements Style{

    private NoStyle(){}
    @Override
    public void setStyle(CellStyle cellStyle) {
        // do noting
    }

}