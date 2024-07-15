package org.nus.food_server.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RecipeResponse {

    private String dishName;
    private String process;

    // Getters and Setters

}
