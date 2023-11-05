package org.celper.type;

/**
 * GENERAL → 일반 표시
 * GENERAL_NUMBER → 숫자 표기 소수 뒷자리 반올림
 * DECIMAL → 소수 표시 소수 둘째 자리
 * _1000_UNIT_CLASSIFICATION  →1,000단위 구분 기호
 * ACCOUNTING → 회계 표시 형식
 * SIMPLE_DATE→ yyyy-MM-dd 형식
 * PERCENT → 백분율
 * NUMBER_TO_KOREAN → 12 → 십이
 */
public enum BuiltinCellFormatType {
    GENERAL("General"),
    GENERAL_NUMBER("0"),
    DECIMAL("0.00"),
    THOUSAND_SEPARATOR("#,##0"),
    ACCOUNTING("_-₩* #,##0_-;-₩* #,##0_-;_-₩* \"-\"_-;_-@_-"),
    SIMPLE_DATE("yyyy-mm-dd"),
    PERCENT("0%"),
    NUMBER_TO_KOREAN("[DBNum4]");
    public final String cellFormat;
    BuiltinCellFormatType(String cellFormat) {
        this.cellFormat = cellFormat;
    }
    public String getCellFormat() {
        return cellFormat;
    }
}
