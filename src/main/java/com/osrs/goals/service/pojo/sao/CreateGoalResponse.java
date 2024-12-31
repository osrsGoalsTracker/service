package com.osrs.goals.service.pojo.sao;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response object for the create goal operation.
 * Contains the ID of the newly created goal.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateGoalResponse {
    private String id;
    private String createdAt;
    private String updatedAt;
}
