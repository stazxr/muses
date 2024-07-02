package com.github.stazxr.muses.base.core.exception.context;

import com.github.stazxr.muses.base.core.exception.MusesException;

/**
 * The base class for all custom exceptions in the Muses framework.
 * Extends RuntimeException to allow unchecked exception handling.

 * @author SunTao
 * @since 2024-06-26
 */
public class MusesContextException extends MusesException {
    private static final long serialVersionUID = -8451328244154695168L;

    public MusesContextException(MusesContextErrorCode errorCode) {
        super(errorCode.toString());
    }

    public MusesContextException(MusesContextErrorCode errorCode, Throwable cause) {
        super(errorCode.toString(), cause);
    }
}
