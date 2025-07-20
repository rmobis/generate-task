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
    
    @Nullable
    public int[] getItemIds() {
        return verification != null ? verification.getItemIds() : null;
    }

    @Nullable
    public Integer getCount() {
        return verification != null ? verification.getCount() : null;
    }

    public String getVerificationMethod() {
        return verification != null ? verification.getMethod() : "";
    }

    @Nullable
    public Integer getVarbit() {
        return verification != null ? verification.getVarbit() : null;
    }
}
