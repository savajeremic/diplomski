/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.repository;

import com.project.raider.dao.User;
import com.project.raider.dao.UserRole;
import com.project.raider.dto.UserDto;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Sava
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    
    @Transactional
    @Modifying
    @Query(value="UPDATE User u SET u.username =:username, "
            + " u.fullname =:fullname, "
            + " u.country =:country, "
            + " u.birthday =:birthday "
            + " WHERE u.id =:userId")
    int updateUser(@Param("userId")long userId, @Param("username")String username, @Param("fullname")String fullname,
                   @Param("country")String country, @Param("birthday")Date birthday);
    
    @Transactional
    @Modifying
    @Query(value="UPDATE User u SET u.avatar =:avatar WHERE u.id =:userId")
    int updateAvatar(@Param("avatar")byte[] avatar, @Param("userId")long userId);
    
    @Transactional
    @Modifying
    @Query(value="UPDATE User u SET u.password =:password WHERE u.id =:userId")
    int updatePassword(@Param("password")String password, @Param("userId")long id);
    
    boolean existsByUsername(String username);
    boolean existsByUsernameAndId(String username, long id);
    boolean existsByEmail(String email);
}
