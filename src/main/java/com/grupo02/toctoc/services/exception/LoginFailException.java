package com.grupo02.toctoc.services.exception;

public class LoginFailException extends BadRequestException{

        public static String msg = "Login failed";

        public LoginFailException(String message) {
            super(message);
        }
}
