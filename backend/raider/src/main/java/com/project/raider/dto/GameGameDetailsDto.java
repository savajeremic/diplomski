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
public class GameGameDetailsDto {
    private long gameId;
    private long gameDetailsId;

    public GameGameDetailsDto() {
    }

    public GameGameDetailsDto(long gameId, long gameDetailsId) {
        this.gameId = gameId;
        this.gameDetailsId = gameDetailsId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getGameDetailsId() {
        return gameDetailsId;
    }

    public void setGameDetailsId(long gameDetailsId) {
        this.gameDetailsId = gameDetailsId;
    }

    @Override
    public String toString() {
        return "GameGameDetailsDto{" + "gameId=" + gameId + ", gameDetailsId=" + gameDetailsId + '}';
    }
}
