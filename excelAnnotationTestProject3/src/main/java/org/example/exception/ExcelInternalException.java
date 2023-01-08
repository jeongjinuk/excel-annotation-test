package org.example.exception;

import org.example.ExcelException;

public class ExcelInternalException extends ExcelException {

    public ExcelInternalException(String message, Throwable cause) {
        super(message, cause);
    }
}
