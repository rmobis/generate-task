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
    private Verification verification;
    
    @Nullable
    public int[] getItemIds() {
        return verification != null ? verification.getItemIds() : null;
    }

    public Integer getCount() {
        return verification != null ? verification.getCount() : 0;
    }

    public String getVerificationMethod() {
        return verification != null ? verification.getMethod() : "";
    }
}
