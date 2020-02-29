/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.service.implementation;

import com.project.raider.dao.Genre;
import com.project.raider.dto.GameDetailsDto;
import com.project.raider.exception.ApiException;
import com.project.raider.repository.GenreRepository;
import com.project.raider.service.GameGenreService;
import com.project.raider.service.GenreService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sava
 */
@Service
public class GenreServiceImp implements GenreService {

    @Autowired
    private final GenreRepository genreRepository;

    @Autowired
    private final GameGenreService gameGenreService;

    public GenreServiceImp(GenreRepository genreRepository, GameGenreService gameGenreService) {
        this.genreRepository = genreRepository;
        this.gameGenreService = gameGenreService;
    }

    @Override
    public List<GameDetailsDto> getAllGenres() {
        List<Genre> genreList = genreRepository.findAll();
        List<GameDetailsDto> gameDetailsDtoList = new ArrayList<>();
        genreList.stream().forEach(g -> gameDetailsDtoList.add(new GameDetailsDto(g.getId(), g.getName())));

        return gameDetailsDtoList;
    }

    @Override
    public GameDetailsDto getById(long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ApiException("Genre not found.", HttpStatus.NOT_FOUND));
        return new GameDetailsDto(genre.getId(), genre.getName());
    }

    @Override
    public GameDetailsDto save(GameDetailsDto genreDto) {
        long id = genreDto.getId();
        String name = genreDto.getName();
        Genre genre = new Genre(name);
        if (genreRepository.existsById(id)) {
            genre.setId(id);
        } else if (genreRepository.existsByName(name)) {
            throw new ApiException("Genre with name already exists.", HttpStatus.BAD_REQUEST);
        }
        Genre newGenre = genreRepository.save(genre);
        genreDto.setId(newGenre.getId());
        return genreDto;
    }

    @Override
    public boolean existsByName(String name) {
        return genreRepository.existsByName(name);
    }

    @Override
    public void delete(long genreId) {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new ApiException("Genre not found.", HttpStatus.NOT_FOUND));
        gameGenreService.findByGenreId(genreId).stream()
                .forEach((gg) -> gameGenreService.delete(gg.getId()));
        genreRepository.delete(genre);
    }
}
