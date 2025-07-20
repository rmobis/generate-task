package com.logmaster.domain;

import javax.annotation.Nullable;

import lombok.Getter;

/**
 * Represents verification options for task completion.
 * Used to determine how a task's completion should be verified.
 */
@Getter
public class Verification {
    /**
     * The verification method (e.g., "collection-log", "achievement-diary").
     */
    private String method;
    
    // Collection log specific fields
    @Nullable
    private int[] itemIds;
    
    @Nullable
    private Integer count;
    
    // Achievement diary specific fields
    @Nullable
    private String region;
    
    @Nullable
    private String difficulty;
}
