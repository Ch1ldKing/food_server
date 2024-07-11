package org.nus.food_server;

import lombok.Data;

@Data
public class ResponseWrapper<T> {
    private T data;
    private int status;

    public ResponseWrapper(T data, int status) {
        this.data = data;
        this.status = status;
    }
}
