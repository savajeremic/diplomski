/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.controller;

import com.project.raider.dao.User;
import com.project.raider.dto.ApiResponse;
import com.project.raider.dto.PasswordDto;
import com.project.raider.dto.UserDto;
import com.project.raider.exception.ApiException;
import com.project.raider.service.UserService;
import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Sava
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ApiResponse<UserDto> saveUser(@RequestBody UserDto user) {
        try {
            return new ApiResponse<>(HttpStatus.OK.value(), "User saved successfully.", userService.save(user));
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin')")
    public ApiResponse<List<UserDto>> listUser() {
        try {
            return new ApiResponse<>(HttpStatus.OK.value(), "User list retrieved successfully.", userService.findAll());
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ApiResponse<UserDto> getUserById(@PathVariable long id) {
        try {
            return new ApiResponse<>(HttpStatus.OK.value(), "User retrieved successfully.", userService.findById(id));
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ApiResponse<UserDto> update(@RequestBody UserDto user) {
        try {
            return new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully.", userService.update(user));
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @PutMapping("/uploadAvatar/{id}")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ApiResponse<Byte[]> uploadAvatar(@RequestParam("avatar") MultipartFile file, @PathVariable long id) throws IOException {
        try {
            userService.updateAvatar(file.getBytes(), id);
            return new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully.", file.getBytes());
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @PostMapping("/updatePassword")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ApiResponse<String> updatePassword(@RequestBody PasswordDto passwordDto) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            final User user = userService.findUserByEmail(authentication.getName());
            userService.checkPasswords(user.getPassword(), passwordDto.getOldPassword());
            userService.updatePassword(passwordDto, user.getId());
            return new ApiResponse<>(HttpStatus.OK.value(), "User's password updated successfully.", true);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ApiResponse<Void> delete(@PathVariable long id) {
        try {
            userService.delete(id);
            return new ApiResponse<>(HttpStatus.OK.value(), "User deleted successfully.", null);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }
}
