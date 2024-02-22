package com.medilabo.ui.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Optional;

public class JwtUtil {
    public static String findToken(HttpServletRequest request) {
        String jwtToken = "";

        Optional<Cookie> optionalCookie = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("token-medilabo")).findFirst();
        if(optionalCookie.isPresent()) {
            jwtToken = optionalCookie.get().getValue();
        }

        return jwtToken;
    }
}
