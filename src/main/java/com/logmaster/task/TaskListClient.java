package com.logmaster.task;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

@Slf4j
@Singleton
public class TaskListClient {

    private static final String TASK_LIST_URL = "https://raw.githubusercontent.com/OSRS-Taskman/task-list/refs/heads/main/lists/tedious.json";

    @Inject
    private OkHttpClient okHttpClient;

    public void getTaskList(Callback callback) throws IOException {
        Request request = new Request.Builder()
                .url(TASK_LIST_URL)
                .get()
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }

    public JsonObject processResponse(Response response) throws IOException {
        if (!response.isSuccessful()) {
            return null;
        }

        ResponseBody resBody = response.body();
        if (resBody == null) {
            return null;
        }
        return new JsonParser().parse(resBody.string()).getAsJsonObject();
    }
}
