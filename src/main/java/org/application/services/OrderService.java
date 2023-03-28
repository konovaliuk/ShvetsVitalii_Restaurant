package org.application.services;

import org.application.models.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    void updateOrder(Order order);
    void deleteOrder(int id);
    Order getOrder(int id);
    List<Order> getAllOrders();
}
