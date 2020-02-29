/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.dto;

/**
 *
 * @author Sava
 */
public class UserGameDto {

    private long userId;
    private long gameId;
    private short gameFlagId;

    public UserGameDto() {
    }

    public UserGameDto(long userId, long gameId, short gameFlagId) {
        this.userId = userId;
        this.gameId = gameId;
        this.gameFlagId = gameFlagId;
    }

    public UserGameDto(long userId, long gameId) {
        this.userId = userId;
        this.gameId = gameId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public short getGameFlagId() {
        return gameFlagId;
    }

    public void setGameFlagId(short gameFlagId) {
        this.gameFlagId = gameFlagId;
    }

    @Override
    public String toString() {
        return "UserGameDto{" + "userId=" + userId + ", gameId=" + gameId + ", gameFlagId=" + gameFlagId + '}';
    }

}
