package com.grupo02.toctoc.controllers;

import com.google.gson.Gson;
import com.grupo02.toctoc.services.exception.EmailAlreadyUsedException;
import com.grupo02.toctoc.services.exception.LoginFailException;
import com.grupo02.toctoc.services.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionControllerHandler {

    @Autowired
    private Gson gson;

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleHttpClientErrorException(HttpClientErrorException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getResponseBodyAsString(), ex.getStatusCode());
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<String> handleHttpServerErrorException(HttpServerErrorException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getResponseBodyAsString(), ex.getStatusCode());
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<Map> emailAlreadyUsedException(EmailAlreadyUsedException ex, WebRequest request) {
        return new ResponseEntity<Map>(new HashMap<>() {{
            put("error","EmailAlreadyUsedException");
            put("msg", gson.toJson(ex.getMessage()));
        }}, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginFailException.class)
    public ResponseEntity<Map> loginFailException(EmailAlreadyUsedException ex, WebRequest request) {
        return new ResponseEntity<Map>(new HashMap<>() {{
            put("error","LoginFailException");
            put("msg", gson.toJson(ex.getMessage()));
        }}, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map> notFoundException(NotFoundException ex, WebRequest request) {
        return new ResponseEntity<Map>(new HashMap<>() {{
            put("error","notFoundException");
            put("msg", gson.toJson(ex.getMessage()));
        }}, HttpStatus.NOT_FOUND);
    }

}
