package com.medicalcenter.medicalcentersystem.service;

import com.medicalcenter.medicalcentersystem.model.PredictionResponse; // <-- IMPORT THE NEW CLASS
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class PredictionService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String predictionApiUrl = "http://127.0.0.1:5000/predict";

    public String predictDisease(Map<String, Integer> symptoms) {
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("symptoms", symptoms);

            // --- THIS IS THE CORRECTED LINE ---
            // Tell RestTemplate to expect a PredictionResponse object
            PredictionResponse response = restTemplate.postForObject(predictionApiUrl, requestBody, PredictionResponse.class);
            // --- END OF CORRECTION ---

            return response != null ? response.getPredictedDisease() : "Could not predict disease.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error connecting to the prediction service.";
        }
    }
}