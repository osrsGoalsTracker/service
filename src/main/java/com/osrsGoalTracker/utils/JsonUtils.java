package com.osrsGoalTracker.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for JSON serialization and deserialization.
 */
@Slf4j
public final class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonUtils() {
        // Private constructor to prevent instantiation
    }

    /**
     * Converts an object to its JSON string representation.
     *
     * @param object The object to convert
     * @return The JSON string representation of the object
     * @throws RuntimeException if the conversion fails
     */
    public static String toJson(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert object to JSON", e);
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }

    /**
     * Converts a JSON string to an object of the specified type.
     *
     * @param json  The JSON string to convert
     * @param clazz The class of the object to create
     * @param <T>   The type of the object to create
     * @return The object created from the JSON string
     * @throws RuntimeException if the conversion fails
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert JSON to object", e);
            throw new RuntimeException("Failed to convert JSON to object", e);
        }
    }
}