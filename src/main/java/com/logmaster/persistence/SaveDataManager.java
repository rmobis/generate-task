package com.logmaster.persistence;

import com.google.gson.JsonSyntaxException;
import com.logmaster.domain.BaseSaveData;
import com.logmaster.domain.SaveData;
import com.logmaster.domain.Task;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;

import javax.inject.Inject;
import javax.inject.Singleton;

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

        try {
            BaseSaveData base = GSON.fromJson(json, BaseSaveData.class);
            if (BaseSaveData.LATEST_VERSION.equals(base.getVersion())) {
                return GSON.fromJson(json, SaveData.class);
            }

            // TODO: convert old version
        } catch (JsonSyntaxException e) {
            log.error("Unable to parse save data JSON", e);
        }

        return new SaveData();
    }
}
