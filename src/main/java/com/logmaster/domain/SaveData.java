package com.logmaster.domain;

import com.logmaster.domain.Task;
import com.logmaster.domain.TaskTier;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@ToString
public class SaveData {
    public SaveData() {
        this.progress = new HashMap<>();

        for (TaskTier tier : TaskTier.values()) {
            this.progress.put(tier, new HashSet<>());
        }
    }

    // We have to leave this here in case someone was running the old version of the plugin
    @Getter
    private HashMap<Integer, Integer> completedTasks = new HashMap<>();

    public Task currentTask;

    // New save data!
    @Getter
    private Map<TaskTier, Set<Integer>> progress;

    @Getter
    @Setter
    private TaskPointer activeTaskPointer;
    @Getter
    @Setter
    private TaskTier selectedTier;
}
