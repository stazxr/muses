package com.github.stazxr.muses.base.core.exception;

import com.github.stazxr.muses.base.core.util.MusesExceptionUtil;

/**
 * The class for all custom exceptions in the Muses framework.
 * Extends RuntimeException to allow unchecked exception handling.

 * @author SunTao
 * @since 2024-06-26
 */
public class MusesException extends RuntimeException {
    private static final long serialVersionUID = 2332115251235242331L;

    /**
     * Constructs a new MusesException with the specified detail message.
     *
     * @param message the detail message
     */
    public MusesException(String message) {
        super(message);
    }

    /**
     * Constructs a new MusesException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public MusesException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Overrides getMessage() method to return a formatted message using MusesExceptionUtil.
     *
     * @return the formatted message
     */
    public String getMessage() {
        return MusesExceptionUtil.buildMessage(super.getMessage(), this.getCause());
    }

    /**
     * Retrieves the root cause of this exception.
     *
     * @return the root cause, or null if there is no root cause
     */
    public Throwable getRootCause() {
        return MusesExceptionUtil.getRootCause(this);
    }

    /**
     * Retrieves the most specific cause of this exception.
     *
     * @return the most specific cause, or this exception if no more specific cause is found
     */
    public Throwable getMostSpecificCause() {
        return MusesExceptionUtil.getMostSpecificCause(this);
    }

    /**
     * Checks whether this exception contains an instance of the specified exception type
     * within its cause chain.
     *
     * @param exType the exception type to look for
     * @return true if an instance of the specified exception type is found, false otherwise
     */
    public boolean contains(Class<?> exType) {
        if (exType == null) {
            return false;
        } else if (exType.isInstance(this)) {
            return true;
        } else {
            Throwable cause = this.getCause();
            if (cause == this) {
                return false;
            } else if (cause instanceof MusesException) {
                return ((MusesException) cause).contains(exType);
            } else {
                while (cause != null) {
                    if (exType.isInstance(cause)) {
                        return true;
                    }
                    if (cause.getCause() == cause) {
                        break;
                    }
                    cause = cause.getCause();
                }
                return false;
            }
        }
    }
}
