package com.medilabo.notes.configuration;

import com.medilabo.notes.proxy.AuthProxy;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private AuthProxy authProxy;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("medilabo-token");

        if(token != null && !token.isEmpty()) {
            ResponseEntity<String> authUserResponse = authProxy.validateToken(token);

            if (authUserResponse.hasBody() && !authUserResponse.getBody().isEmpty()) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(authUserResponse.getBody());

                UsernamePasswordAuthenticationToken uPAT = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                uPAT.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(uPAT);

                //log.info("Valid token");

            } else {
                log.info("Invalid token");
            }
        } else {
            log.info("Empty token");
        }
        filterChain.doFilter(request, response);
    }
}
