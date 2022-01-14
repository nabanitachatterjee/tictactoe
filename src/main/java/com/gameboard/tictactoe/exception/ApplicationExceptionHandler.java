package com.gameboard.tictactoe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

class Message {
    private final String error;

    public Message(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(MovesNotAllowedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message handleException(MovesNotAllowedException exception) {
        return new Message(exception.getMessage());
    }

    @ExceptionHandler(BoardUnavailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message handleException(BoardUnavailableException exception) {
        return new Message(exception.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message handleException(HttpRequestMethodNotSupportedException exception) {
        return new Message("This method is not available!");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message handleException(Exception exception) {
        exception.printStackTrace();
        return new Message("You are not allowed to do this!");
    }
}