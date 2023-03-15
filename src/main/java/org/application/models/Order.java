package org.application.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class Order {
    private int orderId;
    private Status status;
    private User user;
    private String orderDate;
    private double orderTotal;
    private User waiter;
    private User chef;
    private List<OrderFood> orderFoods;
}
