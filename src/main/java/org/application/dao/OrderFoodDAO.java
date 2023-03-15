package org.application.dao;

import org.application.models.OrderFood;

import java.util.List;

public interface OrderFoodDAO extends DAO<OrderFood>{
    List<OrderFood> getById(int id);
}
