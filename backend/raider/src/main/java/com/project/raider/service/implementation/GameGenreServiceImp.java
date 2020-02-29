/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.service.implementation;

import com.project.raider.dao.Game;
import com.project.raider.dao.GameGenre;
import com.project.raider.dao.Genre;
import com.project.raider.dto.GameGameDetailsDto;
import com.project.raider.exception.ApiException;
import com.project.raider.repository.GameGenreRepository;
import com.project.raider.repository.GameRepository;
import com.project.raider.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.raider.service.GameGenreService;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Sava
 */
@Service
public class GameGenreServiceImp implements GameGenreService {

    @Autowired
    private final GameRepository gameRepository;

    @Autowired
    private final GenreRepository genreRepository;

    @Autowired
    private final GameGenreRepository gameGenreRepository;

    public GameGenreServiceImp(GameRepository gameRepository,
            GenreRepository genreRepository,
            GameGenreRepository gameGenreRepository) {
        this.gameRepository = gameRepository;
        this.genreRepository = genreRepository;
        this.gameGenreRepository = gameGenreRepository;
    }

    @Override
    public GameGameDetailsDto save(GameGameDetailsDto gameGenre) {
        Game game = gameRepository.findById(gameGenre.getGameId())
                .orElseThrow(() -> new ApiException("Game not found.", HttpStatus.NOT_FOUND));
        Genre genre = genreRepository.findById(gameGenre.getGameDetailsId())
                .orElseThrow(() -> new ApiException("Genre not found.", HttpStatus.NOT_FOUND));
        gameGenreRepository.save(new GameGenre(game, genre));
        return gameGenre;
    }

    @Override
    public void delete(long id) {
        GameGenre gameGenre = gameGenreRepository.findById(id)
                .orElseThrow(() -> new ApiException("Game Genre not found.", HttpStatus.NOT_FOUND));
        gameGenreRepository.delete(gameGenre);
    }

    @Override
    public GameGenre findById(long id) {
        GameGenre gameGenre = gameGenreRepository.findById(id)
                .orElseThrow(() -> new ApiException("Game Genre not found.", HttpStatus.NOT_FOUND));
        return gameGenre;
    }

    @Override
    public List<GameGenre> findByGameId(long gameId) {
        return gameGenreRepository.findByGameId(gameId);
    }

    @Override
    public List<GameGenre> findByGenreId(long genreId) {
        return gameGenreRepository.findByGameId(genreId);
    }

    @Override
    public boolean existsByGameIdAndGenreId(long gameId, long genreId) {
        return gameGenreRepository.existsByGameIdAndGenreId(gameId, genreId);
    }
}
