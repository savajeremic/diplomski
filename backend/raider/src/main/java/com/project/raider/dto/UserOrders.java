/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.dto;

import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author Sava
 */
public interface UserOrders {
    
    long getOrdersId();

    String getOrdersCode();

    Date getOrdersDate();

    double getOrdersTotal();

    BigInteger getGameId();
    
    default String toStrings() {
        return "Orders id: " + getOrdersId() + " Orders Code: " + getOrdersCode() + " | Date: " + getOrdersDate() +  " | Orders Total: " + getOrdersTotal() + " | Game Id: " + getGameId();
    }
}
