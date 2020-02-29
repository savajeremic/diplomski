/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.controller;

import com.project.raider.dto.ApiResponse;
import com.project.raider.dto.GameDetailsDto;
import com.project.raider.dto.GameDto;
import com.project.raider.dto.GameReviewDto;
import com.project.raider.exception.ApiException;
import com.project.raider.service.CompanyService;
import com.project.raider.service.GameReviewService;
import com.project.raider.service.GameService;
import com.project.raider.service.GenreService;
import com.project.raider.service.LanguageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Sava
 */
@RestController
@RequestMapping("/store")
public class GameController {

    @Autowired
    private final GameService gameService;
    @Autowired
    private final GenreService genreService;
    @Autowired
    private final CompanyService companyService;
    @Autowired
    private final LanguageService languageService;
    @Autowired
    private final GameReviewService gameReviewService;

    public GameController(GameService gameService, GenreService genreService, CompanyService companyService,
                          LanguageService languageService, GameReviewService gameReviewService) {
        this.gameService = gameService;
        this.genreService = genreService;
        this.companyService = companyService;
        this.languageService = languageService;
        this.gameReviewService = gameReviewService;
    }

    @GetMapping
    public ApiResponse<List<GameDto>> getAllGames() {
        try {
            List<GameDto> gameDtos = gameService.getAllGames();
            return new ApiResponse<>(HttpStatus.OK.value(), "Retrieved all games successfully", gameDtos);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<GameDto> getGameById(@PathVariable long id) {
        try {
            return new ApiResponse<>(HttpStatus.OK.value(), "Game retrieved successfully.", gameService.getById(id));
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @GetMapping("/{id}/reviews")
    public ApiResponse<List<GameReviewDto>> getGameReviewsByGameId(@PathVariable long id) {
        try {
            return new ApiResponse<>(HttpStatus.OK.value(), "Game reviews retrieved successfully.", gameReviewService.findByGameId(id));
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @PostMapping("/submitReview")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ApiResponse<GameReviewDto> addGameReview(@RequestBody GameReviewDto gameReviewDto) {
        try {
            GameReviewDto updatedGameReviewDto = gameReviewService.save(gameReviewDto);
            gameService.rateGame(gameReviewDto.getGameId(), gameReviewDto.getRating());
            return new ApiResponse<>(HttpStatus.OK.value(), "Game Review submited.", updatedGameReviewDto);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @GetMapping("/genres")
    public ApiResponse<List<GameDetailsDto>> getGenres() {
        try {
            return new ApiResponse<>(HttpStatus.OK.value(), "Retrieved all genres successfully", genreService.getAllGenres());
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @GetMapping("/companies")
    public ApiResponse<List<GameDetailsDto>> getCompanies() {
        try {
            return new ApiResponse<>(HttpStatus.OK.value(), "Retrieved all companies successfully.", companyService.getAllCompanies());
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @GetMapping("/languages")
    public ApiResponse<List<GameDetailsDto>> getLanguages() {
        try {
            return new ApiResponse<>(HttpStatus.OK.value(), "Retrieved all languages successfully.", languageService.getAllLanguages());
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @GetMapping("/genres/{id}")
    public ApiResponse<GameDetailsDto> getGenreById(@PathVariable long id) {
        try {
            return new ApiResponse<>(HttpStatus.OK.value(), "Retrieved genre successfully", genreService.getById(id));
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @GetMapping("/companies/{id}")
    public ApiResponse<GameDetailsDto> getCompanyById(@PathVariable long id) {
        try {
            return new ApiResponse<>(HttpStatus.OK.value(), "Retrieved company successfully.", companyService.getById(id));
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @GetMapping("/languages/{id}")
    public ApiResponse<GameDetailsDto> getLanguageById(@PathVariable long id) {
        try {
            return new ApiResponse<>(HttpStatus.OK.value(), "Retrieved language successfully.", languageService.getById(id));
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }
}
