package org.nus.food_server.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ChatCompletionResponse {

    private String id;
    private String object;
    private int created;
    private List<Choice> choices;

    public static class Choice {
        private int index;
        private Message message;
        @JsonProperty("finish_reason")
        private String finishReason;

        // Getters and setters
    }

    public static class Message {
        private String role;
        private String content;

        // Getters and setters
    }

    // Getters and setters
}
