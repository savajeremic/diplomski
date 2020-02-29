/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.repository;

import com.project.raider.dao.Orders;
import com.project.raider.dao.UserGameOrders;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.project.raider.dto.UserOrders;
import java.math.BigInteger;

/**
 *
 * @author Sava
 */
public interface UserGameOrdersRepository extends JpaRepository<UserGameOrders, Long> {
    @Query(value = "SELECT o.id as ordersId, o.orders_code as ordersCode, " +
            " o.orders_date as ordersDate, o.orders_total as ordersTotal, ug.GAME_ID as gameId \n"//
            + " FROM user_game_orders as ugo\n"
            + " JOIN user_game as ug ON ugo.user_game_id = ug.ID\n"
            + " JOIN orders as o ON ugo.order_id = o.id\n"
            + " WHERE ugo.user_game_id IN (SELECT ug.ID as id "
            + " FROM user_game as ug WHERE ug.USER_ID = :userId AND ug.GAME_FLAG_ID = 3)"
            , nativeQuery = true)
    List<UserOrders> getUserOrdersByUserId(@Param("userId") Long userId);
}
