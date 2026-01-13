package com.dirkjg.mouseion.exceptions;

// deze klasse vormt de custom exception
public class RecordNotFoundException extends RuntimeException {

    // De exception zonder message (default exception)
    public RecordNotFoundException() {
        super();
    }

    // De exception met message (exception met message)
    public RecordNotFoundException(String message) {
        super(message);
    }

}
