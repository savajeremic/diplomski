/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.controller;

import com.project.raider.dao.Game;
import com.project.raider.dao.User;
import com.project.raider.dto.ApiResponse;
import com.project.raider.dto.GameDetailsDto;
import com.project.raider.dto.GameDto;
import com.project.raider.dto.GameGameDetailsDto;
import com.project.raider.exception.ApiException;
import com.project.raider.service.CompanyService;
import com.project.raider.service.GameCompanyService;
import com.project.raider.service.GameGenreService;
import com.project.raider.service.GameLanguageService;
import com.project.raider.service.GameService;
import com.project.raider.service.GenreService;
import com.project.raider.service.LanguageService;
import com.project.raider.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final GameService gameService;
    @Autowired
    private final GenreService genreService;
    @Autowired
    private final CompanyService companyService;
    @Autowired
    private final LanguageService languageService;

    public AdminController(UserService userService, GameService gameService, GenreService genreService,
                           CompanyService companyService, LanguageService languageService) {
        this.userService = userService;
        this.gameService = gameService;
        this.genreService = genreService;
        this.companyService = companyService;
        this.languageService = languageService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('admin')")
    public ApiResponse<List<User>> listUser() {
        return new ApiResponse<>(HttpStatus.OK.value(), "User list retrieved successfully.", userService.findAll());
    }

    @PostMapping("/game/saveGame")
    @PreAuthorize("hasAuthority('admin')")
    public ApiResponse<Game> saveGame(@RequestBody GameDto gameDto) {
        try {
            return new ApiResponse<>(HttpStatus.OK.value(), "Game successfully added.", gameService.save(gameDto));
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @PostMapping("/game/saveGenre")
    @PreAuthorize("hasAuthority('admin')")
    public ApiResponse<GameDetailsDto> saveGenre(@RequestBody GameDetailsDto genre) {
        try {
            return new ApiResponse<>(HttpStatus.OK.value(), "Genre successfully added.", genreService.save(genre));
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @PostMapping("/game/saveCompany")
    @PreAuthorize("hasAuthority('admin')")
    public ApiResponse<GameDetailsDto> saveCompany(@RequestBody GameDetailsDto company) {
        try {
            return new ApiResponse<>(HttpStatus.OK.value(), "Company successfully added.", companyService.save(company));
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @PostMapping("/game/saveLanguage")
    @PreAuthorize("hasAuthority('admin')")
    public ApiResponse<GameDetailsDto> saveLanguage(@RequestBody GameDetailsDto language) {
        try {
            return new ApiResponse<>(HttpStatus.OK.value(), "Language successfully added.", languageService.save(language));
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @DeleteMapping("/game/deleteGame/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ApiResponse<Void> deleteGame(@PathVariable long id) {
        try {
            gameService.delete(id);
            return new ApiResponse<>(HttpStatus.OK.value(), "Game deleted successfully.", null);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @DeleteMapping("/game/deleteGenre/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ApiResponse<Void> deleteGenre(@PathVariable long id) {
        try {
            genreService.delete(id);
            return new ApiResponse<>(HttpStatus.OK.value(), "Genre deleted successfully.", null);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @DeleteMapping("/game/deleteCompany/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ApiResponse<Void> deleteCompany(@PathVariable long id) {
        try {
            companyService.delete(id);
            return new ApiResponse<>(HttpStatus.OK.value(), "Company deleted successfully.", null);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @DeleteMapping("/game/deleteLanguage/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ApiResponse<Void> deleteLanguage(@PathVariable long id) {
        try {
            languageService.delete(id);
            return new ApiResponse<>(HttpStatus.OK.value(), "Language deleted successfully.", null);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }
}
