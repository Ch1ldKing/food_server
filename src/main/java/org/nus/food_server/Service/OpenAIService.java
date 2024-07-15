package org.nus.food_server.Service;

import org.nus.food_server.DTO.ChatRequest;
import org.nus.food_server.DTO.Message;
import org.nus.food_server.Entity.ChatCompletionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    private final RestTemplate restTemplate;

    @Autowired
    public OpenAIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

//    public ChatCompletionResponse generateRecipe(String ingredients) {
//        String userPrompt = "{" + ingredients + "}";
//        String systemPrompt = "你是美食创新专家。请严格使用以下食材，发挥创意，创造一个新的菜谱，制作步骤要包括尽可能详细的信息，你只能回复的格式为{Dish_name:\"\" Process:\"1.2.3.等等\"}";
//        String json = "{"
//                + "\"model\": \"gpt-3.5-turbo\","
//                + "\"messages\": [{\"role\": \"system\", \"content\": \""+ systemPrompt + "\"},"
//                + "{\"role\": \"user\", \"content\": \"" + userPrompt + "\"}],"
//                + "\"max_tokens\": 100"
//                + "}";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(openaiApiKey);
//        System.out.println("Authorization: Bearer " + openaiApiKey);
//
//
//        HttpEntity<String> request = new HttpEntity<>(json, headers);
//
//        ResponseEntity<ChatCompletionResponse> response = restTemplate.postForEntity(
//                "https://api.openai.com/v1/chat/completions",
//                request,
//                ChatCompletionResponse.class
//        );
//
//        if (response.getStatusCode() == HttpStatus.OK) {
//            return response.getBody();
//        } else {
//            throw new RuntimeException("Failed to get response from OpenAI API");
//        }
//    }

    public ChatCompletionResponse generateRecipe(String ingredients) {
        // Create ChatRequest object
        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setModel("gpt-3.5-turbo");
//        chatRequest.setMaxTokens(100);

        Message systemMessage = new Message();
        systemMessage.setRole("system");
        systemMessage.setContent("你是美食创新专家。请严格使用以下食材，发挥创意，创造一个新的菜谱，制作步骤要包括尽可能详细的信息，你只能回复的格式为{Dish_name:\"\" Process:\"1.2.3.等等\"}");

        Message userMessage = new Message();
        userMessage.setRole("user");
        userMessage.setContent("{" + ingredients + "}");

        List<Message> messages1 = new ArrayList<>();
        messages1.add(systemMessage);
        messages1.add(userMessage);
        chatRequest.setMessages(messages1);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openaiApiKey);
        System.out.println(headers.get("Authorization"));

        // Create HttpEntity
        HttpEntity<ChatRequest> request = new HttpEntity<>(chatRequest, headers);

        // Send request 
        ResponseEntity<ChatCompletionResponse> response = restTemplate.postForEntity(
                "https://api.openai.com/v1/chat/completions",
                request,
                ChatCompletionResponse.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to get response from OpenAI API");
        }
    }
}
