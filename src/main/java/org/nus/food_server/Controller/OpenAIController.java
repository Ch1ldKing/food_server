package org.nus.food_server.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.regex.*;
import org.nus.food_server.DTO.IngredientRequest;
import org.nus.food_server.DTO.RecipeResponse;
import org.nus.food_server.Entity.ChatCompletionResponse;
import org.nus.food_server.Entity.Ingredient;
import org.nus.food_server.Entity.Ingredients;
import org.nus.food_server.Service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cha")
public class OpenAIController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OpenAIService openAIService;

//    @PostMapping("/generate-recipe")
//    public ChatCompletionResponse generateRecipe(@RequestBody Map<String, Object> request) {
//        String ingredients = (String) request.get("ingredients");
//        return openAIService.generateRecipe(ingredients);
//    }

//    @PostMapping("/generate-recipe")
//    public ResponseEntity<RecipeResponse> generateRecipe(@RequestBody IngredientRequest ingredientRequest) {
//        List<String> ingredients = ingredientRequest.getIngredients();
//
//        if (ingredients == null || ingredients.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        String pythonServiceUrl = "http://localhost:5000/generate-recipe";
//
//        // 准备请求体
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("ingredients", ingredients);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
//
//        ResponseEntity<Map> response = restTemplate.postForEntity(pythonServiceUrl, entity, Map.class);
//
//        if (response.getStatusCode() == HttpStatus.OK) {
//            Map<String, Object> responseBody = response.getBody();
//            String recipeDetails = (String) responseBody.get("details");
//
//            // 解析GPT输出内容并转换为RecipeResponse
//            RecipeResponse recipeResponse = new RecipeResponse();
//            String[] parts = recipeDetails.split(", Process:");
//            if (parts.length == 2) {
//                recipeResponse.setDishName(parts[0].replace("{Dish_name:", "").replace("\"", "").trim());
//                recipeResponse.setProcess(parts[1].replace("\"", "").replace("}", "").trim());
//            }
//
//            return new ResponseEntity<>(recipeResponse, HttpStatus.OK);
//        } else {
//            Map<String, Object> errorResponse = new HashMap<>();
//            errorResponse.put("error", "Failed to get response from Python service");
//            errorResponse.put("details", response.getBody());
//            return new ResponseEntity(errorResponse, response.getStatusCode());
//        }
//    }
@PostMapping("/generate-recipe")
public ResponseEntity<?> generateRecipe(@RequestBody IngredientRequest ingredientRequest) {
    List<String> ingredients = ingredientRequest.getIngredients();

    if (ingredients == null || ingredients.isEmpty()) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "No ingredients provided");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

//    String pythonServiceUrl = "http://localhost:5000/generate-recipe";
    String pythonServiceUrl = "http://127.0.0.1:4523/m1/4807115-4461497-default/generate-recipe";

    // 准备请求体
    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("ingredients", ingredients);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> response = restTemplate.postForEntity(pythonServiceUrl, entity, String.class);

    try {
        String jsonString = response.getBody();
        System.out.println("Raw JSON Response: " + jsonString);

        // 提取内部的 JSON 字符串
        Pattern recipePattern = Pattern.compile("\\{\"recipe\":\"\\{(.*?)\\}\"\\}");
        Matcher recipeMatcher = recipePattern.matcher(jsonString);
        String innerJson = null;

        if (recipeMatcher.find()) {
            innerJson = recipeMatcher.group(1).replace("\\n", "\n").replace("\\\"", "\"");
            System.out.println("Extracted Inner JSON: " + innerJson);
        } else {
            System.out.println("No match for inner JSON");
        }

        // 使用正则表达式提取 Dish_name 和 Process
        Pattern dishNamePattern = Pattern.compile("Dish_name:\"(.*?)\"");
        Pattern processPattern = Pattern.compile("Process:\"(.*?)\"");

        Matcher dishNameMatcher = dishNamePattern.matcher(innerJson);
        Matcher processMatcher = processPattern.matcher(innerJson);

        String dishName = null;
        String process = null;

        if (dishNameMatcher.find()) {
            dishName = dishNameMatcher.group(1);
        }

        if (processMatcher.find()) {
            process = processMatcher.group(1);
        }

        RecipeResponse recipeResponse = new RecipeResponse(dishName, process);
        System.out.println("Dish Name: " + recipeResponse.getDishName());
        System.out.println("Process: " + recipeResponse.getProcess());

//        RecipeResponse recipeResponse = objectMapper.readValue(jsonString, RecipeResponse.class);
//        System.out.println("Dish Name: " + recipeResponse.getDishName());
//        System.out.println("Process: " + recipeResponse.getProcess());
        if (response.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>(recipeResponse, HttpStatus.OK);
        } else {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to get response from Python service");
            errorResponse.put("details", response.getBody());
            return new ResponseEntity<>(errorResponse, response.getStatusCode());
        }
    } catch (Exception e) {
        e.printStackTrace();
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Exception occurred");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    if (response.getStatusCode() == HttpStatus.OK) {
//        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
//    } else {
//        Map<String, Object> errorResponse = new HashMap<>();
//        errorResponse.put("error", "Failed to get response from Python service");
//        errorResponse.put("details", response.getBody());
//        return new ResponseEntity<>(errorResponse, response.getStatusCode());
//    }
}
}
