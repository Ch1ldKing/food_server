package org.nus.food_server.Controller;

import org.nus.food_server.DTO.IngredientRequest;
import org.nus.food_server.Entity.Recipe;
import org.nus.food_server.Service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @PostMapping("/search")
    public List<Recipe> searchRecipesByIngredients(@RequestBody IngredientRequest ingredients) {
        return recipeService.getRecipesByIngredients(ingredients.getIngredients());
    }
}
