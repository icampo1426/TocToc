package com.grupo02.toctoc.services.exception;

public class BadRequestException extends RuntimeException{

    public static int CODE = 400;
    public static String msg = "Bad Request";

    public BadRequestException(String message) {
        super(message);
    }
}
