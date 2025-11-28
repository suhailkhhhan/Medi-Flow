package com.medicalcenter.medicalcentersystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;

// This class represents the JSON response: {"predicted_disease": "Some Disease"}
public class PredictionResponse {

    @JsonProperty("predicted_disease")
    private String predictedDisease;

    // Standard getter and setter
    public String getPredictedDisease() {
        return predictedDisease;
    }

    public void setPredictedDisease(String predictedDisease) {
        this.predictedDisease = predictedDisease;
    }
}