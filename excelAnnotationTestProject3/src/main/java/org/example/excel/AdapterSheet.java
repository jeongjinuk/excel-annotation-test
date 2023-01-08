package org.example.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class AdapterSheet {
    private Sheet sheet;
    public AdapterSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public Sheet getSheet() {
        return sheet;
    }
    public Row getRow(int i){
        List<Row> list = getList(sheet.rowIterator());
        return get(list,
                row -> row.getRowNum() == i,
                () -> this.sheet.createRow(i));
    }
    public Cell getCell(int rowIndex, int colIndex){
        Row row = getRow(rowIndex);
        List<Cell> list = getList(row.cellIterator());
        return get(list,
                cell -> cell.getColumnIndex() == colIndex,
                () -> row.createCell(colIndex));
    }
    private static <T> T get(List<T> list, Predicate<T> predicate, Supplier<T> supplier){
        return list.stream()
                .filter(predicate)
                .findAny()
                .orElseGet(supplier);
    }
    private static <T> List<T> getList(Iterator<T> iterator){
        List<T> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);
        return list;
    }
}
