package com.osrs.goals.lambda;

/**
 * Constants for HTTP status codes used in the application.
 */
public final class HttpStatus {
    private HttpStatus() {
        // Private constructor to prevent instantiation
    }

    public static final int OK = 200;
    public static final int BAD_REQUEST = 400;
    public static final int NOT_FOUND = 404;
    public static final int BAD_GATEWAY = 502;
} 
