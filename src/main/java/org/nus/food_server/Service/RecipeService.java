package org.nus.food_server.Service;

import org.nus.food_server.Mapper.RecipeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nus.food_server.Entity.Recipe;
import org.springframework.web.client.RestTemplate;

@Service
public class RecipeService {

    @Autowired
    private RecipeMapper recipeMapper;

    @Autowired
    private RestTemplate restTemplate;

    public List<Recipe> getRecipesByIngredients(List<String> ingredients) {
        return recipeMapper.findByIngredients(ingredients);
    }

    public String getRecommendations(int recipeId) {
        String url = "http://localhost:5000/recommend";
        Map<String, Integer> request = new HashMap<>();
        request.put("recipe_id", recipeId);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        return response.getBody();
    }
}
