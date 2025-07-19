package com.logmaster.domain;

import lombok.Getter;

/**
 * Represents verification options for task completion.
 * Used to determine how a task's completion should be verified.
 */
@Getter
public class Verification {
    /**
     * The verification method (e.g., "collection-log")
     */
    private String method;
    
    /**
     * Array of item IDs used for verification
     */
    private int[] itemIds;
    
    /**
     * Required count of items for task completion
     */
    private Integer count;
    
    /**
     * Required varbit for task completion
     */
    private Integer varbit;
}
