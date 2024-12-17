package com.osrs.goals.lambda;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
    private int statusCode;
    private String body;
} 