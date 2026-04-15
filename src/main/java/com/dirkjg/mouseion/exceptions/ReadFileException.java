package com.dirkjg.mouseion.exceptions;

import com.dirkjg.mouseion.exceptions.ReadFileException;

public class ReadFileException extends RuntimeException {

    public ReadFileException() {
        super();
    }

    public ReadFileException(String message) {
        super(message);
    }

    public ReadFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
