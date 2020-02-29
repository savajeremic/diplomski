/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.service.implementation;

import com.project.raider.dao.Orders;
import com.project.raider.repository.OrdersRepository;
import com.project.raider.service.OrdersService;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sava
 */
@Service
public class OrdersServiceImp implements OrdersService {

    @Autowired
    private final OrdersRepository ordersRepository;

    public OrdersServiceImp(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Override
    public Orders save(double totalPrice) {
        String uniqueId = "#" + UUID.randomUUID().toString().replace("-", "");
        return ordersRepository.save(new Orders(new Date(), totalPrice, uniqueId));
    }

    @Override
    public boolean isUnique(String ordersCode) {
        return !ordersRepository.existsByOrdersCode(ordersCode);
    }
    
    public String generateRandomUUID() {
        String uniqueId = "#" + UUID.randomUUID().toString().replace("-", "");
        if(isUnique(uniqueId)){
            uniqueId = generateRandomUUID();
        }
        return uniqueId;
    }

}
