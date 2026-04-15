package com.dirkjg.mouseion.exceptions;

public class PaintingTitleTooLongException extends RuntimeException {
    public PaintingTitleTooLongException(String message) {
        super(message);
    }

    public PaintingTitleTooLongException(){
        super();
    }
}
