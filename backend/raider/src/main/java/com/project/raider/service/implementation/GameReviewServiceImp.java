/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.service.implementation;

import com.project.raider.dao.Game;
import com.project.raider.dao.GameReview;
import com.project.raider.dao.User;
import com.project.raider.dto.GameReviewDto;
import com.project.raider.dto.UserDto;
import com.project.raider.exception.ApiException;
import com.project.raider.repository.GameRepository;
import com.project.raider.repository.GameReviewRepository;
import com.project.raider.repository.UserRepository;
import com.project.raider.service.GameReviewService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sava
 */
@Service
public class GameReviewServiceImp implements GameReviewService {

    @Autowired
    private final GameReviewRepository gameReviewRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final GameRepository gameRepository;

    public GameReviewServiceImp(GameReviewRepository gameReviewRepository,
            UserRepository userRepository, GameRepository gameRepository) {
        this.gameReviewRepository = gameReviewRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public List<GameReviewDto> getAll() {
        List<GameReview> gameReviewList = gameReviewRepository.findAll();
        List<GameReviewDto> gameReviewDtoList = new ArrayList<>();
        gameReviewList.stream().forEach((gr) -> gameReviewDtoList.add(gameReviewToDto(gr)));
        return gameReviewDtoList;
    }

    @Override
    public List<GameReviewDto> findByGameId(long gameId) {
        List<GameReview> gameReviewList = gameReviewRepository.findByGameId(gameId);
        List<GameReviewDto> gameReviewDtoList = new ArrayList<>();
        gameReviewList.stream().forEach((gr) -> gameReviewDtoList.add(gameReviewToDto(gr)));

        return gameReviewDtoList;
    }

    @Override
    public GameReviewDto save(GameReviewDto gameReviewDto) {
        GameReview exists = gameReviewRepository.findByGameIdAndUser(gameReviewDto.getGameId(), userDtoToUser(gameReviewDto.getUser()));
        if (exists != null) {
            throw new ApiException("User has already rated that game.", HttpStatus.BAD_REQUEST);
        }
        GameReview newGameReview = gameReviewRepository.save(dtoToGameReview(gameReviewDto));
        return gameReviewToDto(newGameReview);
    }

    private GameReviewDto gameReviewToDto(GameReview gameReview) {
        UserDto userDto = userToDtoReview(gameReview.getUser());
        long gameId = gameReview.getGame().getId();
        return new GameReviewDto(gameReview.getId(),
                gameId, userDto, gameReview.getUserRating(), gameReview.getTitle(), gameReview.getComment(), new Date());
    }

    private GameReview dtoToGameReview(GameReviewDto gameReviewDto) {
        User user = userRepository.findById(gameReviewDto.getUser().getId())
                .orElseThrow(() -> new ApiException("User not found."));
        Game game = gameRepository.findById(gameReviewDto.getGameId())
                .orElseThrow(() -> new ApiException("Game not found."));
        return new GameReview(gameReviewDto.getId(), game, user,
                gameReviewDto.getRating(), gameReviewDto.getTitle(), gameReviewDto.getComment(), new Date());
    }

    private User userDtoToUser(UserDto userDto) {
        User user = new User(userDto.getId(), userDto.getUsername(),
                userDto.getPassword(), userDto.getEmail(), userDto.getFullname(),
                userDto.getCountry(), userDto.getAvatar(), userDto.getBirthday());
        return user;
    }

    public UserDto userToDtoReview(User user) {
        UserDto userDto = new UserDto(user.getId(), user.getUsername(),
                user.getEmail(), user.getFullname(), user.getAvatar());
        return userDto;
    }
}
