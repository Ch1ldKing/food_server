package org.nus.food_server.Entity;

import lombok.Data;

@Data
public class Recipe {
    private Integer id;
    private String recipe;
    private String ingredients;
    private Integer comment;
    private String url;

}
