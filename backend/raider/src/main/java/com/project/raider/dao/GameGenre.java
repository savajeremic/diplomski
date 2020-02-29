/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Sava
 */
@Entity
@Table(name = "game_genre")
public class GameGenre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JoinColumn(name = "GAME_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Game game;

    @JoinColumn(name = "GENRE_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Genre genre;

    public GameGenre() {
    }

    public GameGenre(Long id, Game game, Genre genre) {
        this.id = id;
        this.game = game;
        this.genre = genre;
    }

    public GameGenre(Game game, Genre genre) {
        this.game = game;
        this.genre = genre;
    }

    public GameGenre(Long id) {
        this.id = id;
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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "GameGenre{" + "id=" + id + '}';
    }

}
