package org.example.excelInformation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.util.HeaderColumnInformation;

@Getter //이렇게 사용하면 이름을 못따라감
@RequiredArgsConstructor
public enum CarInformation implements HeaderColumnInformation {
    COMPANY("회사", 0, "TEXT"),
    NAME("자동차명",1,"TEXT"),
    KIND("차종", 2, "TEXT"),
    PRICE("가격",3,"INTEGER"),
    RATING("등급",4, "DOUBLE");

    private final String headerName;
    private final int columnIndex;
    private final String columnType;
}
