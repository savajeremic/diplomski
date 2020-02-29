/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.service;

import com.project.raider.dto.GameReviewDto;
import java.util.List;

/**
 *
 * @author Sava
 */
public interface GameReviewService {
    List<GameReviewDto> getAll();
    List<GameReviewDto> findByGameId(long gameId);
    GameReviewDto save(GameReviewDto gameReviewDto);
}
