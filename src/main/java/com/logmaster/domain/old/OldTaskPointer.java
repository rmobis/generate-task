package com.logmaster.domain.old;

import com.logmaster.domain.TaskTier;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Deprecated
public class OldTaskPointer {

    private TaskTier taskTier;
    private OldTask task;
}
