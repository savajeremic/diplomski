/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.dao;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Sava
 */
@Entity
@Table(name = "user_game_orders")
public class UserGameOrders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private long id;

    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Orders orderId;

    @JoinColumn(name = "user_game_id", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private UserGame userGameId;

    public UserGameOrders() {
    }

    public UserGameOrders(long id) {
        this.id = id;
    }

    public UserGameOrders(Orders orderId, UserGame userGameId) {
        this.orderId = orderId;
        this.userGameId = userGameId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Orders getOrderId() {
        return orderId;
    }

    public void setOrderId(Orders orderId) {
        this.orderId = orderId;
    }

    public UserGame getUserGameId() {
        return userGameId;
    }

    public void setUserGameId(UserGame userGameId) {
        this.userGameId = userGameId;
    }

    @Override
    public String toString() {
        return "UserGameOrders{" + "id=" + id + ", orderId=" + orderId + ", userGameId=" + userGameId + '}';
    }

}
