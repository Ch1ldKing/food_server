package org.nus.food_server.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.nus.food_server.Entity.Recipe;

import java.util.List;

@Mapper
public interface RecipeMapper extends BaseMapper<Recipe>{
    @Select("<script>" +
            "SELECT * FROM recipes WHERE " +
            "<foreach collection='ingredients' item='ingredient' separator=' AND '>" +
            "ingredients ILIKE CONCAT('%', #{ingredient}, '%')" +
            "</foreach>" +
            "</script>")
    List<Recipe> findByIngredients(@Param("ingredients") List<String> ingredients);
}
