package org.application.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "order_food")
@IdClass(OrderFoodId.class)
@AllArgsConstructor
@NoArgsConstructor
public class OrderFood{
    @Id
    @Column(name = "order_id")
    private int orderId;
    @Id
    @Column(name = "food_id")
    private int foodId;
    private int quantity;
    @Column(name = "unit_price")
    private double unitPrice;
}
