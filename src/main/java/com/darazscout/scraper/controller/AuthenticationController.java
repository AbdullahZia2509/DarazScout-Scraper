package com.darazscout.scraper.controller;

import com.darazscout.scraper.model.AuthenticationRequest;
import com.darazscout.scraper.model.AuthenticationResponse;
import com.darazscout.scraper.service.MyUserDetailsService;
import com.darazscout.scraper.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/ac")
@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    //@Autowired
    //private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Invalid username/password");
        }
        return jwtTokenUtil.generateToken(authenticationRequest.getUsername());

    }

}
