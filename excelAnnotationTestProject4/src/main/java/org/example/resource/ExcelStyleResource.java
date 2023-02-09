package org.example.resource;

import org.apache.poi.ss.usermodel.CellStyle;
import org.example.excel.style.DefaultStyleKey;
import org.example.excel.style.StyleLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class ExcelStyleResource {
    private final Map<String, CellStyle> headerStyle = new HashMap<>();
    private final Map<String, CellStyle> bodyStyle = new HashMap<>();

    public Optional<CellStyle> findByFieldName(String key, StyleLocation styleLocation) {
        switch (styleLocation) {
            case HEADER:
                return findByFieldName(key, headerStyle, DefaultStyleKey.DEFAULT_HEADER_STYLE);
            case BODY:
                return findByFieldName(key, bodyStyle, DefaultStyleKey.DEFAULT_BODY_STYLE);
            default:
                throw new UnsupportedOperationException();
        }
    }
    void putHeaderStyle(String key, CellStyle cellStyle) {
        headerStyle.put(key, cellStyle);
    }

    void putBodyStyle(String key, CellStyle cellStyle) {
        bodyStyle.put(key, cellStyle);
    }

    private Optional<CellStyle> findByFieldName(String key, Map<String, CellStyle> styleMap, DefaultStyleKey defaultStyleKey) {
        Optional<CellStyle> style = getCellStyle(styleMap, key);
        Optional<CellStyle> defaultStyle = getCellStyle(styleMap, defaultStyleKey.getLocation());
        return !style.isEmpty() ? style : defaultStyle;
    }

    private Optional<CellStyle> getCellStyle(Map<String, CellStyle> map, String key) {
        return map.containsKey(key) ? Optional.of(map.get(key)) : Optional.empty();
    }

}
