package com.osrs.goals.data.pojo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Domain model representing a goal in the system.
 * This class contains the core attributes of a goal.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Goal {
    private String id;
    private String createdAt;
    private String updatedAt;
}
