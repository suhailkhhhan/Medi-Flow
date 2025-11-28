package com.medicalcenter.medicalcentersystem.controller;

import com.medicalcenter.medicalcentersystem.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    // A simple method to get the list of symptoms from the CSV header
    private List<String> getSymptomsList() {
        List<String> symptoms = new ArrayList<>();
        // Make sure the path to your Training.csv is correct
        String csvFile = "D:/P/medical-center-system/ai_service/Training.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String header = br.readLine();
            String[] columns = header.split(",");
            for (int i = 0; i < columns.length - 1; i++) { // Exclude the last 'prognosis' column
                symptoms.add(columns[i].replace("_", " "));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return symptoms;
    }

    @GetMapping("/disease-predictor")
    public ModelAndView showPredictorForm() {
        ModelAndView mav = new ModelAndView("disease-predictor");
        mav.addObject("symptomsList", getSymptomsList());
        return mav;
    }

    @PostMapping("/predict-disease")
    public ModelAndView predictDisease(@RequestParam Map<String, String> selectedSymptoms) {
        ModelAndView mav = new ModelAndView("disease-predictor-result");

        // Prepare the symptom map for the API (0 for not present, 1 for present)
        Map<String, Integer> symptomsMap = new HashMap<>();
        for (String symptom : getSymptomsList()) {
            symptomsMap.put(symptom.replace(" ", "_"), selectedSymptoms.containsKey(symptom) ? 1 : 0);
        }

        String predictedDisease = predictionService.predictDisease(symptomsMap);
        mav.addObject("predictedDisease", predictedDisease);

        return mav;
    }
}