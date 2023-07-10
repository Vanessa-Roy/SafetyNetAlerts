package com.safetynet.SafetyNetAlerts.repository;

import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicalRecordRepository extends BaseRepository {

    public List<MedicalRecord> findAll() throws IOException {

        JSONArray jsonMedicalRecordsArray = readFiletoGetJsonArray("medicalrecords");
        List<MedicalRecord> medicalRecords = new ArrayList<>();

        for(int y=0; y<jsonMedicalRecordsArray.length(); y++) {
            JSONObject jsonMedicalRecords = jsonMedicalRecordsArray.getJSONObject(y);
            MedicalRecord medicalRecord = new MedicalRecord();
            JSONArray jsonMedicationsArray = jsonMedicalRecords.getJSONArray("medications");
            for(int i=0; i<jsonMedicationsArray.length(); i++) {
                medicalRecord.setMedications(jsonMedicationsArray.getString(i));
            }
            JSONArray jsonAllergiesArray = jsonMedicalRecords.getJSONArray("allergies");
            for(int i=0; i<jsonAllergiesArray.length(); i++) {
                medicalRecord.setAllergies(jsonAllergiesArray.getString(i));
            }
            medicalRecord.setFirstName(jsonMedicalRecords.getString("firstName"));
            medicalRecord.setLastName(jsonMedicalRecords.getString("lastName"));
            medicalRecord.setBirthdate(jsonMedicalRecords.getString("birthdate"));
            medicalRecords.add(medicalRecord);
        }
        return medicalRecords;
    }

    //faire tu qui lit bien le fichier

    //faire tu qui filtre le fichier sur les medicalrecords
}
