package com.safetynet.SafetyNetAlerts.repository;

import com.safetynet.SafetyNetAlerts.SafetyNetAlertsApplication;
import com.safetynet.SafetyNetAlerts.configuration.SafetyNetAlertsCatalog;
import com.safetynet.SafetyNetAlerts.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepository {

    private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

    @Autowired
    SafetyNetAlertsCatalog data;

    public List<Person> getPersonList() {
        logger.debug("get the list of persons");
        return data.getPersons();
    }
    }
