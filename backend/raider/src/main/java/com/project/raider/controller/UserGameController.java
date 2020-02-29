/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.controller;

import com.project.raider.dao.User;
import com.project.raider.dto.ApiResponse;
import com.project.raider.dto.GameDto;
import com.project.raider.dto.OrdersDto;
import com.project.raider.dto.UserDto;
import com.project.raider.dto.UserGameDto;
import com.project.raider.exception.ApiException;
import com.project.raider.repository.UserRepository;
import com.project.raider.service.UserGameOrdersService;
import com.project.raider.service.UserGameService;
import com.project.raider.service.UserService;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Sava
 */
@RestController
@RequestMapping("/usergame")
public class UserGameController {

    @Autowired
    private final UserGameService userGameService;
    @Autowired
    private final UserGameOrdersService userGameOrdersService;

    public UserGameController(UserGameService userGameService,
            UserGameOrdersService userGameOrdersService) {
        this.userGameService = userGameService;
        this.userGameOrdersService = userGameOrdersService;
    }

    @GetMapping("/filter/byDate/{isAscending}/{userId}")
    public ApiResponse<List<GameDto>> filterStoreByDate(@PathVariable boolean isAscending, @PathVariable long userId) {
        try {
            return new ApiResponse<>(HttpStatus.OK.value(), "Store games filtered.", userGameService.filterStoreByDate(userId, isAscending));
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @GetMapping("/filter/alphabetically/{userId}")
    public ApiResponse<List<GameDto>> filterStoreAlphabetically(@PathVariable long userId) {
        try {
            List<GameDto> list = userGameService.getUpdatedStore(userId);
            list.sort(Comparator.comparing(GameDto::getName));
            return new ApiResponse<>(HttpStatus.OK.value(), "Store games filtered.", list);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @GetMapping("/filter/byRating/{userId}")
    public ApiResponse<List<GameDto>> filterStoreByRating(@PathVariable long userId) {
        try {
            List<GameDto> list = userGameService.getUpdatedStore(userId);
            list.sort(Comparator.comparing(GameDto::getRating).reversed());
            return new ApiResponse<>(HttpStatus.OK.value(), "Store games filtered.", list);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @GetMapping("/getUpdatedStore/{userId}")
    public ApiResponse<List<GameDto>> getUpdatedStore(@PathVariable long userId) {
        try {
            List<GameDto> list = userGameService.getBestSelling(userId);
            return new ApiResponse<>(HttpStatus.OK.value(), "Store games updated.", list);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @PostMapping("/addToCart")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ApiResponse<UserGameDto> addGameToCart(@RequestBody UserGameDto userGame) {
        try {
            userGame.setGameFlagId((short) 1);
            UserGameDto userGameDto = userGameService.addUserGame(userGame);
            return new ApiResponse<>(HttpStatus.OK.value(), "Game successfully added to cart.", userGameDto);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @PostMapping("/addToWishlist")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ApiResponse<UserGameDto> addGameToWishlist(@RequestBody UserGameDto userGame) {
        try {
            userGame.setGameFlagId((short) 2);
            UserGameDto userGameDto = userGameService.addUserGame(userGame);
            return new ApiResponse<>(HttpStatus.OK.value(), "Game successfully added to wishlist.", userGameDto);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @PostMapping("/cartToWishlist")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ApiResponse<UserGameDto> cartToWishlist(@RequestBody UserGameDto userGame) {
        try {
            UserGameDto userGameDto = userGameService.cartToWishlist(userGame);
            return new ApiResponse<>(HttpStatus.OK.value(), "Game successfully added to wishlist.", userGameDto);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }
    
    @PostMapping("/wishlistToCart")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ApiResponse<UserGameDto> wishlistToCart(@RequestBody UserGameDto userGame) {
        try {
            UserGameDto userGameDto = userGameService.wishlistToCart(userGame);
            return new ApiResponse<>(HttpStatus.OK.value(), "Game successfully added to wishlist.", userGameDto);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @PostMapping("/buyGames")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ApiResponse<UserGameDto> buyGames(@RequestBody List<UserGameDto> userGames) {
        try {
            List<UserGameDto> userGameDtoList = userGameService.buyGames(userGames);
            return new ApiResponse<>(HttpStatus.OK.value(), "Game successfully bought.", userGameDtoList);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @GetMapping("/getUserCart/{userId}")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ApiResponse<List<GameDto>> getUserCart(@PathVariable long userId) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Retrieved all user's cart games successfully.", userGameService.getUserGames(userId, 1));
    }

    @GetMapping("/getUserWishlist/{userId}")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ApiResponse<List<GameDto>> getUserWishlist(@PathVariable long userId) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Retrieved all user's wishlisted games successfully.", userGameService.getUserGames(userId, 2));
    }

    @GetMapping("/getUserGames/{userId}")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ApiResponse<List<UserGameDto>> getUserGames(@PathVariable long userId) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Retrieved all user's owned games successfully.", userGameService.getUserGames(userId, 3));
    }

    @GetMapping("/getUserOrders/{userId}")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ApiResponse<List<OrdersDto>> getUserOrders(@PathVariable long userId) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Retrieved all user's orders successfully.", userGameOrdersService.getOrders(userId));
    }

    @PostMapping("/removeFromCart")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ApiResponse<Void> removeUserGameFromCart(@RequestBody UserGameDto userGame) {
        try {
            userGame.setGameFlagId((short) 1);
            userGameService.removeUserGame(userGame);
            return new ApiResponse<>(HttpStatus.OK.value(), "Game removed from cart.", null);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }

    @PostMapping("/removeFromWishlist")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ApiResponse<Void> removeUserGameFromWishlist(@RequestBody UserGameDto userGame) {
        try {
            userGame.setGameFlagId((short) 2);
            userGameService.removeUserGame(userGame);
            return new ApiResponse<>(HttpStatus.OK.value(), "Game unwished.", null);
        } catch (ApiException e) {
            return new ApiResponse<>(e.getHttpStatus().value(), e.getMessage());
        }
    }
}
