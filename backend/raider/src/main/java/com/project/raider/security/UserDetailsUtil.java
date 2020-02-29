/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.security;

import com.project.raider.configuration.Constants;
import com.project.raider.dao.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Sava
 */
public class UserDetailsUtil implements UserDetails {
    
    private final User user;
    
    public UserDetailsUtil(User user) {
        this.user = user;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority authorityUser = new SimpleGrantedAuthority(Constants.ROLE_USER);
        GrantedAuthority authorityAdmin = new SimpleGrantedAuthority(Constants.ROLE_ADMIN);
        authorities.add(authorityUser);
        authorities.add(authorityAdmin);
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.isActive();// == 1
    }
    
}
