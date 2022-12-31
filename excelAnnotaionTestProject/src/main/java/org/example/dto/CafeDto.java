package org.example.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CafeDto {
    private final String cafeName;
    private final String name;
    private final int price;
    private final double grade;
}
