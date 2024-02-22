package com.medilabo.ui.proxies;

import com.medilabo.ui.beans.DoctorBean;
import com.medilabo.ui.configuration.AuthRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "medilabo-authentication-service", url = "localhost:9005")
public interface LoginProxy {
    @GetMapping("/getUser")
    ResponseEntity<DoctorBean> getDoctorByUsername(@RequestParam String username);
    @GetMapping("/validateToken")
    ResponseEntity<String> validateToken(@RequestParam String token);
    @PostMapping("/authenticate")
    ResponseEntity<String> authenticate(@RequestBody AuthRequest request);

}
