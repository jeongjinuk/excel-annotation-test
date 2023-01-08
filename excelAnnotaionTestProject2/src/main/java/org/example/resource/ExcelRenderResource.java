package org.example.resource;

import lombok.Getter;
import org.example.annotation.ExcelColumn;

import java.util.List;
import java.util.Map;

@Getter
public class ExcelRenderResource {
    private Map<String, ExcelColumn> headerNames;

    public ExcelRenderResource(Map<String, ExcelColumn> headerNames) {
        this.headerNames = headerNames;
    }
}
