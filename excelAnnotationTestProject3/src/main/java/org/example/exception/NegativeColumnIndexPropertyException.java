package org.example.exception;

import org.example.ExcelException;

public class NegativeColumnIndexPropertyException extends ExcelException {
    public NegativeColumnIndexPropertyException(String message) {
        super(message, null);
    }
}
