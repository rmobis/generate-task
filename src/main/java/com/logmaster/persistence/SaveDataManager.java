package com.logmaster.persistence;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.logmaster.domain.SaveData;
import com.logmaster.domain.Task;
import com.logmaster.domain.TaskTier;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;

import static com.logmaster.LogMasterConfig.CONFIG_GROUP;
import static com.logmaster.LogMasterConfig.SAVE_DATA_KEY;
import static net.runelite.http.api.RuneLiteAPI.GSON;

@Singleton
@Slf4j
public class SaveDataManager {
    @Inject
    private ConfigManager configManager;

    private SaveData saveData;

    public @NonNull SaveData getSaveData() {
        this.saveData = loadSaveData();
        return this.saveData;
    }

    public void save() {
        String json = GSON.toJson(saveData);
        configManager.setRSProfileConfiguration(CONFIG_GROUP, SAVE_DATA_KEY, json);
    }

    public Task currentTask() {
        if (getSaveData().getActiveTaskPointer() == null) {
            return null;
        }
        return getSaveData().getActiveTaskPointer().getTask();
    }

    private SaveData loadSaveData() {
        String json = this.configManager.getRSProfileConfiguration(CONFIG_GROUP, SAVE_DATA_KEY);
        if (json == null) {
            return new SaveData();
        }

        SaveData data = null;
        try {
            data = GSON.fromJson(json, SaveData.class);
        } catch (Exception e) {
            log.error("Unable to parse save data JSON", e);
        }

        if (data != null) {
            return data;
        }

        return new SaveData();
    }
}
