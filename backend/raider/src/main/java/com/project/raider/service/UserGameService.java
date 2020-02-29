/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.service;

import com.project.raider.dao.Game;
import com.project.raider.dao.User;
import com.project.raider.dao.UserGame;
import com.project.raider.dto.GameDto;
import com.project.raider.dto.UserGameDto;
import java.util.List;

/**
 *
 * @author Sava
 */
public interface UserGameService {

    UserGameDto addUserGame(UserGameDto userGameDto);
    UserGameDto cartToWishlist(UserGameDto userGameDto);
    UserGameDto wishlistToCart(UserGameDto userGameDto);
    List<UserGameDto> buyGames(List<UserGameDto> userGames);
    List<GameDto> getUserGames(long userId, int gameFlagId);
    List<GameDto> getUpdatedStore(long userId);
    List<GameDto> filterStoreByDate(long userId, boolean isAscending);
    List<GameDto> getBestSelling(long userId);
    void removeUserGame(UserGameDto userGameDto);
}
