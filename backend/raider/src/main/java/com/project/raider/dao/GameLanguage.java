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
@Table(name = "game_language")
public class GameLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JoinColumn(name = "GAME_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Game game;

    @JoinColumn(name = "LANGUAGE_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Language language;

    public GameLanguage() {
    }

    public GameLanguage(Long id, Game game, Language language) {
        this.id = id;
        this.game = game;
        this.language = language;
    }

    public GameLanguage(Game game, Language language) {
        this.game = game;
        this.language = language;
    }

    public GameLanguage(Long id) {
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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "GameLanguage{" + "id=" + id + '}';
    }

}
