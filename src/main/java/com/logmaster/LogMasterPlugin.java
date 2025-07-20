package com.logmaster;

import com.google.inject.Provides;
import com.logmaster.domain.Task;
import com.logmaster.domain.TaskPointer;
import com.logmaster.domain.TaskTier;
import com.logmaster.domain.verification.AchievementDiaryVerification;
import com.logmaster.domain.verification.CollectionLogVerification;
import com.logmaster.domain.verification.Verification;
import com.logmaster.persistence.SaveDataManager;
import com.logmaster.task.TaskService;
import com.logmaster.ui.InterfaceManager;
import com.logmaster.ui.component.TaskOverlay;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.SoundEffectID;
import net.runelite.api.events.*;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.input.MouseManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.LinkBrowser;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.logmaster.util.GsonOverride.GSON;

@Slf4j
@PluginDescriptor(name = "Collection Log Master")
public class LogMasterPlugin extends Plugin {
    private static final int COLLECTION_LOG_SETUP_SCRIPT_ID = 7797;

	@Inject
	private Client client;

	@Inject
	private LogMasterConfig config;

	@Inject
	private MouseManager mouseManager;

	@Inject
	protected TaskOverlay taskOverlay;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private TaskService taskService;

	@Inject
	private SaveDataManager saveDataManager;

	@Inject
	private InterfaceManager interfaceManager;

	@Override
	protected void startUp()
	{
		String diaryJson = "{\"method\":\"achievement-diary\",\"region\":\"ardougne\",\"difficulty\":\"easy\"}";
		Verification verif = GSON.fromJson(diaryJson, Verification.class);

		if (verif instanceof AchievementDiaryVerification) {
			AchievementDiaryVerification diaryVerif = (AchievementDiaryVerification) verif;
			log.info("{}", diaryVerif.getRegion());
		}

		String clogJson = "{\"method\":\"collection-log\",\"itemIds\":[123],\"count\":1}";
		Verification verif2 = GSON.fromJson(clogJson, Verification.class);

		if (verif2 instanceof CollectionLogVerification) {
			CollectionLogVerification clogVerif = (CollectionLogVerification) verif2;
			log.info("{}", clogVerif.getItemIds());
		}


		mouseManager.registerMouseWheelListener(interfaceManager);
		mouseManager.registerMouseListener(interfaceManager);
		interfaceManager.initialise();
		this.taskOverlay.setResizable(true);
		this.overlayManager.add(this.taskOverlay);
		this.taskService.getTaskList();
	}

	@Override
	protected void shutDown() {
		mouseManager.unregisterMouseWheelListener(interfaceManager);
		mouseManager.unregisterMouseListener(interfaceManager);
		this.overlayManager.remove(this.taskOverlay);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event) {
		if (!event.getGroup().equals("log-master")) {
			return;
		}
		interfaceManager.updateAfterConfigChange();
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged) {
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN) {
			saveDataManager.getSaveData();
		} else if(gameStateChanged.getGameState().equals(GameState.LOGIN_SCREEN)) {
			saveDataManager.save();
		}
	}

	@Subscribe
	public void onWidgetLoaded(WidgetLoaded e) {
		if(e.getGroupId() == InterfaceID.COLLECTION) {
			interfaceManager.handleCollectionLogOpen();
		}
	}

	@Subscribe
	public void onWidgetClosed(WidgetClosed e) {
		if(e.getGroupId() == InterfaceID.COLLECTION) {
			interfaceManager.handleCollectionLogClose();
		}
	}

    @Subscribe
    public void onScriptPostFired(ScriptPostFired scriptPostFired) {
        if (scriptPostFired.getScriptId() == COLLECTION_LOG_SETUP_SCRIPT_ID) {
            interfaceManager.handleCollectionLogScriptRan();
        }
    }

	@Subscribe
	public void onGameTick(GameTick event) {
		interfaceManager.updateTaskListBounds();
	}

	public void generateTask() {
		TaskPointer pointer =this.saveDataManager.getSaveData().getActiveTaskPointer();
		if ((pointer != null && pointer.getTask() != null) || taskService.getTaskList() == null) {
			interfaceManager.disableGenerateTaskButton();
			return;
		}

		this.client.playSoundEffect(SoundEffectID.UI_BOOP);
		List<Task> uniqueTasks = findAvailableTasks();

		if(uniqueTasks.size() <= 0) {
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "No more tasks left. Looks like you win?", "");
			playFailSound();

			return;
		}

		int index = (int) Math.floor(Math.random()*uniqueTasks.size());


		TaskPointer newTaskPointer = new TaskPointer();
		newTaskPointer.setTask(uniqueTasks.get(index));
		newTaskPointer.setTaskTier(getCurrentTier());
		this.saveDataManager.getSaveData().setActiveTaskPointer(newTaskPointer);
		this.saveDataManager.save();
		interfaceManager.rollTask(this.saveDataManager.getSaveData().getActiveTaskPointer().getTask().getName(), this.saveDataManager.getSaveData().getActiveTaskPointer().getTask().getDisplayItemId(), config.rollPastCompleted() ? taskService.getForTier(getCurrentTier()) : uniqueTasks);
		log.debug("Task generated: "+this.saveDataManager.getSaveData().getActiveTaskPointer().getTask().getName());

		this.saveDataManager.save();
	}

	public void completeTask() {
		completeTask(saveDataManager.getSaveData().getActiveTaskPointer().getTask().getId(), saveDataManager.getSaveData().getActiveTaskPointer().getTaskTier());
	}

	public void completeTask(String taskID, TaskTier tier) {
		this.client.playSoundEffect(SoundEffectID.UI_BOOP);

		if (saveDataManager.getSaveData().getProgress().get(tier).contains(taskID)) {
			saveDataManager.getSaveData().getProgress().get(tier).remove(taskID);
		} else {
			addCompletedTask(taskID, tier);
			if (saveDataManager.getSaveData().getActiveTaskPointer() != null && taskID.equals(saveDataManager.getSaveData().getActiveTaskPointer().getTask().getId())) {
				nullCurrentTask();
			}
		}
		this.saveDataManager.save();
		interfaceManager.completeTask();
	}

	public void nullCurrentTask() {
		this.saveDataManager.getSaveData().setActiveTaskPointer(null);
		this.saveDataManager.save();
		interfaceManager.clearCurrentTask();
	}

	public static int getCenterX(Widget window, int width) {
		return (window.getWidth() / 2) - (width / 2);
	}

	public static int getCenterY(Widget window, int height) {
		return (window.getHeight() / 2) - (height / 2);
	}

	public void addCompletedTask(String taskID, TaskTier tier) {
		this.saveDataManager.getSaveData().getProgress().get(tier).add(taskID);
		this.saveDataManager.save();
	}

	public TaskTier getCurrentTier() {
		TaskTier[] allTiers = TaskTier.values();
		int firstVisibleTier = 0;
		for (int i = 0; i < allTiers.length; i++) {
			if (config.hideBelow() == allTiers[i]) {
				firstVisibleTier = i;
			}
		}

		Map<TaskTier, Integer> tierPercentages = taskService.completionPercentages(saveDataManager.getSaveData());
		for (int i = firstVisibleTier; i < allTiers.length; i++) {
			TaskTier tier = allTiers[i];
			if (tierPercentages.get(tier) < 100) {
				return tier;
			}
		}


		return TaskTier.MASTER;
	}

	public TaskTier getSelectedTier() {
		return this.saveDataManager.getSaveData().getSelectedTier();
	}

	public List<Task> findAvailableTasks() {
		return taskService.getTaskList().getForTier(getCurrentTier()).stream().filter(t -> !this.saveDataManager.getSaveData().getProgress().get(getCurrentTier()).contains(t.getId())).collect(Collectors.toList());
	}

	public void playFailSound() {
		client.playSoundEffect(2277);
	}

	@Provides
	LogMasterConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(LogMasterConfig.class);
	}

	public void visitFaq() {
		LinkBrowser.browse("https://docs.google.com/document/d/e/2PACX-1vTHfXHzMQFbt_iYAP-O88uRhhz3wigh1KMiiuomU7ftli-rL_c3bRqfGYmUliE1EHcIr3LfMx2UTf2U/pub");
	}
}
