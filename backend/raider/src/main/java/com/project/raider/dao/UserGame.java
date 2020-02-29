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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Sava
 */
@Entity
@Table(name = "user_game")
public class UserGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private User user;

    @JoinColumn(name = "GAME_ID", referencedColumnName = "ID")
    @ManyToOne
    private Game game;

    @JoinColumn(name = "GAME_FLAG_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private GameFlag gameFlag;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userGameId")
    private List<UserGameOrders> userGameOrdersList;

    public UserGame() {
    }

    public UserGame(Long id) {
        this.id = id;
    }

    public UserGame(User user, Game game, GameFlag gameFlag) {
        this.user = user;
        this.game = game;
        this.gameFlag = gameFlag;
    }

    public UserGame(long id, User user, Game game, GameFlag gameFlag) {
        this.id = id;
        this.user = user;
        this.game = game;
        this.gameFlag = gameFlag;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameFlag getGameFlag() {
        return gameFlag;
    }

    public void setGameFlag(GameFlag gameFlag) {
        this.gameFlag = gameFlag;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<UserGameOrders> getUserGameOrdersList() {
        return userGameOrdersList;
    }

    public void setUserGameOrdersList(List<UserGameOrders> userGameOrdersList) {
        this.userGameOrdersList = userGameOrdersList;
    }

    @Override
    public String toString() {
        return "UserGame{" + "user=" + user + ", id=" + id + ", gameFlag=" + gameFlag + ", game=" + game + '}';
    }

}
