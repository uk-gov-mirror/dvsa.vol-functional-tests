package org.dvsa.testing.framework.exception;

public class NonexistingWorldStateException extends Exception {
    public NonexistingWorldStateException() {
    }

    public NonexistingWorldStateException(String message) {
        super(message);
    }

    public NonexistingWorldStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonexistingWorldStateException(Throwable cause) {
        super(cause);
    }
}
