package org.example.resource;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.example.ExcelStyle;
import org.example.excel.style.NoStyle;
import org.example.excel.style.Style;
import org.example.excel.style.StyleLocation;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public final class ExcelStyleResourceFactory {
    private final ExcelStyleResource excelStyleResource = new ExcelStyleResource();
    private CellStyle defaultHeaderStyle;
    private CellStyle defaultBodyStyle;
    private final Workbook workbook;

    public ExcelStyleResourceFactory(List<Field> fields, Optional<ExcelStyle> defaultStyle, Workbook workbook) {
        this.workbook = workbook;
        this.defaultHeaderStyle = workbook.createCellStyle();
        this.defaultBodyStyle = workbook.createCellStyle();
        defaultStyle.ifPresent(this::setDefaultStyle);
        fields.forEach(this::put);
    }
    public ExcelStyleResource getExcelStyleResource(){
        return this.excelStyleResource;
    }

    private void setDefaultStyle(ExcelStyle excelStyle) {
        this.defaultHeaderStyle = setStyle(defaultHeaderStyle, excelStyle.headerStyleClass(), true);
        this.defaultBodyStyle = setStyle(defaultBodyStyle, excelStyle.bodyStyleClass(),true);
        short format = workbook.createDataFormat().getFormat(excelStyle.format());
        defaultBodyStyle.setDataFormat(format);
    }

    private void put(Field field) {
        CellStyle header = defaultHeaderStyle;
        CellStyle body = defaultBodyStyle;
        if (field.isAnnotationPresent(ExcelStyle.class)) {
            ExcelStyle excelStyle = field.getDeclaredAnnotation(ExcelStyle.class);
            header = setStyle(defaultHeaderStyle, excelStyle.headerStyleClass(), false);
            body = setStyle(defaultBodyStyle, excelStyle.bodyStyleClass(), false);
            body.setDataFormat(workbook.createDataFormat().getFormat(excelStyle.format()));
        }
        excelStyleResource.put(StyleLocation.HEADER, field.getName(), header);
        excelStyleResource.put(StyleLocation.BODY, field.getName(), body);
    }

    private CellStyle setStyle(CellStyle defaultStyle, Class<? extends Style> c, boolean isDefault) {
        CellStyle cellStyle = workbook.createCellStyle();

        if (c.equals(NoStyle.class)) {
            cellStyle.cloneStyleFrom(defaultStyle);
            return cellStyle;
        }
        Style style = ReflectionUtils.getInstance(c);

        if (!isDefault && style.usedDefaultStyle()){
            cellStyle.cloneStyleFrom(defaultStyle);
        }
        style.configure(cellStyle);
        return cellStyle;
    }
}
