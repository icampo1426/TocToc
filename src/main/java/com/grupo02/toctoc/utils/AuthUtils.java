package com.grupo02.toctoc.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {

    public static <T> T getCurrentAuthUser(Class<T> clazz) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (clazz.isInstance(authentication.getPrincipal())) {
            return clazz.cast(authentication.getPrincipal());
        }

        return null;
    }
}
