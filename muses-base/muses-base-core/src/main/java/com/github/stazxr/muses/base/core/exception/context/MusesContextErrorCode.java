package com.github.stazxr.muses.base.core.exception.context;

/**
 * Enum defining error codes and messages related to Muses context exceptions.
 *
 * @author SunTao
 * @since 2024-07-02
 */
public enum MusesContextErrorCode {
    /**
     * Loading default Muses context error.
     */
    MUS_CXT_001("MUS_CXT_001", "Loading default Muses context error."),

    /**
     * Muses context properties are null while creating Muses context.
     */
    MUS_CXT_002("MUS_CXT_002", "Muses context properties are null while creating Muses context.");

    private final String code;
    private final String message;

    MusesContextErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets the error code.
     *
     * @return The error code
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets the error message.
     *
     * @return The error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns a string representation of the error in the format [code] message.
     *
     * @return The string representation of the error
     */
    @Override
    public String toString() {
        return "[" + getCode() + "] " + getMessage();
    }
}
