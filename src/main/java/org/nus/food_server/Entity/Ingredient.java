package org.nus.food_server.Entity;

import lombok.Data;

@Data
public class Ingredient {
    private Integer id;
    private String name;
    private String category;
}
