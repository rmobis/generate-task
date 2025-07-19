package com.logmaster.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskPointer {

    private TaskTier taskTier;
    private Task task;
}
