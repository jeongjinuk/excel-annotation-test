package org.example.excel.sheet.cell;

import java.util.regex.Pattern;

public enum CellAddressPattern {
    ROW_ABS_PATTERN(Pattern.compile("[0-9]+")),
    COL_ABS_PATTERN(Pattern.compile("[A-Z]+"));

    private Pattern pattern;

    CellAddressPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    Pattern getPattern() {
        return pattern;
    }
}
