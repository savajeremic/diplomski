/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.service.implementation;

import com.project.raider.dao.Language;
import com.project.raider.dto.GameDetailsDto;
import com.project.raider.exception.ApiException;
import com.project.raider.repository.LanguageRepository;
import com.project.raider.service.GameLanguageService;
import com.project.raider.service.LanguageService;
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
public class LanguageServiceImp implements LanguageService {

    @Autowired
    private final LanguageRepository languageRepository;
    
    @Autowired
    private final GameLanguageService gameLanguageService;

    public LanguageServiceImp(LanguageRepository languageRepository, 
            GameLanguageService gameLanguageService) {
        this.languageRepository = languageRepository;
        this.gameLanguageService = gameLanguageService;
    }

    @Override
    public List<GameDetailsDto> getAllLanguages() {
        List<Language> languageList = languageRepository.findAll();
        List<GameDetailsDto> gameDetailsDtoList = new ArrayList<>();
        languageList.stream().forEach(l -> gameDetailsDtoList.add(new GameDetailsDto(l.getId(), l.getName())));
        return gameDetailsDtoList;
    }

    @Override
    public GameDetailsDto getById(long id) {
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new ApiException("Language not found.", HttpStatus.NOT_FOUND));
        return new GameDetailsDto(language.getId(), language.getName());
    }

    @Override
    public GameDetailsDto save(GameDetailsDto languageDto) {
        long id = languageDto.getId();
        String name = languageDto.getName();
        Language language = new Language(name);
        if(languageRepository.existsById(id)) {
            language.setId(id);
        } 
        else if(languageRepository.existsByName(name)) {
            throw new ApiException("Language with name already exists.", HttpStatus.BAD_REQUEST);
        }
        Language newLanguage = languageRepository.save(language);
        languageDto.setId(newLanguage.getId());
        return languageDto;
    }

    @Override
    public boolean existsByName(String name) {
        return languageRepository.existsByName(name);
    }

    @Override
    public void delete(long languageId) {
        Language language = languageRepository.findById(languageId)
                .orElseThrow(() -> new ApiException("Language not found.", HttpStatus.NOT_FOUND));
        gameLanguageService.findByLanguageId(languageId).stream()
                .forEach((gl) -> gameLanguageService.delete(gl.getId()));
        languageRepository.delete(language);
    }
}
