/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.service.implementation;

import com.project.raider.configuration.Constants;
import com.project.raider.dao.Game;
import com.project.raider.dao.GameFlag;
import com.project.raider.dao.Orders;
import com.project.raider.dao.User;
import com.project.raider.dao.UserGame;
import com.project.raider.dto.GameDto;
import com.project.raider.dto.UserGameDto;
import com.project.raider.exception.ApiException;
import com.project.raider.repository.GameFlagRepository;
import com.project.raider.repository.GameRepository;
import com.project.raider.repository.UserGameRepository;
import com.project.raider.repository.UserRepository;
import com.project.raider.service.OrdersService;
import com.project.raider.service.UserGameOrdersService;
import com.project.raider.service.UserGameService;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sava
 */
@Service
public class UserGameServiceImp implements UserGameService {

    @Autowired
    private final UserGameRepository userGameRepository;
    @Autowired
    private final GameFlagRepository gameFlagRepository;
    @Autowired
    private final GameRepository gameRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final OrdersService ordersService;
    @Autowired
    private final UserGameOrdersService userGameOrdersService;
    @Autowired
    private final DaoToDtoConverter converter;

    public UserGameServiceImp(UserGameRepository userGameRepository, 
            GameFlagRepository gameFlagRepository, 
            GameRepository gameRepository, UserRepository userRepository, 
            OrdersService ordersService, 
            UserGameOrdersService userGameOrdersService, 
            DaoToDtoConverter converter) {
        this.userGameRepository = userGameRepository;
        this.gameFlagRepository = gameFlagRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.ordersService = ordersService;
        this.userGameOrdersService = userGameOrdersService;
        this.converter = converter;
    }

    @Override
    public UserGameDto addUserGame(UserGameDto userGameDto) {
        UserGame userGame = getUserGameFromDto(userGameDto, false);
        GameFlag cartFlag = gameFlagRepository.findByName(Constants.FLAG_CART);
        GameFlag ownedFlag = gameFlagRepository.findByName(Constants.FLAG_OWNED);
        GameFlag wishlistFlag = gameFlagRepository.findByName(Constants.FLAG_WISHLIST);

        boolean isUserGameInCart = userGameRepository.existsByUserAndGameAndGameFlag(userGame.getUser(), userGame.getGame(), cartFlag);
        boolean isUserGameOwned = userGameRepository.existsByUserAndGameAndGameFlag(userGame.getUser(), userGame.getGame(), ownedFlag);
        boolean isUserGameInWishlist = userGameRepository.existsByUserAndGameAndGameFlag(userGame.getUser(), userGame.getGame(), wishlistFlag);

        if (isUserGameInCart) {
            throw new ApiException("Game is already in cart.", HttpStatus.BAD_REQUEST);
        }
        if (isUserGameInWishlist) {
            throw new ApiException("Game is already in wishlist.", HttpStatus.BAD_REQUEST);
        }
        if (isUserGameOwned) {
            throw new ApiException("You already own this game.", HttpStatus.BAD_REQUEST);
        }

        userGameRepository.save(userGame);
        return userGameDto;
    }

    @Override
    public UserGameDto cartToWishlist(UserGameDto userGameDto) {
        UserGame userGame = getUserGameFromDto(userGameDto, true);
        GameFlag cartFlag = gameFlagRepository.findByName(Constants.FLAG_CART);
        GameFlag ownedFlag = gameFlagRepository.findByName(Constants.FLAG_OWNED);
        GameFlag wishlistFlag = gameFlagRepository.findByName(Constants.FLAG_WISHLIST);

        boolean isUserGameInCart = userGameRepository.existsByUserAndGameAndGameFlag(userGame.getUser(), userGame.getGame(), cartFlag);
        boolean isUserGameOwned = userGameRepository.existsByUserAndGameAndGameFlag(userGame.getUser(), userGame.getGame(), ownedFlag);
        boolean isUserGameInWishlist = userGameRepository.existsByUserAndGameAndGameFlag(userGame.getUser(), userGame.getGame(), wishlistFlag);

        if (!isUserGameInCart) {
            throw new ApiException("Game isn't in your cart, if you want to move game from cart to wishlist, first put it in cart.", HttpStatus.BAD_REQUEST);
        }
        if (isUserGameInWishlist) {
            throw new ApiException("Game is already in wishlist.", HttpStatus.BAD_REQUEST);
        }
        if (isUserGameOwned) {
            throw new ApiException("You already own this game.", HttpStatus.BAD_REQUEST);
        }
        userGame.setGameFlag(wishlistFlag);
        userGameRepository.save(userGame);
        return userGameDto;
    }
    
    @Override
    public UserGameDto wishlistToCart(UserGameDto userGameDto) {
        UserGame userGame = getUserGameFromDto(userGameDto, true);
        GameFlag wishlistFlag = gameFlagRepository.findByName(Constants.FLAG_WISHLIST);
        GameFlag ownedFlag = gameFlagRepository.findByName(Constants.FLAG_OWNED);
        GameFlag cartFlag = gameFlagRepository.findByName(Constants.FLAG_CART);

        boolean isUserGameInWishlist = userGameRepository.existsByUserAndGameAndGameFlag(userGame.getUser(), userGame.getGame(), wishlistFlag);
        boolean isUserGameOwned = userGameRepository.existsByUserAndGameAndGameFlag(userGame.getUser(), userGame.getGame(), ownedFlag);
        boolean isUserGameInCart = userGameRepository.existsByUserAndGameAndGameFlag(userGame.getUser(), userGame.getGame(), cartFlag);

        if (!isUserGameInWishlist) {
            throw new ApiException("Game isn't in your wishlist, if you want to move game from wishlist to cart, first wishlist it.", HttpStatus.BAD_REQUEST);
        }
        if (isUserGameInCart) {
            throw new ApiException("Game is already in cart.", HttpStatus.BAD_REQUEST);
        }
        if (isUserGameOwned) {
            throw new ApiException("You already own this game.", HttpStatus.BAD_REQUEST);
        }
        userGame.setGameFlag(cartFlag);
        userGameRepository.save(userGame);
        return userGameDto;
    }

    @Override
    public List<UserGameDto> buyGames(List<UserGameDto> userGameDtoList) {
        if (userGameDtoList.size() < 1) {
            throw new ApiException("Can't buy an empty cart.", HttpStatus.BAD_REQUEST);
        }
        double totalPrice = 0;
        List<UserGame> userGameList = new ArrayList<>();
        UserGame userGame;
        for (UserGameDto ugd : userGameDtoList) {
            userGame = getUserGameFromDto(ugd, true);
            userGameList.add(userGame);
            if (!userGame.getGameFlag().getName().equals(Constants.FLAG_OWNED)) {
                totalPrice += userGame.getGame().getPrice();
            }
            userGame.setGameFlag(gameFlagRepository.findByName(Constants.FLAG_OWNED));
        }
        userGameRepository.saveAll(userGameList);

        Orders order = ordersService.save(totalPrice);
        userGameList.stream().forEach((ug) -> {
            userGameOrdersService.save(order, ug);
        });
        return userGameDtoList;
    }

    @Override
    public List<GameDto> getUserGames(long userId, int gameFlagId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException("User not found.", HttpStatus.NOT_FOUND));
        GameFlag gameFlag = gameFlagRepository.findById((short) gameFlagId).orElseThrow(() -> new ApiException("Game flag not found.", HttpStatus.NOT_FOUND));

        List<GameDto> gameDtoList = new ArrayList<>();
        List<UserGame> userGameList = userGameRepository.findByUserAndGameFlag(user, gameFlag);
        userGameList.stream().forEach((userGame) -> {
            Game game = gameRepository.findById(userGame.getGame().getId())
                    .orElseThrow(() -> new ApiException("Game not found.", HttpStatus.NOT_FOUND));
            GameDto gameDto = converter.gameToDto(game);
            gameDtoList.add(gameDto);
        });
        return gameDtoList;
    }

    @Override
    public List<GameDto> filterStoreByDate(long userId, boolean isAscending) {
        User user;
        if (userId != 0) {
            user = userRepository.findById(userId).orElseThrow(() -> new ApiException("User not found.", HttpStatus.NOT_FOUND));
        } else {
            user = new User(0L);
        }
        List<Game> games;
        if (isAscending) {
            games = gameRepository.findByOrderByReleaseDateAsc();
        } else {
            games = gameRepository.findByOrderByReleaseDateDesc();
        }
        return updateStoreGames(games, user);
    }

    @Override
    public List<GameDto> getUpdatedStore(long userId) {
        User user;
        if (userId != 0) {
            user = userRepository.findById(userId).orElseThrow(() -> new ApiException("User not found.", HttpStatus.NOT_FOUND));
        } else {
            user = new User(0L);
        }
        List<Game> games = gameRepository.findByBestSelling();
        return updateStoreGames(games, user);
    }

    @Override
    public List<GameDto> getBestSelling(long userId) {
        User user;
        if (userId != 0) {
            user = userRepository.findById(userId).orElseThrow(() -> new ApiException("User not found.", HttpStatus.NOT_FOUND));
        } else {
            user = new User(0L);
        }
        List<Game> games = gameRepository.findByBestSelling();
        return updateStoreGames(games, user);
    }

    private List<GameDto> updateStoreGames(List<Game> games, User user) {
        List<GameDto> gameDtos = new ArrayList<>();
        games.stream().forEach((game) -> {
            GameDto gameDto = converter.gameToDto(game);
            UserGame userGame = userGameRepository.findByUserAndGame(user, game);
            if (userGame != null) {
                gameDto.setIsInCart(isUserGameInGameFlag(userGame, 1));
                gameDto.setIsInWishlist(isUserGameInGameFlag(userGame, 2));
                gameDto.setIsInOwned(isUserGameInGameFlag(userGame, 3));
            }
            gameDtos.add(gameDto);
        });
        return gameDtos;
    }

    @Transactional
    @Override
    public void removeUserGame(UserGameDto userGameDto) {
        UserGame userGame = getUserGameFromDto(userGameDto, true);
        if (userGameDto.getGameFlagId() == 3) {
            throw new ApiException("You cant delete a game you own.", HttpStatus.BAD_REQUEST);
        }
        if (!userGameRepository.existsByUser(userGame.getUser())) {
            throw new ApiException("Nothing to remove.", HttpStatus.BAD_REQUEST);
        }
        userGameRepository.delete(userGame);
    }

    private boolean isUserGameInGameFlag(UserGame userGame, int gameFlagId) {
        return userGameRepository.findByUserAndGameAndGameFlag(
                userGame.getUser(), userGame.getGame(), userGame.getGameFlag())
                .getGameFlag().getId() == (short) gameFlagId;
    }

    private UserGame getUserGameFromDto(UserGameDto userGameDto, boolean isUpdate) {
        User user = userRepository.findById(userGameDto.getUserId()).orElseThrow(() -> new ApiException("User not found.", HttpStatus.NOT_FOUND));
        Game game = gameRepository.findById(userGameDto.getGameId()).orElseThrow(() -> new ApiException("Game not found.", HttpStatus.NOT_FOUND));
        GameFlag gameFlag = gameFlagRepository.findById(userGameDto.getGameFlagId()).orElseThrow(() -> new ApiException("Game flag not found.", HttpStatus.NOT_FOUND));
        UserGame userGame;
        if (isUpdate) {
            UserGame userGame1 = userGameRepository.findByUserAndGameAndGameFlag(user, game, gameFlag);
            long userGameId = 0;
            if(userGame1 != null) {
                userGameId = userGame1.getId();
            } else {
                throw new ApiException("User game not found.", HttpStatus.NOT_FOUND);
            }
            userGame = new UserGame(userGameId, user, game, gameFlag);
        } else {
            userGame = new UserGame(user, game, gameFlag);
        }
        return userGame;
    }
}
