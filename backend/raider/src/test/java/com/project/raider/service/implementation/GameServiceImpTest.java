package com.project.raider.service.implementation;

import com.project.raider.dao.*;
import com.project.raider.dto.GameDetailsDto;
import com.project.raider.dto.GameDto;
import com.project.raider.dto.GameGameDetailsDto;
import com.project.raider.dto.UserDto;
import com.project.raider.exception.ApiException;
import com.project.raider.repository.GameRepository;
import com.project.raider.service.GameCompanyService;
import com.project.raider.service.GameGenreService;
import com.project.raider.service.GameLanguageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class GameServiceImpTest {

    @InjectMocks
    private GameServiceImp gameServiceImp;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private GameGenreService gameGenreService;
    @Mock
    private GameCompanyService gameCompanyService;
    @Mock
    private GameLanguageService gameLanguageService;
    @Mock
    private DaoToDtoConverter converter;

    private Game game;
    private GameDto gameDto;
    private Genre genre1, genre2;
    private Company company1, company2;
    private Language language1, language2;
    private GameGameDetailsDto gameGenreDto;
    private GameGameDetailsDto gameCompanyDto;
    private GameGameDetailsDto gameLanguageDto;
    private List<Game> gameList;
    private List<GameGenre> gameGenreList;
    private List<GameCompany> gameCompanyList;
    private List<GameLanguage> gameLanguageList;
    private List<GameDetailsDto> gameGenreListDto, gameCompanyListDto, gameLanguageListDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        game = new Game(1L, "TestGame 3", "Some description of the game",
                "/assets/images/1.png", "1.2.0", 8.75, 530L, new Date(), 52.5, 49.99);
        gameDto = new GameDto();
        genre1 = new Genre(1L, "Action");
        genre2 = new Genre(2L, "RPG");
        company1 = new Company(1L, "CD PROJEKT RED");
        company2 = new Company(2L, "Larian Studios");
        language1 = new Language(1L, "English");
        language2 = new Language(2L, "German");
        gameList = new ArrayList<>();
        gameGenreList = new ArrayList<>();
        gameCompanyList = new ArrayList<>();
        gameLanguageList = new ArrayList<>();
        gameList.add(game);
        gameGenreList.add(new GameGenre(1L, game, genre1));
        gameGenreList.add(new GameGenre(2L, game, genre2));
        gameCompanyList.add(new GameCompany(1L, game, company1));
        gameCompanyList.add(new GameCompany(2L, game, company2));
        gameLanguageList.add(new GameLanguage(1L, game, language1));
        gameLanguageList.add(new GameLanguage(2L, game, language2));
        game.setGameGenreList(gameGenreList);
        game.setGameCompanyList(gameCompanyList);
        game.setGameLanguageList(gameLanguageList);
        BeanUtils.copyProperties(game, gameDto);
        gameDto.setId(game.getId());
        gameGenreListDto = new ArrayList<>();
        gameCompanyListDto = new ArrayList<>();
        gameLanguageListDto = new ArrayList<>();
        gameGenreListDto.add(new GameDetailsDto(genre1.getId(), genre1.getName()));
        gameGenreListDto.add(new GameDetailsDto(genre2.getId(), genre2.getName()));
        gameCompanyListDto.add(new GameDetailsDto(company1.getId(), company1.getName()));
        gameCompanyListDto.add(new GameDetailsDto(company2.getId(), company2.getName()));
        gameLanguageListDto.add(new GameDetailsDto(language1.getId(), language1.getName()));
        gameLanguageListDto.add(new GameDetailsDto(language2.getId(), language2.getName()));
        gameDto.setGenres(gameGenreListDto);
        gameDto.setCompanies(gameCompanyListDto);
        gameDto.setLanguages(gameLanguageListDto);
        gameGenreDto = new GameGameDetailsDto(game.getId(), gameGenreListDto.get(0).getId());
        gameCompanyDto = new GameGameDetailsDto(game.getId(), gameCompanyListDto.get(0).getId());
        gameLanguageDto = new GameGameDetailsDto(game.getId(), gameLanguageListDto.get(0).getId());
    }

    @Test
    @DisplayName("Converting Game.class to GameDto.class!")
    void gameToDtoConverter() {
        when(converter.gameToDto(any(Game.class))).thenReturn(gameDto);
        GameDto gameDtoTest = gameServiceImp.gameToDtoConverter(game);
        assertNotNull(gameDtoTest);
        assertEquals("TestGame 3", gameDtoTest.getName());
    }

    @Test
    @DisplayName("Getting game by id!")
    void getById_Success() {
        when(gameRepository.findById( anyLong() )).thenReturn(Optional.of(game));
        when(gameServiceImp.gameToDtoConverter(any(Game.class))).thenReturn(gameDto);
        GameDto gameDtoTest = gameServiceImp.getById(1L);
        assertNotNull(gameDtoTest);
        assertEquals(1L, gameDtoTest.getId());
    }
    @Test
    @DisplayName("Getting game by id: Throwing exception, not found")
    void getById_Exception() {
        when(gameRepository.findById( anyLong() )).thenReturn(Optional.empty());
        when(gameServiceImp.gameToDtoConverter(any(Game.class))).thenReturn(gameDto);
        assertThrows(ApiException.class, () -> gameServiceImp.getById(1L));
    }

    @Test
    @DisplayName("Getting all games!")
    void getAllGames() {
        when(gameRepository.findByBestSelling()).thenReturn(gameList);
        when(gameServiceImp.gameToDtoConverter(any(Game.class))).thenReturn(gameDto);
        List<GameDto> gameDtoList = gameServiceImp.getAllGames();
        assertNotNull(gameDtoList);
        assertEquals("TestGame 3", gameDtoList.get(0).getName());
    }

    @Test
    @DisplayName("Game exists by name!")
    void testGameExistsByName() {
        when(gameRepository.existsByName("TestGame 3")).thenReturn(true);
        assertTrue(gameServiceImp.existsByName("TestGame 3"));
        assertFalse(gameServiceImp.existsByName("TestGame 4"));
    }

    @Test
    @DisplayName("Saving game!")
    void testSaveGame_Success() {
        when(gameRepository.existsById(anyLong())).thenReturn(true);
        saveGameWhens();

        GameDto gameDtoTest = gameServiceImp.save(gameDto);
        assertAll("GameDto values are not null",
                () -> assertNotNull(gameDtoTest),
                () -> assertNotNull(gameDtoTest.getGenres()),
                () -> assertNotNull(gameDtoTest.getCompanies()),
                () -> assertNotNull(gameDtoTest.getLanguages())
        );
        assertAll("GameDto values are set as expected",
                () -> assertEquals("TestGame 3", gameDtoTest.getName()),
                () -> assertEquals("Action", gameDtoTest.getGenres().get(0).getName()),
                () -> assertEquals("CD PROJEKT RED", gameDtoTest.getCompanies().get(0).getName()),
                () -> assertEquals("English", gameDtoTest.getLanguages().get(0).getName())
        );
    }

    @Test
    @DisplayName("Saving game: Throwing exception, already exists by name")
    void testSaveGame_Exception() {
        when(gameRepository.existsById(anyLong())).thenReturn(false);
        saveGameWhens();
        assertThrows(ApiException.class, () -> gameServiceImp.save(gameDto));
    }

    void saveGameWhens() {
        when(gameRepository.existsByName(anyString())).thenReturn(true);
        when(gameRepository.save(any(Game.class))).thenReturn(game);
        when(gameGenreService.existsByGameIdAndGenreId(anyLong(), anyLong())).thenReturn(true);
        when(gameCompanyService.existsByGameIdAndCompanyId(anyLong(), anyLong())).thenReturn(true);
        when(gameLanguageService.existsByGameIdAndLanguageId(anyLong(), anyLong())).thenReturn(true);
        when(gameGenreService.save(any(GameGameDetailsDto.class))).thenReturn(gameGenreDto);
        when(gameCompanyService.save(any(GameGameDetailsDto.class))).thenReturn(gameCompanyDto);
        when(gameLanguageService.save(any(GameGameDetailsDto.class))).thenReturn(gameLanguageDto);
    }

    @Test
    @DisplayName("Rating game!")
    void testRateGame_Success() {
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));
        when(gameRepository.saveGameRating(anyDouble(), anyLong())).thenReturn(1);
        double userRating = 10;
        double newGlobalRating = gameServiceImp.rateGame(game.getId(), userRating);
        assertNotNull(newGlobalRating);
        double expectedGlobalRating = ((game.getRating() * game.getVotes()) + userRating) / (game.getVotes() + 1);
        assertEquals(expectedGlobalRating, newGlobalRating);
    }

    @Test
    @DisplayName("Rating game: Throwing exception, game not found")
    void testRateGame_Exception() {
        when(gameRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(gameRepository.saveGameRating(anyDouble(), anyLong())).thenReturn(1);
        double userRating = 10;
        assertThrows(ApiException.class, () -> gameServiceImp.rateGame(game.getId(), userRating));
    }
    @Test
    @DisplayName("Deleting game!")
    void testDeleteGame_Success() {
        when(gameRepository.existsById(anyLong())).thenReturn(true);
        gameServiceImp.delete(1L);
    }
    @Test
    @DisplayName("Deleting game: Throwing exception, game not found")
    void testDeleteGame_Exception() {
        when(gameRepository.existsById(anyLong())).thenReturn(false);
        assertThrows(ApiException.class, () -> gameServiceImp.delete(1L));
    }
    @Test
    @DisplayName("Game does exist by id")
    void testGameExistsById_True() {
        when(gameRepository.existsById(anyLong())).thenReturn(true);
        assertTrue(gameServiceImp.existsById(1L));
    }
    @Test
    @DisplayName("Game doesnt exist by id")
    void testGameExistsById_False() {
        when(gameRepository.existsById(anyLong())).thenReturn(false);
        assertFalse(gameServiceImp.existsById(1L));
    }
}