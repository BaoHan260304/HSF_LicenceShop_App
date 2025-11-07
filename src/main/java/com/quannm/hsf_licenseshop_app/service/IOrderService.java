package com.quannm.hsf_licenseshop_app.service;

import com.quannm.hsf_licenseshop_app.entity.Order;

import java.util.List;

public interface IOrderService {

    List<Order> findByBuyerId(Long buyerId);

}