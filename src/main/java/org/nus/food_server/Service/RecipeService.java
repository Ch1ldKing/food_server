package org.nus.food_server.Service;

import org.nus.food_server.Mapper.RecipeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.nus.food_server.Entity.Recipe;

@Service
public class RecipeService {

    @Autowired
    private RecipeMapper recipeMapper;

    public List<Recipe> getRecipesByIngredients(List<String> ingredients) {
        return recipeMapper.findByIngredients(ingredients);
    }
}
