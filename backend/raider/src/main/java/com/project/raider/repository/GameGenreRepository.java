/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.repository;

import com.project.raider.dao.GameGenre;
import com.project.raider.dao.Genre;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Sava
 */
public interface GameGenreRepository extends JpaRepository<GameGenre, Long> {

    List<GameGenre> findByGameId(long gameId);
    boolean existsByGameIdAndGenreId(long gameId, long genreId);
}
