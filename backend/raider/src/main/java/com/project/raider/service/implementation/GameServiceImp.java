/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.service.implementation;

import com.project.raider.dao.Game;
import com.project.raider.dto.GameDto;
import com.project.raider.dto.GameGameDetailsDto;
import com.project.raider.exception.ApiException;
import com.project.raider.repository.GameRepository;
import com.project.raider.service.GameCompanyService;
import com.project.raider.service.GameService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.raider.service.GameGenreService;
import com.project.raider.service.GameLanguageService;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Sava
 */
@Service
public class GameServiceImp implements GameService {

    @Autowired
    private final GameRepository gameRepository;

    @Autowired
    private final GameGenreService gameGenreService;

    @Autowired
    private final GameCompanyService gameCompanyService;

    @Autowired
    private final GameLanguageService gameLanguageService;

    @Autowired
    private final DaoToDtoConverter converter;

    public GameServiceImp(GameRepository gameRepository,
            GameGenreService gameGenreService,
            GameCompanyService gameCompanyService,
            GameLanguageService gameLanguageService,
            DaoToDtoConverter converter) {
        this.gameRepository = gameRepository;
        this.gameGenreService = gameGenreService;
        this.gameCompanyService = gameCompanyService;
        this.gameLanguageService = gameLanguageService;
        this.converter = converter;
    }

    public GameDto gameToDtoConverter(Game game) {
        return converter.gameToDto(game);
    }

    @Override
    public GameDto getById(long id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new ApiException("Game not found.", HttpStatus.NOT_FOUND));
        return gameToDtoConverter(game);
    }

    @Override
    public List<GameDto> getAllGames() {
        List<GameDto> gameDtoList = new ArrayList<>();
        gameRepository.findByBestSelling().stream().forEach((game) -> {
            GameDto gameDto = gameToDtoConverter(game);
            gameDtoList.add(gameDto);
        });

        return gameDtoList;
    }

    @Override
    public boolean existsByName(String name) {
        return gameRepository.existsByName(name);
    }

    @Override
    public GameDto save(GameDto gameDto) {
        Game game = new Game(gameDto.getName(), gameDto.getDescription(),
                gameDto.getCoverImage(), gameDto.getVersion(),
                gameDto.getRating(), gameDto.getVotes(),
                gameDto.getReleaseDate(), gameDto.getSize(), gameDto.getPrice());
        boolean existsById = gameRepository.existsById(gameDto.getId());
        if (gameRepository.existsByName(gameDto.getName()) && !existsById) {
            throw new ApiException("Game already exists by that name.");
        }
        if (existsById) {
            game.setId(gameDto.getId());
        }
        gameRepository.save(game);
        deleteGameDetailsByGameId(game.getId());
        if(gameDto.getGenres() != null || gameDto.getCompanies() != null || gameDto.getLanguages() != null) {
            if (!gameDto.getGenres().isEmpty()) {
                gameDto.getGenres().stream().forEach((gg) -> {
                    GameGameDetailsDto gameGenre = new GameGameDetailsDto(game.getId(), gg.getId());
                    if (!gameGenreService.existsByGameIdAndGenreId(game.getId(), gg.getId())) {
                        gameGenreService.save(gameGenre);
                    }
                });
            }

            if (!gameDto.getCompanies().isEmpty()) {
                gameDto.getCompanies().stream().forEach((gc) -> {
                    GameGameDetailsDto gameCompany = new GameGameDetailsDto(game.getId(), gc.getId());
                    if (!gameCompanyService.existsByGameIdAndCompanyId(game.getId(), gc.getId())) {
                        gameCompanyService.save(gameCompany);
                    }
                });
            }
            if (!gameDto.getLanguages().isEmpty()) {
                gameDto.getLanguages().stream().forEach((gl) -> {
                    GameGameDetailsDto gameLanguage = new GameGameDetailsDto(game.getId(), gl.getId());
                    if (!gameLanguageService.existsByGameIdAndLanguageId(game.getId(), gl.getId())) {
                        gameLanguageService.save(gameLanguage);
                    }
                });
            }
        }
        return gameDto;
    }

    @Override
    public double rateGame(long gameId, double newUserRating) {
        Game game = gameRepository.findById(gameId).orElseThrow(()
                -> new ApiException("Game not found.", HttpStatus.NOT_FOUND));
        double totalRating = game.getRating();
        long votes = game.getVotes();
        double newTotalRating = ((totalRating * votes) + newUserRating) / (votes + 1);
        gameRepository.saveGameRating(newTotalRating, gameId);

        return newTotalRating;
    }

    @Override
    public void delete(long gameId) {
        if(!existsById(gameId)){
            throw new ApiException("Game with id " + gameId + " doesnt exist.", HttpStatus.NOT_FOUND);
        }
        deleteGameDetailsByGameId(gameId);
        gameRepository.deleteById(gameId);
    }

    @Override
    public boolean existsById(long gameId) {
        return gameRepository.existsById(gameId);
    }

    private void deleteGameDetailsByGameId(long gameId) {
        if(existsById(gameId)){
            gameGenreService.findByGameId(gameId).stream()
                    .forEach((gg) -> gameGenreService.delete(gg.getId()));
            gameCompanyService.findByGameId(gameId).stream()
                    .forEach((gc) -> gameCompanyService.delete(gc.getId()));
            gameLanguageService.findByGameId(gameId).stream()
                    .forEach((gl) -> gameLanguageService.delete(gl.getId()));
        }
    }
}
