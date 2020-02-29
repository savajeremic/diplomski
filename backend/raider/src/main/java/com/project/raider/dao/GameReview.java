/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.dao;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Sava
 */
@Entity
@Table(name = "game_review")
public class GameReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JoinColumn(name = "GAME_ID", referencedColumnName = "ID")
    @ManyToOne
    private Game game;

    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @ManyToOne
    private User user;

    @NotNull
    @Column(name = "USER_RATING")
    private double userRating;
    
    @Size(max = 65535)
    @Column(name = "TITLE")
    private String title;

    @Size(max = 65535)
    @Column(name = "COMMENT")
    private String comment;

    @Basic(optional = false)
    @NotNull
    @Column(name = "REVIEW_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reviewDate;

    public GameReview() {
    }

    public GameReview(Long id) {
        this.id = id;
    }

    public GameReview(Long id, double userRating) {
        this.id = id;
        this.userRating = userRating;
    }

    public GameReview(Long id, Game game, User user, double userRating, String title, String comment, Date reviewDate) {
        this.id = id;
        this.game = game;
        this.user = user;
        this.userRating = userRating;
        this.title = title;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public GameReview(Game game, User user, double userRating, String title, String comment, Date reviewDate) {
        this.game = game;
        this.user = user;
        this.userRating = userRating;
        this.title = title;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
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
        return "GameReview{" + "id=" + id + ", game=" + game + ", user=" + user + ", userRating=" + userRating + ", comment=" + comment + ", reviewDate=" + reviewDate + '}';
    }

}
