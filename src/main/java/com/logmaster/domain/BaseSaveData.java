package com.logmaster.domain;

import lombok.Getter;
import lombok.ToString;

import javax.annotation.Nullable;

@ToString
@Getter
public class BaseSaveData {
    public static final Integer LATEST_VERSION = 1;

    protected @Nullable Integer version;
}
