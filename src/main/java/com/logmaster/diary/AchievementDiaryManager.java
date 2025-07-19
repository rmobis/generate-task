package com.logmaster.diary;

import net.runelite.api.Client;
import net.runelite.api.VarbitComposition;
import net.runelite.api.ChatMessageType;
import lombok.extern.slf4j.Slf4j;

import com.logmaster.LogMasterPlugin;
import com.logmaster.domain.Task;
import com.logmaster.domain.TaskTier;
import com.logmaster.task.TaskService;

import javax.inject.Inject;

@Slf4j
public class AchievementDiaryManager {

    @Inject
    private Client client;

    @Inject
    private TaskService taskService;

    @Inject
    private LogMasterPlugin plugin;

    public void initialise() {
    }

    public boolean isAchievementDiaryCompleted(int diaryID, int count) {
        boolean isCompleted = getVarbitValue(diaryID) == count;
        return isCompleted;
    }

    private int getVarbitValue(int varbitId)
    {
        VarbitComposition v = client.getVarbit(varbitId);
        if (v == null)
        {
            return -1;
        }

        int value = client.getVarpValue(v.getIndex());
        int lsb = v.getLeastSignificantBit();
        int msb = v.getMostSignificantBit();
        int mask = (1 << ((msb - lsb) + 1)) - 1;
        return (value >> lsb) & mask;
    }

    public void sync() {
        // Check all tasks across all tiers for achievement diary verification
        for (TaskTier tier : TaskTier.values()) {
            log.info("==== Syncing Diary for tier: {} ====", tier.displayName);
            for (Task task : taskService.getTaskList().getForTier(tier)) {
                // Only process tasks that use achievement-diary verification method
                if (!task.getVerificationMethod().equals("achievement-diary")) {
                    continue;
                }
                
                Integer varbit = task.getVarbit();
                int count = task.getCount() != null ? task.getCount() : 1;
                if (varbit == null) {
                    log.warn("Task '{}' has achievement-diary verification but no varbit specified", task.getName());
                    continue;
                }

                boolean isDiaryCompleted = isAchievementDiaryCompleted(varbit, count);
                boolean isTaskCompleted = plugin.isTaskCompleted(task.getId(), tier);

                log.info("Checking task '{}': {}",
                    task.getName(), isDiaryCompleted);

                if (isDiaryCompleted && !isTaskCompleted) {
                    // Diary is completed but task is not marked as completed - mark it as completed
                    plugin.completeTask(task.getId(), tier, false);
                    client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", 
                        tier.displayName + " tier task '" + task.getName() + "' marked as <col=27ae60>completed</col> (achievement diary verified).", null);
                    log.debug("Task '{}' marked as completed for tier {} (achievement diary completed)", task.getName(), tier.displayName);
                } else if (!isDiaryCompleted && isTaskCompleted) {
                    // Diary is not completed but task is marked as completed - unmark it
                    plugin.completeTask(task.getId(), tier, false);
                    client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", 
                        tier.displayName + " tier task '" + task.getName() + "' marked as <col=c0392b>incomplete</col> (achievement diary not completed).", null);
                    log.debug("Task '{}' unmarked as completed for tier {} (achievement diary not completed)", task.getName(), tier.displayName);
                }
            }
        }
    }
}
