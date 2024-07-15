package org.nus.food_server.Controller;
import org.nus.food_server.DTO.ChatRequest;
import org.nus.food_server.DTO.ChatResponse;
import org.nus.food_server.Entity.Ingredients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class ChatController {

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @PostMapping("/chat")
    public String chat(@RequestBody Ingredients prompt) {

        String prompt1 =prompt.getIngredientarray();
        // 创建 request
        ChatRequest request = new ChatRequest(model, prompt1);

        // 调用 API
        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        // 返回第一个响应
        return response.getChoices().get(0).getMessage().getContent();
    }
}