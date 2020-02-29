/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.repository;

import com.project.raider.dao.Company;
import com.project.raider.dao.Game;
import com.project.raider.dao.Genre;
import com.project.raider.dao.Language;
import com.project.raider.dto.GameDto;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Sava
 */
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query(value = "SELECT g.*\n"
            + " FROM game as g\n"
            + " LEFT OUTER JOIN user_game as ug ON g.ID = ug.GAME_ID\n"
            + " GROUP BY g.ID  \n"
            + " ORDER BY COUNT(case when ug.GAME_FLAG_ID = 3 then 1 else null end) DESC ", nativeQuery = true)
    List<Game> findByBestSelling();

    List<Game> findByOrderByReleaseDateAsc();

    List<Game> findByOrderByReleaseDateDesc();

    Game findByName(String name);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Game g SET g.rating =:newRating, g.votes = g.votes + 1 WHERE g.id =:gameId")
    int saveGameRating(@Param("newRating") double rating, @Param("gameId") long gameId);

    boolean existsByName(String name);
}
