package com.dirkjg.mouseion.controllers;

import com.dirkjg.mouseion.exceptions.RecordNotFoundException;
import com.dirkjg.mouseion.exceptions.PaintingTitleTooLongException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    //Deze exception handler vangt elke RecordNotFoundException op die naar de gebruiker wordt gegooid en returned daarvoor in de plaats een ResponseEntity met de Message en de NOT_FOUND-status (404)
    @ExceptionHandler(value = RecordNotFoundException.class)
    public ResponseEntity<Object> exception(RecordNotFoundException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = IndexOutOfBoundsException.class)
    public ResponseEntity<Object> exception(IndexOutOfBoundsException exception) {
        return new ResponseEntity<>("Dit id staat niet in de database", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = PaintingTitleTooLongException.class)
    public ResponseEntity<String> exception(PaintingTitleTooLongException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
