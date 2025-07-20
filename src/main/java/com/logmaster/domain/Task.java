package com.logmaster.domain;

import javax.annotation.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private String id;
    private String name;
    private int displayItemId;
    
    @Nullable
    private Verification verification;

    public String getVerificationMethod() {
        return verification != null ? verification.getMethod() : "";
    }
    
    // Collection log specific methods
    @Nullable
    public int[] getItemIds() {
        return verification != null ? verification.getItemIds() : null;
    }

    @Nullable
    public Integer getCount() {
        return verification != null ? verification.getCount() : null;
    }

    // Achievement diary specific methods
    @Nullable
    public String getRegion() {
        return verification != null ? verification.getRegion() : null;
    }

    @Nullable
    public String getDifficulty() {
        return verification != null ? verification.getDifficulty() : null;
    }
}
