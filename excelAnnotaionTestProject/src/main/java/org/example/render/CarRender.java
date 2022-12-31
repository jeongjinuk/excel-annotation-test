package org.example.render;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.example.dto.CafeDto;
import org.example.dto.CarDto;
import org.example.excelInformation.CafeInformation;
import org.example.excelInformation.CarInformation;

public class CarRender extends  ViewRender<CarDto>{


    @Override
    protected void renderHeader(Sheet sheet, Row row) {
        for (CarInformation carInformation : CarInformation.values()) { // 이부분이 좀 문제
            renderCell(row, carInformation, carInformation.getHeaderName());
        }
    }

    @Override
    protected void renderBody(Row row, CarDto dto) {
        renderCell(row, CarInformation.COMPANY, dto.getCompany());
        renderCell(row, CarInformation.NAME, dto.getName());
        renderCell(row, CarInformation.KIND, dto.getKind());
        renderCell(row, CarInformation.PRICE, dto.getPrice());
        renderCell(row, CarInformation.RATING, dto.getRating());
    }
}
