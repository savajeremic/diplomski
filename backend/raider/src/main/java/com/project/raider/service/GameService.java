/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.service;

import com.project.raider.dao.Company;
import com.project.raider.dao.Game;
import com.project.raider.dao.GameCompany;
import com.project.raider.dao.GameGenre;
import com.project.raider.dao.GameLanguage;
import com.project.raider.dao.Genre;
import com.project.raider.dao.Language;
import com.project.raider.dto.GameDto;
import java.util.List;

/**
 *
 * @author Sava
 */
public interface GameService {
    GameDto save(GameDto gameDto);
    GameDto getById(long id);
    List<GameDto> getAllGames();
    boolean existsByName(String name);
    boolean existsById(long id);
    double rateGame(long gameId, double newUserRating);
    void delete(long gameId);
}
