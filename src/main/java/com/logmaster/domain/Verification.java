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
    
    /**
     * Array of item IDs used for verification
     */
    @Nullable
    private int[] itemIds;
    
    /**
     * Required count of items for task completion, or value of varbit
     */
    @Nullable
    private Integer count;
    
    /**
     * Required varbit to check for task completion
     */
    @Nullable
    private Integer varbit;
}
