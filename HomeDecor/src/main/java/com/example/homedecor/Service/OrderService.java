package com.example.homedecor.Service;

import com.example.homedecor.Entity.Orders;

public interface OrderService {
    void placeOrder(Orders order);
    Orders getOrderById(Long id);
    Iterable<Orders> getAllOrders();
    String getFurnitureName(Long furnitureId);
    Long orderCount();
}
