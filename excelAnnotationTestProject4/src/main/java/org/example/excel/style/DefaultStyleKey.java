package org.example.excel.style;

public enum DefaultStyleKey {
    DEFAULT_HEADER_STYLE("_DEFAULT_HEADER_STYLE"),
    DEFAULT_BODY_STYLE("_DEFAULT_BODY_STYLE");

    private String location;

    DefaultStyleKey(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}
