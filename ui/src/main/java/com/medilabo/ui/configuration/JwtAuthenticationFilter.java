package com.medilabo.ui.configuration;

import com.medilabo.ui.proxies.LoginProxy;
import com.medilabo.ui.util.JwtUtil;
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
    private LoginProxy loginProxy;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = JwtUtil.findToken(request);

        if(!jwtToken.isEmpty()) {
            try {
                String username = loginProxy.validateToken(jwtToken).getBody();

                if (!username.isEmpty()) {

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken uPAT = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    uPAT.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(uPAT);

                    log.info("User authenticated with success");

                } else {
                    log.info("Invalid token (wrong user or expired token");
                }
            } catch (Exception e) {
                log.error("Authentication error: " + e.getMessage());
            }
        } else {
            log.info("No cookie found");
        }
        filterChain.doFilter(request, response);
    }
}
