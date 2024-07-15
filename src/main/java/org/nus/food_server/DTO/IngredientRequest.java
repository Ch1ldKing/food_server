package org.nus.food_server.DTO;

import lombok.Data;

import java.util.List;

@Data
public class IngredientRequest {

    private List<String> ingredients;

}
