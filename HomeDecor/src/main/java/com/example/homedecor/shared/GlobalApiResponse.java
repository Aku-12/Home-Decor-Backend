package com.example.homedecor.shared;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class GlobalApiResponse<T> {
    private String message;
    private T data;
    private Integer statusCode;
}
