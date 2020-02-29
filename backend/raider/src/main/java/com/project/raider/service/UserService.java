/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.service;

import com.project.raider.dao.User;
import com.project.raider.dao.UserRole;
import com.project.raider.dto.GameDto;
import com.project.raider.dto.PasswordDto;
import com.project.raider.dto.RegisterUser;
import com.project.raider.dto.UserDto;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Sava
 */
public interface UserService {
    
    List<UserDto> findAll();
    User findUserByEmail(String email);
    UserDto findById(long id);
    UserDto save(UserDto userDto);
    UserDto update(UserDto userDto);
    byte[] updateAvatar(byte[] avatar, long id);
    boolean updatePassword(PasswordDto passwordForm, long id);
    boolean checkPasswords(String password, String oldPassword);
    boolean existsByUsernameAndId(String username, long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsById(long id);
    void delete(long id);
}
