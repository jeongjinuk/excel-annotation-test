package org.example.excelInformation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.example.util.HeaderColumnInformation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum CafeInformation implements HeaderColumnInformation {
    COMPANY("회사",0, "STRING"),
    NAME("제품명",1, "INT"),
    PRICE("가격",2, "INT"),
    GRADE("등급",3, "DOUBLE");

    private final String headerName;
    private final int columnIndex;
    private final String ColumnType;
/*
    public static Map<Integer, List<CafeInformation>> getAllColumns() {
        return Arrays.stream(values())
                .collect(Collectors.groupingBy(HeaderColumnInformation::getRowIndex));
    }
*/

}
