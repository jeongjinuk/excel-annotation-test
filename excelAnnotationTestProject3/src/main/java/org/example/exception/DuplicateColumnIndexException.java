package org.example.exception;

public class DuplicateColumnIndexException extends ExcelException {

    public DuplicateColumnIndexException(String message) {
        super(message, null);
    }
}
