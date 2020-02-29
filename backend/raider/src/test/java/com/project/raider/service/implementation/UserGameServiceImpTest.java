package com.project.raider.service.implementation;

import com.project.raider.configuration.Constants;
import com.project.raider.dao.*;
import com.project.raider.dto.GameDto;
import com.project.raider.dto.UserGameDto;
import com.project.raider.exception.ApiException;
import com.project.raider.repository.GameFlagRepository;
import com.project.raider.repository.GameRepository;
import com.project.raider.repository.UserGameRepository;
import com.project.raider.repository.UserRepository;
import com.project.raider.service.OrdersService;
import com.project.raider.service.UserGameOrdersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class UserGameServiceImpTest {
    @InjectMocks
    private UserGameServiceImp userGameServiceImp;
    @Mock
    private UserGameRepository userGameRepository;
    @Mock
    private GameFlagRepository gameFlagRepository;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrdersService ordersService;
    @Mock
    private UserGameOrdersService userGameOrdersService;
    @Mock
    private DaoToDtoConverter converter;

    private List<UserGameDto> userGameDtoList;
    private List<UserGame> userGameList;
    private UserGameDto userGameDto, userGameDto2;
    private UserGame userGame, userGame2;
    private User user;
    private Game game, game2;
    private GameDto gameDto;
    private GameFlag gameFlagCart, gameFlagWishlist, gameFlagOwned;
    private Orders order;
    private UserGameOrders userGameOrders;
    private final String FLAG_CART = "cart",
    FLAG_WISHLIST = "wishlist",
    FLAG_OWNED = "owned";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userGameDto = new UserGameDto(1L, 1L, (short) 1);
        userGameDto2 = new UserGameDto(1L, 2L, (short) 1);
        gameFlagCart = new GameFlag((short) 1, FLAG_CART);
        gameFlagWishlist = new GameFlag((short) 2, FLAG_WISHLIST);
        gameFlagOwned = new GameFlag((short) 3, FLAG_OWNED);
        game = new Game(1L, "TestGame 3", "Some description of the game",
                "/assets/images/1.png", "1.2.0", 8.75, 530L,
                new Date(), 52.5, 49.99);
        game2 = new Game(2L, "Naziv", "Some description of the game",
                "/assets/images/1.png", "1.2.0", 8.75, 530L,
                new Date(), 52.5, 49.99);
        gameDto = new GameDto();
        BeanUtils.copyProperties(game, gameDto); gameDto.setId(1L);
        user = new User("admin96", "8213sdhhj12udzizx", "admin@adminson.com",
                "Sava Jeremic", new Date(),"Adminton", null, true,
                new UserRole((short) 2, "admin"));
        user.setId(1L);
        userGame = new UserGame(user, game, gameFlagCart);
        userGame2 = new UserGame(user, game2, gameFlagCart);
        userGameDtoList = new ArrayList<>();
        userGameDtoList.add(userGameDto);
        userGameDtoList.add(userGameDto2);
        userGameList = new ArrayList<>();
        userGameList.add(userGame);
        userGameList.add(userGame2);
        order = new Orders(1L);
        userGameOrders = new UserGameOrders(order, userGame);
    }

    void getUserGameFromDtoWhens() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));
        when(gameFlagRepository.findById(anyShort())).thenReturn(Optional.of(gameFlagCart));
        when(userGameRepository.findByUserAndGameAndGameFlag(any(User.class), any(Game.class), any(GameFlag.class)))
                .thenReturn(userGame);
    }

    void buyGamesWhens() {
        getUserGameFromDtoWhens();
        when(userGameRepository.saveAll(anyList())).thenReturn(userGameList);
        when(gameFlagRepository.findByName(anyString())).thenReturn(gameFlagOwned);
        when(ordersService.save(anyDouble())).thenReturn(new Orders(1L));
        when(userGameOrdersService.save(any(Orders.class), any(UserGame.class))).thenReturn(userGameOrders);
    }

    void addNewUserGameWhens() {
        getUserGameFromDtoWhens();
        when(gameFlagRepository.findByName(FLAG_CART)).thenReturn(gameFlagCart);
        when(gameFlagRepository.findByName(FLAG_WISHLIST)).thenReturn(gameFlagWishlist);
        when(gameFlagRepository.findByName(FLAG_OWNED)).thenReturn(gameFlagOwned);
    }

    @Test
    @DisplayName("Adding new game to cart/wishlist/owned")
    void testAddNewUserGame_Success() {
        addNewUserGameWhens();
        when(userGameRepository.existsByUserAndGameAndGameFlag(any(User.class), any(Game.class), any(GameFlag.class)))
                .thenReturn(false);
        //adding to cart
        UserGameDto testUserGameDto = userGameServiceImp.addUserGame(userGameDto);
        assertNotNull(testUserGameDto);
        assertEquals((short) 1, testUserGameDto.getGameFlagId());
        //adding to wishlist
        testUserGameDto.setGameFlagId((short) 2);
        testUserGameDto = userGameServiceImp.addUserGame(userGameDto);
        assertEquals((short) 2, testUserGameDto.getGameFlagId());
        //adding to owned(buying)
        testUserGameDto.setGameFlagId((short) 3);
        testUserGameDto = userGameServiceImp.addUserGame(userGameDto);
        assertEquals((short) 3, testUserGameDto.getGameFlagId());
    }

    @Test
    @DisplayName("Adding new game: Throwing exception, already in cart/wishlist/owned")
    void testAddNewUserGame_Exception() {
        addNewUserGameWhens();
        when(userGameRepository.existsByUserAndGameAndGameFlag(any(User.class), any(Game.class), any(GameFlag.class)))
                .thenReturn(true);
        assertThrows(ApiException.class, () -> userGameServiceImp.addUserGame(userGameDto));
        //adding to wishlist
        userGameDto.setGameFlagId((short) 2);
        assertThrows(ApiException.class, () -> userGameServiceImp.addUserGame(userGameDto));
        //adding to owned(buying)
        userGameDto.setGameFlagId((short) 3);
        assertThrows(ApiException.class, () -> userGameServiceImp.addUserGame(userGameDto));
    }

    @Test
    @DisplayName("Buying games!")
    void testBuyGames_Success() {
        buyGamesWhens();

        List<UserGameDto> newUserGameDtoList = userGameServiceImp.buyGames(userGameDtoList);
        assertNotNull(newUserGameDtoList);
        assertTrue(newUserGameDtoList.size() > 0);
        assertEquals(newUserGameDtoList.get(0).getGameFlagId(), gameFlagCart.getId());
    }

    @Test
    @DisplayName("Buying games: Throwing exception, empty cart")
    void testBuyGames_Exception() {
        buyGamesWhens();
        assertThrows(ApiException.class, () -> userGameServiceImp.buyGames(Collections.emptyList()));
    }

    @Test
    @DisplayName("Getting user games(cart)")
    void getUserGames_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(gameFlagRepository.findById(anyShort())).thenReturn(Optional.of(gameFlagCart));
        when(userGameRepository.findByUserAndGameFlag(any(User.class), any(GameFlag.class))).thenReturn(userGameList);
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));
        when(converter.gameToDto(any(Game.class))).thenReturn(gameDto);
        List<GameDto> testUserCartGamesList = userGameServiceImp.getUserGames(user.getId(), gameFlagCart.getId());
        assertEquals(testUserCartGamesList.get(0).getName(), gameDto.getName());
    }

    @Test
    @DisplayName("Getting user games(cart): Throwing exception, not found user/gameflag/game")
    void getUserGames_Exception() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(gameFlagRepository.findById(anyShort())).thenReturn(Optional.empty());
        when(userGameRepository.findByUserAndGameFlag(any(User.class), any(GameFlag.class))).thenReturn(userGameList);
        when(gameRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(converter.gameToDto(any(Game.class))).thenReturn(gameDto);
        assertThrows(ApiException.class, () -> userGameServiceImp.getUserGames(user.getId(), gameFlagCart.getId()));
    }
}