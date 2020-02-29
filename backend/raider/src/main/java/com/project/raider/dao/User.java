/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Blob;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

/**
 *
 * @author Sava
 */
@Entity
@Table(name = "user")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotEmpty(message = "Please provide a username")
    @NotNull()
    @Size(min = 1, max = 65535)
    @Column(name = "USERNAME")
    private String username;
    
    @NotEmpty(message = "Please provide a password")
    @NotNull
    @Size(min = 1, max = 65535)
    @Column(name = "PASSWORD")
    private String password;
    
    @NotEmpty(message = "Please provide an email")
    @NotNull
    @Size(min = 1, max = 65535)
    @Column(name = "EMAIL")
    private String email;
    
    @Size(max = 65535)
    @Column(name = "FULLNAME")
    private String fullname;
    
    @Size(max = 65535)
    @Column(name = "COUNTRY")
    private String country;
    
    @Lob
    @Column(name = "AVATAR")
    private byte[] avatar;
    
    @Column(name = "BIRTHDAY")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthday;
    
    @Nullable
    @JoinColumn(name = "USER_ROLE_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false, targetEntity = com.project.raider.dao.UserRole.class)
    private UserRole userRoleId;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<UserGame> userGameList;
    
    @OneToMany(mappedBy = "user")
    private List<GameReview> gameReviewList;

    @NotNull()
    @Column(name = "ACTIVE")
    private boolean active;
    
    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(String username, String fullname, String country, byte[] avatar, Date birthday) {
        this.username = username;
        this.fullname = fullname;
        this.country = country;
        this.avatar = avatar;
        this.birthday = birthday;
    }

    public User(Long id, String username, String password, String email, String fullname, String country, byte[] avatar, Date birthday) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.country = country;
        this.avatar = avatar;
        this.birthday = birthday;
    }
    
    public User(String username, String password, String email, String fullname, Date birthday, String country, boolean active, UserRole userRoleId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.birthday = birthday;
        this.country = country;
        this.avatar = avatar;
        this.active = active;
        this.userRoleId = userRoleId;
    }

    public User(String username, String password, String email, String fullname, Date birthday, String country, byte[] avatar, boolean active, UserRole userRoleId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.birthday = birthday;
        this.country = country;
        this.avatar = avatar;
        this.active = active;
        this.userRoleId = userRoleId;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<UserGame> getUserGameList() {
        return userGameList;
    }

    public void setUserGameList(List<UserGame> userGameList) {
        this.userGameList = userGameList;
    }

    public List<GameReview> getGameReviewList() {
        return gameReviewList;
    }

    public void setGameReviewList(List<GameReview> gameReviewList) {
        this.gameReviewList = gameReviewList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public UserRole getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(UserRole userRoleId) {
        this.userRoleId = userRoleId;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", password=" 
                + password + ", email=" + email + ", fullname=" + fullname + 
                ", country=" + country + ", avatar=" + avatar + ", birthday=" 
                + birthday + ", userRoleId=" + userRoleId.getName() + ", active=" 
                + active + '}';
    }

    

}
