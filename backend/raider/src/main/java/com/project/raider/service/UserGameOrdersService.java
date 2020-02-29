/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.service;

import com.project.raider.dao.Orders;
import com.project.raider.dao.UserGame;
import com.project.raider.dao.UserGameOrders;
import com.project.raider.dto.OrdersDto;
import java.util.List;

/**
 *
 * @author Sava
 */
public interface UserGameOrdersService {
    
    UserGameOrders save(Orders orderId, UserGame gameId);
    List<OrdersDto> getOrders(long userId);
}
