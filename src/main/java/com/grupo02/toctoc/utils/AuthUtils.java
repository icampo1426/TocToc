package com.grupo02.toctoc.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuthUtils {

    public static <T> Optional<T> getCurrentAuthUser(Class<T> clazz) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (clazz.isInstance(authentication.getPrincipal())) {
            return Optional.of(clazz.cast(authentication.getPrincipal()));
        }

        return Optional.empty();
    }
}
