package org.example.style;

import org.apache.poi.ss.usermodel.*;
import org.example.excel.style.Style;

public class DefaultHeaderStyle implements Style {
    /**
     * 기본 헤더 스타일
     * 배경 : 파랑색
     * 테두리 : MEDIUM
     */
    @Override
    public void setStyle(CellStyle cellStyle) {
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setBorderTop(BorderStyle.MEDIUM);
        cellStyle.setBorderLeft(BorderStyle.MEDIUM);
        cellStyle.setBorderRight(BorderStyle.MEDIUM);

        cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }

}
