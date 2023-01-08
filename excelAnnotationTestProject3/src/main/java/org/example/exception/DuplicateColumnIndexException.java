package org.example.exception;

import org.example.ExcelException;

public class DuplicateColumnIndexException extends ExcelException {

    public DuplicateColumnIndexException(String message) {
        super(message, null);
    }
}
