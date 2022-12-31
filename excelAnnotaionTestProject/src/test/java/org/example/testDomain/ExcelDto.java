package org.example.testDomain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExcelDto {
    private final String cafeName;
    private final String name;
    private final int price;
    private final double grade;
}
