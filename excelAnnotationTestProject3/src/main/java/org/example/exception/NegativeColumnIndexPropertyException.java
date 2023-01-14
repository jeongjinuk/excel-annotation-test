package org.example.exception;

public class NegativeColumnIndexPropertyException extends ExcelException {
    public NegativeColumnIndexPropertyException(String message) {
        super(message, null);
    }
}
