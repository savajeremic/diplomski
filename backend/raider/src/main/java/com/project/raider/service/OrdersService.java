/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.service;

import com.project.raider.dao.Orders;

/**
 *
 * @author Sava
 */
public interface OrdersService {
    Orders save(double totalPrice);
    boolean isUnique(String ordersCode);
}
