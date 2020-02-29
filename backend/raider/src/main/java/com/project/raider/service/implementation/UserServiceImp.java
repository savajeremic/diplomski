/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.service.implementation;

import com.project.raider.configuration.Constants;
import com.project.raider.dao.User;
import com.project.raider.dao.UserRole;
import com.project.raider.dto.PasswordDto;
import com.project.raider.dto.UserDto;
import com.project.raider.exception.ApiException;
import com.project.raider.repository.UserRepository;
import com.project.raider.repository.UserRoleRepository;
import com.project.raider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Sava
 */
@Primary
@Service
public class UserServiceImp implements UserDetailsService, UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserRoleRepository userRoleRepository;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository,
                          UserRoleRepository userRoleRepository,
                          BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findUserByEmail(email);
        List<GrantedAuthority> authList = new ArrayList<>();
        userRoleRepository.findAll().stream().filter((role)
                -> (user.getUserRoleId().getName().equals(role.getName())
                && Objects.equals(user.getUserRoleId().getId(), role.getId()))
        ).forEachOrdered((role) -> {
            authList.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authList);
    }

    @Override
    public List<UserDto> findAll() {
        List<UserDto> userDtoList = new ArrayList<>();
        userRepository.findAll().stream().forEach((user)
                -> userDtoList.add(userToDto(user))
        );
        return userDtoList;
    }

    @Override
    public UserDto findById(long id) {
        return userToDto(userRepository.findById(id).orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND)));
    }

    @Override
    public void delete(long id) {
        if(!existsById(id)) {
            throw new ApiException("User not found", HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDto save(UserDto userDto) {
        long userId = userDto.getId();
        String username = userDto.getUsername(), email = userDto.getEmail(), password = userDto.getPassword();

        if (isEmptyOrNull(username, password, email)) {
            throw new ApiException("Username, password and email fields must be filled.", HttpStatus.BAD_REQUEST);
        }
        if (!existsByUsernameAndId(username, userId) && existsByUsername(username)) {
            throw new ApiException("Username already exists.", HttpStatus.BAD_REQUEST);
        }
        if (existsByEmail(email) && !existsById(userId)) {
            throw new ApiException("Email already exists.", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setId(userId);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFullname(userDto.getFullname());
        user.setCountry(userDto.getCountry());
        user.setAvatar(userDto.getAvatar());
        user.setBirthday(userDto.getBirthday());
        user.setUserRoleId(userRoleRepository.findByName(Constants.ROLE_USER));
        user.setActive(true);

        User newUser = userRepository.save(user);

        userDto.setId(newUser.getId());
        userDto.setUserRole(user.getUserRoleId());
        return userDto;
    }

    @Override
    public UserDto update(UserDto userDto) {
        long userId = userDto.getId();
        String username = userDto.getUsername(), email = userDto.getEmail();

        if (isEmptyOrNull(username, email)) {
            throw new ApiException("Username and email fields must be filled.", HttpStatus.BAD_REQUEST);
        }
        if (!existsByUsernameAndId(username, userId) && existsByUsername(username)) {
            throw new ApiException("Username already exists.", HttpStatus.BAD_REQUEST);
        }
        if (existsByEmail(email) && !existsById(userId)) {
            throw new ApiException("Email already exists.", HttpStatus.BAD_REQUEST);
        }
        userRepository.updateUser(userId, username, userDto.getFullname(), userDto.getCountry(), userDto.getBirthday());
        return userDto;
    }

    @Override
    public byte[] updateAvatar(byte[] avatar, long id) {
        userRepository.updateAvatar(avatar, id);
        return avatar;
    }

    @Override
    public boolean updatePassword(PasswordDto passwordForm, long id) {
        String newPassword = passwordForm.getNewPassword();
        String encodedPassword = passwordEncoder.encode(newPassword);
        userRepository.updatePassword(encodedPassword, id);
        return true;
    }

    @Override
    public boolean checkPasswords(String password, String oldPassword) {
        boolean doesMatch = passwordEncoder.matches(oldPassword, password);
        if(!doesMatch) {
            throw new ApiException("Old password doesn't match", HttpStatus.NOT_ACCEPTABLE);
        }
        return doesMatch;
    }

    @Override
    public boolean existsByUsernameAndId(String username, long id) {
        return userRepository.existsByUsernameAndId(username, id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsById(long id) {
        return userRepository.existsById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()
                -> new UsernameNotFoundException("No user found with email='" + email + "'"));
        return user;
    }

    private UserDto userToDto(User user) {
        UserDto userDto = new UserDto(user.getId(), user.getUsername(),
                user.getPassword(), user.getEmail(), user.getFullname(),
                user.getBirthday(), user.getCountry(),
                user.getAvatar(), user.getUserRoleId());
        return userDto;
    }

    private boolean isEmptyOrNull(String... strings) {
        for (String s : strings) {
            if (s == null || s.equals("") || s.length() == 0) {
                return true;
            }
        }
        return false;
    }
}
