/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.dto;

import java.util.Date;

/**
 *
 * @author Sava
 */
public class GameReviewDto {

    private long id;
    private long gameId;
    private UserDto user;
    private double rating;
    private String title;
    private String comment;
    private Date reviewDate;

    public GameReviewDto() {
    }

    public GameReviewDto(long id, long gameId, UserDto user, double rating, String title, String comment, Date reviewDate) {
        this.id = id;
        this.gameId = gameId;
        this.user = user;
        this.rating = rating;
        this.comment = comment;
        this.title = title;
        this.reviewDate = reviewDate;
    }

    public GameReviewDto(long gameId, UserDto user, double rating, String title, String comment, Date reviewDate) {
        this.gameId = gameId;
        this.user = user;
        this.rating = rating;
        this.title = title;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    @Override
    public String toString() {
        return "GameReviewDto{" + "id=" + id + ", gameId=" + gameId + ", user=" + user + ", rating=" + rating + ", title=" + title + ", comment=" + comment + ", reviewDate=" + reviewDate + '}';
    }

}
