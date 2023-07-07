package com.safetynet.SafetyNetAlerts.repository;

import com.safetynet.SafetyNetAlerts.model.FireStation;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FireStationRepository {

    public List<FireStation> findAll() throws IOException {

        final String MyJSON = "C:/Users/indov/git/SafetyNetAlerts/SafetyNetAlerts/src/main/resources/data.json";
        Path filePath = Path.of(MyJSON);
        String jsonString = Files.readString(filePath);
        JSONObject json = new JSONObject(jsonString);
        JSONArray jsonFireStationsArray = json.getJSONArray("firestations");
        List<FireStation> fireStations = new ArrayList<>();

        for(int y=0; y<jsonFireStationsArray.length(); y++) {
            JSONObject jsonFireStation = jsonFireStationsArray.getJSONObject(y);
            FireStation fireStation = new FireStation();
            fireStation.setStation(jsonFireStation.getString("station"));
            fireStation.setAddress(jsonFireStation.getString("address"));
            fireStations.add(fireStation);
        }
        return fireStations;
    }

    //faire tu qui lit bien le fichier

    //faire tu qui filtre le fichier sur les firestations
}
