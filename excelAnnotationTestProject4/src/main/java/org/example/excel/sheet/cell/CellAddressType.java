package org.example.excel.sheet.cell;

public enum CellAddressType {
    ROW_ABS,
    COL_ABS,
    ABS;

    public static String convert(String str, CellAddressType type){
        switch (type){
            case ABS:
                return convertABS(str).toString();
            case COL_ABS:
                return convertColABS(str).toString();
            case ROW_ABS:
                return convertRowABS(str).toString();
            default:
                throw new UnsupportedOperationException();
        }
    }

    private static StringBuilder convertRowABS(String str){
        StringBuilder stringBuilder = new StringBuilder("$");
        CellAddressPattern.ROW_ABS_PATTERN.getPattern()
                .matcher(str).results()
                .forEach(stringBuilder::append);
        return stringBuilder;
    }
    private static StringBuilder convertColABS(String str){
        StringBuilder stringBuilder = new StringBuilder("$");
        CellAddressPattern.COL_ABS_PATTERN.getPattern()
                .matcher(str).results()
                .forEach(stringBuilder::append);
        return stringBuilder;
    }

    private static StringBuilder convertABS(String str){
        return convertColABS(str).append(convertRowABS(str));
    }
}
