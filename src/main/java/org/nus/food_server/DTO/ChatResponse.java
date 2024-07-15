package org.nus.food_server.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class ChatResponse {

    private List<Choice> choices;

    // 忽略构造函数、get、set 方法
    public static class Choice {

        private int index;

        @Getter
        @Setter
        private Message message;

        // 忽略构造函数、get、set 方法
    }

}