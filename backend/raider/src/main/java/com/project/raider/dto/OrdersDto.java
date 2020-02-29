/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Sava
 */
public class OrdersDto {
    private long id;
    private Date date;
    private double total;
    private String code;
    private List<GameDto> games;

    public OrdersDto() {
    }

    public OrdersDto(long id, Date date, double total, String code, List<GameDto> games) {
        this.id = id;
        this.date = date;
        this.total = total;
        this.code = code;
        this.games = games;
    }

    public OrdersDto(Date date, double total, String code, List<GameDto> games) {
        this.date = date;
        this.total = total;
        this.code = code;
        this.games = games;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<GameDto> getGames() {
        return games;
    }

    public void setGames(List<GameDto> games) {
        this.games = games;
    }

    @Override
    public String toString() {
        List<String> ordersGamesNames = new ArrayList<>(); 
        games.stream().forEach((g)->ordersGamesNames.add(g.getName()));
        return "OrdersDto{" + "ordersId=" + id + ", ordersDate=" 
                + date + ", ordersTotal=" + total + ", ordersCode="
                + code + ", ordersGames=" + ordersGamesNames + '}';
    }
}
