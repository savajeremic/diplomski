/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.dao;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Sava
 */
@Entity
@Table(name = "game_flag")
public class GameFlag {
    
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "NAME")
    private String name;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private short id;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gameFlag")
    private List<UserGame> userGameList;

    public GameFlag() {
    }

    public GameFlag(short id) {
        this.id = id;
    }

    public GameFlag(short id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public List<UserGame> getUserGameList() {
        return userGameList;
    }

    public void setUserGameList(List<UserGame> userGameList) {
        this.userGameList = userGameList;
    }

    @Override
    public String toString() {
        return "GameFlag{" + "name=" + name + ", id=" + id + ", userGameList=" + userGameList + '}';
    }
}
