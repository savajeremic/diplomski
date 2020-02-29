/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Sava
 */
@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "orders_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ordersDate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "orders_total")
    private double ordersTotal;

    @Basic(optional = false)
    @NotNull
    @Column(name = "orders_code")
    private String ordersCode;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderId")
    private List<UserGameOrders> userGameOrdersList;

    public Orders() {
    }

    public Orders(Long id) {
        this.id = id;
    }

    public Orders(Date ordersDate, double ordersTotal, String ordersCode) {
        this.ordersDate = ordersDate;
        this.ordersTotal = ordersTotal;
        this.ordersCode = ordersCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrdersDate() {
        return ordersDate;
    }

    public void setOrdersDate(Date ordersDate) {
        this.ordersDate = ordersDate;
    }

    public double getOrdersTotal() {
        return ordersTotal;
    }

    public void setOrdersTotal(double ordersTotal) {
        this.ordersTotal = ordersTotal;
    }

    public String getOrdersCode() {
        return ordersCode;
    }

    public void setOrdersCode(String ordersCode) {
        this.ordersCode = ordersCode;
    }

    public List<UserGameOrders> getUserGameOrdersList() {
        return userGameOrdersList;
    }

    public void setUserGameOrdersList(List<UserGameOrders> userGameOrdersList) {
        this.userGameOrdersList = userGameOrdersList;
    }

    @Override
    public String toString() {
        return "com.project.raider.dao.Orders[ id=" + id + " ]";
    }

}
