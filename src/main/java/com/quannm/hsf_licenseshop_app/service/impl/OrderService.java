package com.quannm.hsf_licenseshop_app.service.impl;

import com.quannm.hsf_licenseshop_app.dao.OrderDAO;
import com.quannm.hsf_licenseshop_app.entity.Order;
import com.quannm.hsf_licenseshop_app.service.IOrderService;

import java.util.List;

public class OrderService implements IOrderService {

    private final OrderDAO orderDAO;

    public OrderService() {
        this.orderDAO = new OrderDAO();
    }

    @Override
    public List<Order> findByBuyerId(Long buyerId) {
        return orderDAO.findByBuyerId(buyerId);
    }
}