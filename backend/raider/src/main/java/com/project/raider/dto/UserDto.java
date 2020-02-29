/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.dto;

import com.project.raider.dao.UserRole;
import java.io.IOException;
import java.util.Date;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Sava
 */
public class UserDto {
    private long id;
    private String username, password, email, fullname, country;
    private byte[] avatar;
    private Date birthday;
    private UserRole userRole;

    public UserDto() {
    }

    public UserDto(long id) {
        this.id = id;
    }

    public UserDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    public UserDto(long id, String username, String email, String fullname, byte[] avatar) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullname = fullname;
        this.avatar = avatar;
    }

    public UserDto(long id, String username, String email, String fullname, String country, Date birthday) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullname = fullname;
        this.country = country;
        this.birthday = birthday;
    }

    public UserDto(long id, String username, String password, String email, String fullname, Date birthday, String country, byte[] avatar, UserRole userRole) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.birthday = birthday;
        this.country = country;
        this.avatar = avatar;
        this.userRole = userRole;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "UserDTO{" + "id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", fullname=" + fullname + ", birthday=" + birthday + ", country=" + country + ", avatar=" + avatar + ", userRole=" + userRole + '}';
    }
}
