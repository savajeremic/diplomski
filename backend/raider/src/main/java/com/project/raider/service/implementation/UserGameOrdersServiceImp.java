/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.service.implementation;

import com.project.raider.dao.Orders;
import com.project.raider.dao.UserGame;
import com.project.raider.dao.UserGameOrders;
import com.project.raider.dto.GameDto;
import com.project.raider.dto.OrdersDto;
import com.project.raider.dto.UserOrders;
import com.project.raider.repository.UserGameOrdersRepository;
import com.project.raider.service.GameService;
import com.project.raider.service.UserGameOrdersService;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sava
 */
@Service
public class UserGameOrdersServiceImp implements UserGameOrdersService {

    @Autowired
    private final UserGameOrdersRepository userGameOrdersRepository;
    
    @Autowired
    private final GameService gameService;

    public UserGameOrdersServiceImp(UserGameOrdersRepository userGameOrdersRepository, GameService gameService) {
        this.userGameOrdersRepository = userGameOrdersRepository;
        this.gameService = gameService;
    }

    @Override
    public UserGameOrders save(Orders orderId, UserGame gameId) {
        return userGameOrdersRepository.save(new UserGameOrders(orderId, gameId));
    }

    @Transactional
    @Override
    public List<OrdersDto> getOrders(long userId) {
        List<UserOrders> userOrdersList = userGameOrdersRepository.getUserOrdersByUserId(userId);
        List<OrdersDto> ordersDtoList = new ArrayList<>();
        List<GameDto> gameDtoList = new ArrayList<>();
        UserOrders currentOrdersDto, lastOrdersDto = null;
        OrdersDto ordersDto;
        int i = userOrdersList.size();

        for (UserOrders uo : userOrdersList) {
            i--;
            currentOrdersDto = uo;
            if (lastOrdersDto == null) {
                lastOrdersDto = uo;
            }
            if (currentOrdersDto.getOrdersId() != lastOrdersDto.getOrdersId()) {
                ordersDto = new OrdersDto(lastOrdersDto.getOrdersId(),
                        lastOrdersDto.getOrdersDate(),
                        lastOrdersDto.getOrdersTotal(),
                        lastOrdersDto.getOrdersCode(), gameDtoList);
                ordersDtoList.add(ordersDto);
                gameDtoList = new ArrayList<>();
            }
            if (i == 0) {
                gameDtoList.add(gameService.getById(currentOrdersDto.getGameId().longValue()));
                ordersDto = new OrdersDto(currentOrdersDto.getOrdersId(),
                        currentOrdersDto.getOrdersDate(),
                        currentOrdersDto.getOrdersTotal(),
                        currentOrdersDto.getOrdersCode(), gameDtoList);
                ordersDtoList.add(ordersDto);
                gameDtoList = new ArrayList<>();
            }
            gameDtoList.add(gameService.getById(currentOrdersDto.getGameId().longValue()));
            lastOrdersDto = currentOrdersDto;
        }
        return ordersDtoList;
    }
}
