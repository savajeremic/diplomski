/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.service.implementation;

import com.project.raider.dao.Game;
import com.project.raider.dao.GameLanguage;
import com.project.raider.dao.Language;
import com.project.raider.dto.GameGameDetailsDto;
import com.project.raider.exception.ApiException;
import com.project.raider.repository.GameLanguageRepository;
import com.project.raider.repository.GameRepository;
import com.project.raider.repository.LanguageRepository;
import com.project.raider.service.GameLanguageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sava
 */
@Service
public class GameLanguageServiceImp implements GameLanguageService {

    @Autowired
    private final GameRepository gameRepository;

    @Autowired
    private final LanguageRepository languageRepository;

    @Autowired
    private final GameLanguageRepository gameLanguageRepository;

    public GameLanguageServiceImp(GameRepository gameRepository,
            LanguageRepository languageRepository,
            GameLanguageRepository gameLanguageRepository) {
        this.gameRepository = gameRepository;
        this.languageRepository = languageRepository;
        this.gameLanguageRepository = gameLanguageRepository;
    }

    @Override
    public GameGameDetailsDto save(GameGameDetailsDto gameLanguage) {
        Game game = gameRepository.findById(gameLanguage.getGameId())
                .orElseThrow(() -> new ApiException("Game not found.", HttpStatus.NOT_FOUND));
        Language language = languageRepository.findById(gameLanguage.getGameDetailsId())
                .orElseThrow(() -> new ApiException("Language not found.", HttpStatus.NOT_FOUND));
        gameLanguageRepository.save(new GameLanguage(game, language));
        return gameLanguage;
    }

    @Override
    public void delete(long id) {
        GameLanguage gameLanguage = gameLanguageRepository.findById(id)
                .orElseThrow(() -> new ApiException("Game Language not found.", HttpStatus.NOT_FOUND));
        gameLanguageRepository.delete(gameLanguage);
    }

    @Override
    public GameLanguage findById(long id) {
        GameLanguage gamelanguage = gameLanguageRepository.findById(id)
                .orElseThrow(() -> new ApiException("Game Language not found.", HttpStatus.NOT_FOUND));
        return gamelanguage;
    }

    @Override
    public List<GameLanguage> findByGameId(long gameId) {
        return gameLanguageRepository.findByGameId(gameId);
    }

    @Override
    public List<GameLanguage> findByLanguageId(long languageId) {
        return gameLanguageRepository.findByLanguageId(languageId);
    }

    @Override
    public boolean existsByGameIdAndLanguageId(long gameId, long languageId) {
        return gameLanguageRepository.existsByGameIdAndLanguageId(gameId, languageId);
    }
}
