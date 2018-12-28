package com.example.administrator.chengnian444.exception;

/**
 * @author Administrator
 * @Title ${name}
 * @ProjectName 444
 * @Description: TODO
 * @date 2018/12/2819:22
 */
public class ContentException extends Exception {
    public ContentException() {
    }

    public ContentException(String message) {
        super(message);
    }

    public ContentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContentException(Throwable cause) {
        super(cause);
    }

    public ContentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
