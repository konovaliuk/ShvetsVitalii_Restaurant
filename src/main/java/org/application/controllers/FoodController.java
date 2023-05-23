package org.application.controllers;

import org.application.models.Food;
import org.application.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class FoodController {
    private final FoodService foodService;
    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }
    @RequestMapping("/html/menu/{page}")
    public String listUsers(@PathVariable int page, Model model) {
        int pageSize = 2;

        Page<Food> pageFood = foodService.getAllFood(page, pageSize);
        List<Food> listFood = pageFood.getContent();
        model.addAttribute("listFood", listFood);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", pageFood.getTotalPages());
        model.addAttribute("startPage", Math.max(1, page - 2));
        model.addAttribute("endPage", Math.min(pageFood.getTotalPages(), page + 2));
        return "menu";
    }
}
