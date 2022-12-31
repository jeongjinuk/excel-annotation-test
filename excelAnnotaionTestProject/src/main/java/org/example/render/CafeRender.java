package org.example.render;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.example.dto.CafeDto;
import org.example.excelInformation.CafeInformation;

public class CafeRender extends ViewRender<CafeDto> {
    @Override
    protected void renderHeader(Sheet sheet, Row row) {
        for (CafeInformation cafeInformation : CafeInformation.values()) {
            renderCell(row, cafeInformation, cafeInformation.getHeaderName());
        }
    }
    @Override
    protected void renderBody(Row row, CafeDto dto) {
        renderCell(row, CafeInformation.COMPANY, dto.getCafeName());
        renderCell(row, CafeInformation.NAME, dto.getName());
        renderCell(row, CafeInformation.PRICE, dto.getPrice());
        renderCell(row, CafeInformation.GRADE, dto.getGrade());
    }
}