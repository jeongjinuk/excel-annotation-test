package org.celper.exception;

/**
 * The type Excel exception.
 */
public class ExcelException extends RuntimeException {
    /**
     * Instantiates a new Excel exception.
     *
     * @param message the message
     */
    public ExcelException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Excel exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }
}
