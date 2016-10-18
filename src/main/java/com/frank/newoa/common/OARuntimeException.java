package com.frank.newoa.common;

/**
 * oa运行时异常
 *
 * @author fzhang090
 */
public class OARuntimeException extends RuntimeException {

    public OARuntimeException() {
        super();
    }

    public OARuntimeException(String message) {
        super(message);
    }

    public OARuntimeException(Exception e) {
        super(e);
    }

    public OARuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
