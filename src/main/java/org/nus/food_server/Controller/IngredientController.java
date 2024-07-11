package org.nus.food_server.Controller;

import org.nus.food_server.Entity.Ingredient;
import org.nus.food_server.Service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;

    @PostMapping("/all")
    public List<Ingredient> readAll() {
        return ingredientService.readAll();
    }

}
