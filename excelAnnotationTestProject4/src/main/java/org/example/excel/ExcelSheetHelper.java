package org.example.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.example.excel.sheet.CellAddressType;
import org.example.excel.sheet.ExcelSheet;
import org.example.excel.style.StyleLocation;
import org.example.exception.ExcelException;
import org.example.exception.NoSuchFieldNameException;
import org.example.resource.ExcelStyleResource;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ExcelSheetHelper implements ExcelSheet {
    private Sheet sheet;
    private Map<String, Integer> fieldIndexMap;
    private ExcelStyleResource styleResource;
    ExcelSheetHelper(Map<String, Integer> fieldIndexMap, ExcelStyleResource styleResource) {
        this.fieldIndexMap = fieldIndexMap;
        this.styleResource = styleResource;
    }
    void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }
    @Override
    public Row getRow(int i){
        List<Row> list = getList(sheet.rowIterator());
        return get(list,
                row -> row.getRowNum() == i,
                () -> this.sheet.createRow(i));
    }
    @Override
    public Cell getCell(int rowIndex, int colIndex){
        Row row = getRow(rowIndex);
        List<Cell> list = getList(row.cellIterator());
        return get(list,
                cell -> cell.getColumnIndex() == colIndex,
                () -> row.createCell(colIndex));
    }
    @Override
    public Cell getCellByFieldName(int row, String fieldName) {
        if (!this.fieldIndexMap.containsKey(fieldName)){
            throw new NoSuchFieldNameException(String.format("'%s' does not exist", fieldName));
        }
        return getCell(row, this.fieldIndexMap.get(fieldName));
    }

    @Override
    public String getCellAddress(int row, int col) {
        return getCell(row, col).getAddress().formatAsString();
    }

    @Override
    public String getCellAddressByFieldName(int row, String fieldName) {
        if (!this.fieldIndexMap.containsKey(fieldName)){
            throw new NoSuchFieldNameException(String.format("'%s' does not exist", fieldName));
        }
        return getCell(row, this.fieldIndexMap.get(fieldName)).getAddress().formatAsString();
    }

    @Override
    public String getCellAddress(int row, int col, CellAddressType type) {
        return CellAddressType.convert(getCellAddress(row,col), type);
    }

    @Override
    public String getCellAddressByFieldName(int row, String fieldName, CellAddressType type) {
        return CellAddressType.convert(getCellAddressByFieldName(row,fieldName), type);
    }

    @Override
    public boolean containsFieldName(String fieldName) {
        return this.fieldIndexMap.containsKey(fieldName);
    }

    @Override
    public void setCellValue(Cell cell, Object o) {
        if (o instanceof Number) {
            Number number = (Number) o;
            cell.setCellValue(number.doubleValue());
            return;
        }
        cell.setCellValue(o == null ? "" : o.toString());
    }

    @Override
    public void setFormulaCell(Cell cell, String formula) {
        cell.setCellFormula(formula);
    }

    void setStyle(Cell cell, String fieldName, StyleLocation styleLocation){
        Optional<CellStyle> cellStyle = this.styleResource.findByFieldName(fieldName, styleLocation);
        if (cellStyle.isEmpty()){
            return;
        }
        cell.setCellStyle(cellStyle.get());
    }

    <T> void setCellValue(Field field, Cell cell, T data) {
        try {
            field.setAccessible(true);
            Object o = field.get(data);
            setCellValue(cell, o);
        } catch (IllegalAccessException e) {
            throw new ExcelException(e.getMessage(), e);
        }
    }
    <T> T get(List<T> list, Predicate<T> predicate, Supplier<T> supplier){
        return list.stream()
                .filter(predicate)
                .findAny()
                .orElseGet(supplier);
    }
    <T> List<T> getList(Iterator<T> iterator){
        List<T> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);
        return list;
    }
}
