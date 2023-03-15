package org.application.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderedFoodUnit {
    private Food food;
    private int quantity;
    private double unitPrice;
}
