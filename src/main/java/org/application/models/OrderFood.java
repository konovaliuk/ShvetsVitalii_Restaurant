package org.application.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderFood {
    private int orderId;
    private List<OrderedFoodUnit> orderedFoodUnits;
}
