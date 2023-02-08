package org.example.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;

public final class NoStyle implements Style{

    private NoStyle(){}
    @Override
    public void setStyle(CellStyle cellStyle) {
        // do noting
    }
}
