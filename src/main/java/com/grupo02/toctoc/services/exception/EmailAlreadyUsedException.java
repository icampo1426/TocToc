package com.grupo02.toctoc.services.exception;

public class EmailAlreadyUsedException extends BadRequestException {

    public static String msg = "Email already used";

    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
