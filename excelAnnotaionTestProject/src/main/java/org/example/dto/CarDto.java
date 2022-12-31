package org.example.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CarDto {
    private final String company;
    private final String name;
    private final String kind;
    private final int price;
    private final double rating;

}
