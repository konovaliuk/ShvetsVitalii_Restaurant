package org.application.dao;

import org.application.models.OrderFood;
import org.application.models.OrderFoodId;

import java.util.List;

public interface OrderFoodDAO{
    OrderFood get(OrderFoodId id);
    List<OrderFood> getByOrderId(int orderId);
    List<OrderFood> getAll();
    OrderFood create(OrderFood orderFood);
    void update(OrderFood orderFood);
    void delete(OrderFoodId id);
}
