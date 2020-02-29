/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.controller;

import com.project.raider.security.JwtTokenUtil;
import com.project.raider.dto.ApiResponse;
import com.project.raider.dto.AuthToken;
import com.project.raider.dto.LoginUser;
import com.project.raider.dao.User;
import com.project.raider.exception.ApiException;
import com.project.raider.repository.UserRepository;
import com.project.raider.service.UserService;
import com.project.raider.service.implementation.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Sava
 */

@RestController
@RequestMapping("")
public class AuthenticationController {

    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JwtTokenUtil jwtTokenUtil;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private UserServiceImp userDetailsService;

    public AuthenticationController(AuthenticationManager authenticationManager, 
            JwtTokenUtil jwtTokenUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResponse<AuthToken> login(@RequestBody LoginUser loginUser) throws AuthenticationException {
        String email, password;
        try {
            email = loginUser.getEmail();
            password = loginUser.getPassword();
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
            final User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ApiException("No user "
                    + "found " + "with email='" + email + "'"));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            final String token = jwtTokenUtil.generateToken(user);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            AuthToken authToken = new AuthToken(token, user.getEmail(), user.getUsername(),
                            userDetails.getAuthorities().stream().findFirst().get().getAuthority(),
                            user.getAvatar(), user.getId());
            return new ApiResponse<>(HttpStatus.OK.value(), "Login Success", authToken);
        } catch (AuthenticationException e) {
            System.out.println("exception e: " + e.getMessage());
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(),
                    e.getMessage() + " - Login Failed, check your email and password again");
        }
    }
}
