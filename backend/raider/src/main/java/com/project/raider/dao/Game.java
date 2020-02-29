/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    @NotNull
    @Size(min = 1, max = 65535)
    @Column(name = "NAME")
    private String name;
    @Size(max = 65535)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 65535)
    @Column(name = "COVER_IMAGE")
    private String coverImage;
    @Size(max = 65535)
    @Column(name = "VERSION")
    private String version;
    @Column(name = "RATING")
    private double rating;
    @Column(name = "VOTES")
    private long votes;
    @Column(name = "RELEASE_DATE")
    @Temporal(TemporalType.DATE)
    private Date releaseDate;
    @Column(name = "SIZE")
    private double size;
    @Column(name = "PRICE")
    private double price;
    @OneToMany(mappedBy = "game")
    private List<UserGame> userGameList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private List<GameGenre> gameGenreList;
    @OneToMany(mappedBy = "game")
    private List<GameReview> gameReviewList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private List<GameCompany> gameCompanyList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private List<GameLanguage> gameLanguageList;

    public Game() {
    }

    public Game(long id) {
        this.id = id;
    }

    public Game(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Game(long id, @NotNull @Size(min = 1, max = 65535) String name, @Size(max = 65535) String description, @Size(max = 65535) String coverImage, @Size(max = 65535) String version, double rating, long votes, Date releaseDate, double size, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coverImage = coverImage;
        this.version = version;
        this.rating = rating;
        this.votes = votes;
        this.releaseDate = releaseDate;
        this.size = size;
        this.price = price;
    }

    public Game(String name, String description, String coverImage, String version, double rating, long votes, Date releaseDate, double size, double price) {
        this.name = name;
        this.description = description;
        this.coverImage = coverImage;
        this.version = version;
        this.rating = rating;
        this.votes = votes;
        this.releaseDate = releaseDate;
        this.size = size;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public long getVotes() {
        return votes;
    }

    public void setVotes(long votes) {
        this.votes = votes;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<UserGame> getUserGameList() {
        return userGameList;
    }

    public void setUserGameList(List<UserGame> userGameList) {
        this.userGameList = userGameList;
    }

    public List<GameGenre> getGameGenreList() {
        return gameGenreList;
    }

    public void setGameGenreList(List<GameGenre> gameGenreList) {
        this.gameGenreList = gameGenreList;
    }

    public List<GameReview> getGameReviewList() {
        return gameReviewList;
    }

    public void setGameReviewList(List<GameReview> gameReviewList) {
        this.gameReviewList = gameReviewList;
    }

    public List<GameCompany> getGameCompanyList() {
        return gameCompanyList;
    }

    public void setGameCompanyList(List<GameCompany> gameCompanyList) {
        this.gameCompanyList = gameCompanyList;
    }

    public List<GameLanguage> getGameLanguageList() {
        return gameLanguageList;
    }

    public void setGameLanguageList(List<GameLanguage> gameLanguageList) {
        this.gameLanguageList = gameLanguageList;
    }

    @Override
    public String toString() {
        return "Game{" + "name=" + name + ", description=" + description
                + ", coverImage=" + coverImage + ", version=" + version
                + ", id=" + id + ", rating=" + rating + ", votes=" + votes
                + ", releaseDate=" + releaseDate + ", size=" + size
                + ", price=" + price + '}';
    }

}
