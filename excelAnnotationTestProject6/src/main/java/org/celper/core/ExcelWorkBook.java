package org.celper.core;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.celper.type.WorkBookType;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Excel work book.
 */
public class ExcelWorkBook {
    private final Workbook _wb;
    private final List<ExcelSheet> sheets = new ArrayList<>();

    /**
     * Instantiates a new Excel work book.
     *
     * @param workBookType the work book type
     */
    public ExcelWorkBook(WorkBookType workBookType) {
        this(workBookType.createWorkBook());
    }

    /**
     * Instantiates a new Excel work book.
     *
     * @param workbook the workbook
     */
    public ExcelWorkBook(Workbook workbook) {
        this._wb = workbook;
        workbook.spliterator().forEachRemaining(sheet -> this.sheets.add(new ExcelSheet(workbook, sheet)));
    }

    /**
     * Create sheet excel sheet.
     *
     * @return the excel sheet
     */
    public ExcelSheet createSheet() {
        ExcelSheet sheet = new ExcelSheet(this._wb, this._wb.createSheet());
        this.sheets.add(sheet);
        return sheet;
    }

    /**
     * Create sheet excel sheet.
     *
     * @param name the name
     * @return the excel sheet
     */
    public ExcelSheet createSheet(String name) {
        ExcelSheet sheet = new ExcelSheet(this._wb, this._wb.createSheet(name));
        this.sheets.add(sheet);
        return sheet;
    }

    /**
     * Gets sheet at.
     *
     * @param idx the idx
     * @return the sheet at
     */
    public ExcelSheet getSheetAt(int idx) {
        return this.sheets.get(idx);
    }

    /**
     * Gets sheet by name.
     *
     * @param name the name
     * @return the sheet by name
     */
    public ExcelSheet getSheetByName(String name) {
        return this.sheets.stream()
                .filter(excelSheet -> name.equals(excelSheet.getName()))
                .findAny()
                .orElseThrow();
    }

    /**
     * Size int.
     *
     * @return the int
     */
    public int size() {
        return this.sheets.size();
    }

    /**
     * Gets workbook.
     *
     * @return the workbook
     */
    public Workbook getWorkbook() {
        return _wb;
    }

    /**
     * Write.
     *
     * @param outputStream the output stream
     * @throws IOException the io exception
     */
    public void write(OutputStream outputStream) throws IOException {
        this._wb.write(outputStream);
        _wb.close();
        if (_wb.getClass().equals(SXSSFWorkbook.class)){
            ((SXSSFWorkbook)_wb).dispose();
        }
        outputStream.close();
    }
}
