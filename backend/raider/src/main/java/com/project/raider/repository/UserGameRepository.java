/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.repository;

import com.project.raider.dao.Game;
import com.project.raider.dao.GameFlag;
import com.project.raider.dao.User;
import com.project.raider.dao.UserGame;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Sava
 */
public interface UserGameRepository extends JpaRepository<UserGame, Long> {
    UserGame findByUserAndGameAndGameFlag(User user, Game game, GameFlag gameFlag);
    List<UserGame> findByUserAndGameFlag(User user, GameFlag gameFlag);
    UserGame findByUserAndGame(User user, Game game);
    boolean existsByUser(User user);
    boolean existsByUserAndGameAndGameFlag(User user, Game game, GameFlag gameFlag);
}
