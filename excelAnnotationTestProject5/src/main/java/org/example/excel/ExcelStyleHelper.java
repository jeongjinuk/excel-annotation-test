package org.example.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;
import org.example.excel.style.NoStyle;
import org.example.excel.style.Style;
import org.example.excel.style.StyleHelper;
import org.example.excel.style.StyleLocation;
import org.example.resource.ExcelStyleResource;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ExcelStyleHelper implements StyleHelper {
/*    private final ExcelStyleResource resource;
    private final Supplier<CellStyle> styleSupplier;
    private final Supplier<DataFormat> formatSupplier;

    private final Map<String, CellStyle> cellStyleMap = new HashMap<>();

    ExcelStyleHelper(ExcelStyleResource resource, Workbook workbook) {
        this.resource = resource;
        this.styleSupplier = workbook::createCellStyle;
        this.formatSupplier = workbook::createDataFormat;
    }


    boolean isNoStyleClass(Style style){ // 여기에 해당되면
        return style instanceof NoStyle;
    }
    Style getStyle(StyleLocation styleLocation){

    }

    void setCellStyle(Cell cell, String fieldName, StyleLocation styleLocation){
        switch (styleLocation){
            case HEADER:

            case BODY:
        }
    }*/













}