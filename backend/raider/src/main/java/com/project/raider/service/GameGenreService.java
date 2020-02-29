/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.service;

import com.project.raider.dao.GameGenre;
import com.project.raider.dto.GameGameDetailsDto;
import java.util.List;

/**
 *
 * @author Sava
 */
public interface GameGenreService {
    GameGameDetailsDto save(GameGameDetailsDto gameGenre);
    GameGenre findById(long id);
    List<GameGenre> findByGameId(long gameId);
    List<GameGenre> findByGenreId(long genreId);
    boolean existsByGameIdAndGenreId(long gameId, long genreId);
    void delete(long id);
}
