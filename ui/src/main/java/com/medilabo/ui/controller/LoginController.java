package com.medilabo.ui.controller;

import com.medilabo.ui.configuration.AuthRequest;
import com.medilabo.ui.proxies.LoginProxy;
import com.medilabo.ui.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;

@Log4j2
@Controller
public class LoginController {
    @Autowired
    private LoginProxy loginProxy;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        //Check cookies
        if(!JwtUtil.findToken(request).isEmpty()) {
            return "redirect:home";
        }

        return "login";
    }

    @PostMapping("/token")
    public String generateToken(HttpSession session, @ModelAttribute AuthRequest authRequest, HttpServletRequest request, HttpServletResponse response) {
        //User authentication
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            log.error("User authentication failed: " + e.getMessage());
            return "redirect:login?error";
        }

        //Getting user details & creating cookie
        try {
            ResponseEntity<String> responseEntity = loginProxy.authenticate(authRequest);
            if(!responseEntity.getStatusCode().is2xxSuccessful()) {
                return "redirect:login?error";
            }

            final String token = responseEntity.getBody();

            Cookie cookie = new Cookie("token-medilabo", token);
            cookie.setMaxAge(Integer.MAX_VALUE);
            response.addCookie(cookie);
            return "redirect:home";

        } catch(Exception e) {
            log.error("User authentication failed: " + e.getMessage());
            return "redirect:login?error";
        }
    }

    //TODO: add logout link somewhere
    @PostMapping("/log_out")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("token-medilabo")) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                break;
            }
        }

        return "redirect:/login?logout";
    }
}
