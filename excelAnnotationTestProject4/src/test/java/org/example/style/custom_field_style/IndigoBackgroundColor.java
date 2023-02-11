package org.example.style.custom_field_style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.example.excel.style.Style;

public class IndigoBackgroundColor implements Style {
    /**
     * 배경색 : 인디고
     */
    @Override
    public void setStyle(CellStyle cellStyle) {
        cellStyle.setFillForegroundColor(IndexedColors.INDIGO.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }

}
