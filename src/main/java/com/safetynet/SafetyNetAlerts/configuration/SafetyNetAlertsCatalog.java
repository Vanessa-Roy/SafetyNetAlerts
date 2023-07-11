package com.safetynet.SafetyNetAlerts.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class SafetyNetAlertsCatalog {

    private JsonNode dataJson;

    public SafetyNetAlertsCatalog() throws IOException {
        dataJson = readFile();
    }

    private static JsonNode readFile() throws IOException {
        final String RESOURCE_NAME = "data.json";
        Resource data = new ClassPathResource(RESOURCE_NAME);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(data.getFile());
        return jsonNode;
    }
    public String getJsonString(String key) throws IOException {

        JsonNode jsonArray = dataJson.get(key);
        String jsonString = jsonArray.toString();

        return jsonString;
    }
}
