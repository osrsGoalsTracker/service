package com.osrs.goals.lambda;

import lombok.Builder;
import lombok.Data;

/**
 * Represents an API response with status code and body.
 */
@Data
@Builder
public class ApiResponse {
    /**
     * The HTTP status code of the response.
     */
    private int statusCode;

    /**
     * The response body content.
     */
    private String body;
}
