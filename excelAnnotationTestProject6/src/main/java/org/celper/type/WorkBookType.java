package org.celper.type;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.celper.exception.UnsupportedWorkBookVersionException;


public enum WorkBookType {

    HSSF("xls"),

    SXSSF("xlsx"), // SXSSF 의 경우 dispose 해줘야함 아니면 user/appData/Local/temp 시트 관련 xml 계속 생김

    XSSF("xlsx");

    private final String type;

    WorkBookType(String type) {
        this.type = type;
    }

    public Workbook createWorkBook() {
        switch (this){
            case HSSF:
                return new HSSFWorkbook();
            case SXSSF:
                return new SXSSFWorkbook();
            case XSSF:
                return new XSSFWorkbook();
            default:
                throw new UnsupportedWorkBookVersionException("Unsupported Workbook versions");
        }
    }
}

