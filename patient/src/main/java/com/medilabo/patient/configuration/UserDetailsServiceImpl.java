package com.medilabo.patient.configuration;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username != null && !username.isEmpty()) {
            boolean enabled = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;

            return new org.springframework.security.core.userdetails.User(username, "",
                    enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
                    new ArrayList<GrantedAuthority>());
        } else {
            throw new UsernameNotFoundException("Invalid token");
        }
    }
}
