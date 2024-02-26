package com.medilabo.ui.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

    public static void deleteToken(HttpServletRequest request, HttpServletResponse response) {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("token-medilabo")) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                break;
            }
        }
    }
}
