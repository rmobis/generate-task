package com.logmaster.domain.verification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CollectionLogVerification extends Verification {
    private @NonNull Set<Integer> itemIds;
    private int count;
}
