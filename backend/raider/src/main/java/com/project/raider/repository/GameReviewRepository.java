/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.repository;

import com.project.raider.dao.GameReview;
import com.project.raider.dao.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Sava
 */
public interface GameReviewRepository extends JpaRepository<GameReview, Long>{
    List<GameReview> findByGameId(long gameId);
    GameReview findByGameIdAndUser(long gameId, User user);

}
