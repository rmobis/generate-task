package com.logmaster.domain;

import javax.annotation.Nullable;

import com.logmaster.domain.verification.Verification;

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
        if (verification instanceof com.logmaster.domain.verification.CollectionLogVerification) {
            return ((com.logmaster.domain.verification.CollectionLogVerification) verification).getItemIds();
        }
        return null;
    }

    @Nullable
    public Integer getCount() {
        if (verification instanceof com.logmaster.domain.verification.CollectionLogVerification) {
            return ((com.logmaster.domain.verification.CollectionLogVerification) verification).getCount();
        }
        return null;
    }

    // Achievement diary specific methods
    @Nullable
    public String getRegion() {
        if (verification instanceof com.logmaster.domain.verification.AchievementDiaryVerification) {
            return ((com.logmaster.domain.verification.AchievementDiaryVerification) verification).getRegion();
        }
        return null;
    }

    @Nullable
    public String getDifficulty() {
        if (verification instanceof com.logmaster.domain.verification.AchievementDiaryVerification) {
            return ((com.logmaster.domain.verification.AchievementDiaryVerification) verification).getDifficulty();
        }
        return null;
    }
}
