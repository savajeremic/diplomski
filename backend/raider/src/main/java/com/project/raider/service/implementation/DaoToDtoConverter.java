/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.service.implementation;

import com.project.raider.dao.Game;
import com.project.raider.dao.User;
import com.project.raider.dto.GameDetailsDto;
import com.project.raider.dto.GameDto;
import com.project.raider.dto.UserDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Sava
 */
@Component
public class DaoToDtoConverter {

    public GameDto gameToDto(Game game) {
        List<GameDetailsDto> genreList = new ArrayList<>();
        List<GameDetailsDto> companyList = new ArrayList<>();
        List<GameDetailsDto> languageList = new ArrayList<>();
        game.getGameGenreList().stream().forEach((gg) -> {
            genreList.add(new GameDetailsDto(
                    gg.getGenre().getId(), gg.getGenre().getName()));
        });
        game.getGameCompanyList().stream().forEach((gc) -> {
            companyList.add(new GameDetailsDto(
                    gc.getCompany().getId(), gc.getCompany().getName()));
        });
        game.getGameLanguageList().stream().forEach((gl) -> {
            languageList.add(new GameDetailsDto(
                    gl.getLanguage().getId(), gl.getLanguage().getName()));
        });
        GameDto gameDto = new GameDto(game.getId(), game.getName(),
                game.getDescription(), game.getCoverImage(),
                game.getReleaseDate(), game.getVersion(),
                game.getRating(), game.getSize(), game.getPrice(),
                game.getVotes(), genreList, companyList, languageList);
        return gameDto;
    }
}
