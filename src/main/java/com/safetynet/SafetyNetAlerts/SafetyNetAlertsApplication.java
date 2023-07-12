package com.safetynet.SafetyNetAlerts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SafetyNetAlertsApplication {
	private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

	public static void main(String[] args) {
		logger.info("Initializing SafetyNet Alerts Application");
		SpringApplication.run(SafetyNetAlertsApplication.class, args);
	}

}
