package com.safetynet.SafetyNetAlerts.repository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class BaseRepository {

    final String PATH_NAME = "SafetyNetAlerts/src/main/resources/data.json";
    public JSONArray readFiletoGetJsonArray(String key) throws IOException {
        File data = new File(PATH_NAME);
        Path filePath = Path.of(data.getAbsolutePath());
        String jsonString = Files.readString(filePath);
        JSONObject json = new JSONObject(jsonString);
        JSONArray jsonArray = json.getJSONArray(key);

        return jsonArray;
    }

}
