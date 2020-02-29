package com.project.raider.service.implementation;

import com.project.raider.dao.User;
import com.project.raider.dao.UserRole;
import com.project.raider.dto.PasswordDto;
import com.project.raider.dto.UserDto;
import com.project.raider.exception.ApiException;
import com.project.raider.repository.UserRepository;
import com.project.raider.repository.UserRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class UserServiceImpTest {

    @InjectMocks
    private UserServiceImp userServiceImp;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserRoleRepository userRoleRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private User user;
    private UserDto userDto;
    private UserRole userRoleUser, userRoleAdmin;
    private PasswordDto pwdDto;
    private String userEmail = "admin@adminson.com", userUsername = "admin96", encryptedPassword = "12fjfe838fm1kjsnzxn10s0x82n";
    private long userId = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userRoleUser = new UserRole((short) 1, "user");
        userRoleAdmin = new UserRole((short) 2, "admin");
        user = new User(userUsername, encryptedPassword, userEmail, "Sava Jeremic", new Date(),
                "Adminton", null, true, userRoleAdmin);
        user.setId(userId);
        userDto = new UserDto(userUsername, encryptedPassword, userEmail);
        pwdDto = new PasswordDto(user.getPassword(), "newPassword");

    }

    @Test
    @DisplayName("Finding user by id!")
    void findById_Success() {
        when(userRepository.findById( anyLong() )).thenReturn(Optional.of(user));
        UserDto userDto = userServiceImp.findById(1L);
        assertNotNull(userDto);
        assertEquals(1L, userDto.getId());
    }

    @Test
    @DisplayName("Finding user by id: Throwing exception, not found")
    void findById_Exception() {
        when(userRepository.findById( anyLong() )).thenReturn(Optional.empty());
        assertThrows(ApiException.class, () -> userServiceImp.findById(userId));
    }

    @Test
    @DisplayName("Finding user by email!")
    void testFindUserByEmail_Success() {
        when(userRepository.findByEmail( anyString() )).thenReturn(Optional.of(user));
        User user1 = userServiceImp.findUserByEmail(userEmail);
        assertNotNull(user1);
        assertEquals(userUsername, user1.getUsername());
    }

    @Test
    @DisplayName("Finding user by email: Throwing exception, not found")
    void testFindUserByEmail_Exception() {
        when(userRepository.findByEmail( anyString() )).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userServiceImp.findUserByEmail(userEmail));
    }

    @Test
    @DisplayName("Saving user!")
    void testSaveUser_Success() {
        when(userRepository.save( any(User.class) )).thenReturn(user);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
        userDto.setPassword(bCryptPasswordEncoder.encode("testPassword"));
        when(userRoleRepository.findByName(anyString())).thenReturn(userRoleUser);
        userDto.setUserRole(userRoleRepository.findByName("user"));
        userServiceImp.save(userDto);
        assertNotNull(userDto);
        assertEquals("admin96", userDto.getUsername());
    }

    @Test
    @DisplayName("Save user: Throwing exception if username, email or password are Empty or Null")
    void testSaveUser_WrongCredentials_Exception() {
        when(userRepository.save( any(User.class) )).thenReturn(user);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
        userDto.setPassword(bCryptPasswordEncoder.encode("testPassword"));
        when(userRoleRepository.findByName(anyString())).thenReturn(userRoleUser);
        userDto.setUserRole(userRoleRepository.findByName("user"));
        //testing when username is empty
        userDto.setUsername("");
        assertThrows(ApiException.class, () -> userServiceImp.save(userDto));
        //testing when email is empty
        userDto.setUsername("test");
        userDto.setEmail("");
        assertThrows(ApiException.class, () -> userServiceImp.save(userDto));
        //testing when email is empty
        userDto.setEmail("test@email.com");
        userDto.setPassword("");
        assertThrows(ApiException.class, () -> userServiceImp.save(userDto));
        //testing when username is null
        userDto.setUsername(null);
        assertThrows(ApiException.class, () -> userServiceImp.save(userDto));
        //testing when email is null
        userDto.setUsername("test");
        userDto.setEmail(null);
        assertThrows(ApiException.class, () -> userServiceImp.save(userDto));
        //testing when email is null
        userDto.setEmail("test@email.com");
        userDto.setPassword(null);
        assertThrows(ApiException.class, () -> userServiceImp.save(userDto));
    }

    @Test
    @DisplayName("Updating user!")
    void testUpdateUser_Success() {
        when(userRepository.updateUser( anyLong(), anyString(), anyString(), anyString(), any(Date.class) )).thenReturn(1);
        userServiceImp.update(userDto);
        assertNotNull(userDto);
        assertEquals("admin96", userDto.getUsername());
    }

    @Test
    @DisplayName("Updating user: Throwing exception if username or email are Empty")
    void testUpdateUser_Exception_UsernameFieldMustBeFilled() {
        when(userRepository.updateUser( anyLong(), anyString(), anyString(), anyString(), any(Date.class) )).thenReturn(1);
        userDto.setUsername("");
        ApiException apiException = assertThrows(ApiException.class, () -> userServiceImp.update(userDto));
        assertEquals("Username and email fields must be filled.", apiException.getMessage());

        userDto.setUsername("test");
        userDto.setEmail("");
        apiException = assertThrows(ApiException.class, () -> userServiceImp.update(userDto));
        assertEquals("Username and email fields must be filled.", apiException.getMessage());
    }

    @Test
    @DisplayName("Updating user password!")
    void updatePassword_Success() {
        when(userRepository.updatePassword(anyString(), anyLong())).thenReturn(1);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("newPasswordEncodedCode");
        userDto.setPassword(bCryptPasswordEncoder.encode("newPassword"));
        boolean hasUpdated = userServiceImp
                .updatePassword(pwdDto, user.getId());
        assertTrue(hasUpdated);
        assertEquals("newPasswordEncodedCode", userDto.getPassword());
    }

    @Test
    @DisplayName("Checking if old passwords match")
    void checkPasswords_Success() {
        when(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(true);
        boolean doMatch = userServiceImp.checkPasswords(pwdDto.getOldPassword(), userDto.getPassword());
        assertTrue(doMatch);
        assertEquals(pwdDto.getOldPassword(), userDto.getPassword());
    }

    @Test
    @DisplayName("Old passwords don't match, Exception")
    void checkPasswords_Error() {
        when(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(false);
        assertThrows(ApiException.class, () -> userServiceImp.checkPasswords(pwdDto.getOldPassword(), "asd"));
        assertNotEquals(pwdDto.getOldPassword(), "asd");
    }
}