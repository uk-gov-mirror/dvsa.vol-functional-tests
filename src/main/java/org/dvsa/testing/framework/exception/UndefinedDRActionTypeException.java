package org.dvsa.testing.framework.exception;

public class UndefinedDRActionTypeException extends Exception {

    public UndefinedDRActionTypeException() {
    }

    public UndefinedDRActionTypeException(String message) {
        super(message);
    }

    public UndefinedDRActionTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UndefinedDRActionTypeException(Throwable cause) {
        super(cause);
    }

}
