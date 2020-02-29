/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.service;

import com.project.raider.dao.GameLanguage;
import com.project.raider.dto.GameGameDetailsDto;
import java.util.List;

/**
 *
 * @author Sava
 */
public interface GameLanguageService {
    GameGameDetailsDto save(GameGameDetailsDto gameLanguage);
    GameLanguage findById(long id);
    List<GameLanguage> findByGameId(long gameId);
    List<GameLanguage> findByLanguageId(long languageId);
    boolean existsByGameIdAndLanguageId(long gameId, long languageId);
    void delete(long id);

}
