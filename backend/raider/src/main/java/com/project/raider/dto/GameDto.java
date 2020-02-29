/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.dto;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Sava
 */
public class GameDto {

    private String name, description, coverImage, version;
    private double rating, size, price;
    private long votes, id;
    private Date releaseDate;
    private List<GameDetailsDto> genres;
    private List<GameDetailsDto> companies;
    private List<GameDetailsDto> languages;
    private boolean isInCart, isInWishlist, isInOwned;

    public GameDto() {
    }

    public GameDto(long id, String name, String description, String coverImage, Date releaseDate, String version,
                   double rating, double size, double price, long votes, List<GameDetailsDto> genres,
                   List<GameDetailsDto> companies, List<GameDetailsDto> languages) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coverImage = coverImage;
        this.releaseDate = releaseDate;
        this.version = version;
        this.rating = rating;
        this.votes = votes;
        this.size = size;
        this.price = price;
        this.genres = genres;
        this.companies = companies;
        this.languages = languages;
    }

    public GameDto(String name, String description, String coverImage, String version, double rating, double size,
                   double price, long votes, long id, Date releaseDate, List<GameDetailsDto> genres,
                   List<GameDetailsDto> companies, List<GameDetailsDto> languages, boolean isInCart,
                   boolean isInWishlist, boolean isInOwned) {
        this.name = name;
        this.description = description;
        this.coverImage = coverImage;
        this.version = version;
        this.rating = rating;
        this.size = size;
        this.price = price;
        this.votes = votes;
        this.id = id;
        this.releaseDate = releaseDate;
        this.genres = genres;
        this.companies = companies;
        this.languages = languages;
        this.isInCart = isInCart;
        this.isInWishlist = isInWishlist;
        this.isInOwned = isInOwned;
    }

    public GameDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GameDto(long id) {
        this.id = id;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<GameDetailsDto> getGenres() {
        return genres;
    }

    public void setGenres(List<GameDetailsDto> genres) {
        this.genres = genres;
    }

    public List<GameDetailsDto> getCompanies() {
        return companies;
    }

    public void setCompanies(List<GameDetailsDto> companies) {
        this.companies = companies;
    }

    public List<GameDetailsDto> getLanguages() {
        return languages;
    }

    public void setLanguages(List<GameDetailsDto> languages) {
        this.languages = languages;
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
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

    public long getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isIsInCart() {
        return isInCart;
    }

    public void setIsInCart(boolean isInCart) {
        this.isInCart = isInCart;
    }

    public boolean isIsInWishlist() {
        return isInWishlist;
    }

    public void setIsInWishlist(boolean isInWishlist) {
        this.isInWishlist = isInWishlist;
    }

    public boolean isIsInOwned() {
        return isInOwned;
    }

    public void setIsInOwned(boolean isInOwned) {
        this.isInOwned = isInOwned;
    }

    @Override
    public String toString() {
        return "GameDto{" + ", id=" + id
                + ", name=" + name + ", description=" + description
                + ", cover_image=" + coverImage + ", release_date=" + releaseDate
                + ", version=" + version + ", rating=" + rating + ", size=" + size
                + ", price=" + price + ", votes=" + votes + "genres=" + genres
                + ", companies=" + companies + ", languages=" + languages
                + ", in cart=" + isInCart + ", in wishlsit=" + isInWishlist
                + ", in owned=" + isInOwned
                + " }";
    }
}
