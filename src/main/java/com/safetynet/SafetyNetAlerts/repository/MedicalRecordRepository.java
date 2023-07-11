package com.safetynet.SafetyNetAlerts.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlerts.configuration.SafetyNetAlertsCatalog;
import com.safetynet.SafetyNetAlerts.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class MedicalRecordRepository {

    @Autowired
    SafetyNetAlertsCatalog data;

    public List<MedicalRecord> findAll() throws IOException {

        String jsonMedicalRecordsArray = data.getJsonString("medicalrecords");
        ObjectMapper objectMapper = new ObjectMapper();
        List<MedicalRecord> medicalRecords = objectMapper.readValue(jsonMedicalRecordsArray, new TypeReference<List<MedicalRecord>>() {});

        return medicalRecords;
    }

}
