package org.nus.food_server.Mapper;

import org.nus.food_server.Entity.Ingredient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IngredientMapper {
    @Select("SELECT * FROM ingredients")
    public List<Ingredient> readAll();
}
