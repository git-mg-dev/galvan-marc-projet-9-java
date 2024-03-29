package com.medilabo.ui.configuration;

import com.medilabo.ui.beans.DoctorBean;
import com.medilabo.ui.proxies.GatewayProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private GatewayProxy gatewayProxy;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ResponseEntity<DoctorBean> doctorResponse = gatewayProxy.getDoctorByUsername(username);

        if(doctorResponse.getStatusCode().is2xxSuccessful()) {
            DoctorBean doctor = doctorResponse.getBody();
            boolean enabled = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;

            return new org.springframework.security.core.userdetails.User(doctor.getUsername(), doctor.getPassword(),
                    enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
                    new ArrayList<GrantedAuthority>());
        } else {
            throw new UsernameNotFoundException("Username not found");
        }

    }
}
