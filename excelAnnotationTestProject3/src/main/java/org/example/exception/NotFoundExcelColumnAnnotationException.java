package org.example.exception;

import org.example.ExcelException;

public class NotFoundExcelColumnAnnotationException extends ExcelException {
    public NotFoundExcelColumnAnnotationException(String message) {
        super(message, null);
    }
}
