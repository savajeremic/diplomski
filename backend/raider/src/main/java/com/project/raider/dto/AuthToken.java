/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.dto;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author Sava
 */
public class AuthToken {

    private String token;
    private String email;
    private String username;
    private String role;
    private byte[] avatar;
    private long id;

    public AuthToken() {
    }

    public AuthToken(String token, String email, String username, String role, byte[] avatar, long id) {
        this.token = token;
        this.email = email;
        this.username = username;
        this.role = role;
        this.avatar = avatar;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "AuthToken{" + "token=" + token + ", email=" + email + ", username=" + username + ", role=" + role + ", avatar=" + avatar + ", id=" + id + '}';
    }

}
