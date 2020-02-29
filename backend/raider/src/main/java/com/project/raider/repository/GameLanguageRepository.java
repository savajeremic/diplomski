/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.repository;

import com.project.raider.dao.GameLanguage;
import com.project.raider.dao.Language;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Sava
 */
public interface GameLanguageRepository extends JpaRepository<GameLanguage, Long> {

    List<GameLanguage> findByGameId(long gameId);
    List<GameLanguage> findByLanguageId(long languageId);
    boolean existsByGameIdAndLanguageId(long gameId, long languageId);
}
