package org.application.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Food {
    private int foodId;
    private String name;
    private double unitPrice;
    private String description;
    private String imagePath;
    private List<Category> categories;
}
