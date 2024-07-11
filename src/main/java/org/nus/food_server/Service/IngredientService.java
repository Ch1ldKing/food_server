package org.nus.food_server.Service;

import org.nus.food_server.Entity.Ingredient;
import org.nus.food_server.Mapper.IngredientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    @Autowired
    private IngredientMapper ingredientMapper;
    public List<Ingredient> readAll() {
        return ingredientMapper.readAll();
    }
}
