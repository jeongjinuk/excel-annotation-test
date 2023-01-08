package org.example;

import org.example.excel.AdapterSheet;

import java.util.Map;

public interface FormulaTemplate<T> {
    default void formulaRender(AdapterSheet adapterSheet, int rowIndex, Map<String, Integer> fieldNameWithColumnIndexMap) {
        validation(adapterSheet, rowIndex, fieldNameWithColumnIndexMap);
        render(adapterSheet, rowIndex, fieldNameWithColumnIndexMap);
    }
    void validation(AdapterSheet Sheet, int rowIndex, Map<String, Integer> fieldNameWithColumnIndexMap);
    void render(AdapterSheet Sheet, int rowIndex, Map<String, Integer> fieldNameWithColumnIndexMap);
}

