package com.medicalcenter.medicalcentersystem.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;

@Component
public class AIServiceRunner implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("--- Spring Boot application is ready. Starting Python AI Service... ---");

        ProcessBuilder processBuilder = new ProcessBuilder(
            "python",
            "app.py" // The script to run
        );

        // --- THIS IS THE FIX ---
        // Provide the full relative path to the ai_service directory
        String projectDirName = "medical-center-system"; // The name of your root project folder
        processBuilder.directory(new File(projectDirName + File.separator + "ai_service"));

        processBuilder.inheritIO();

        try {
            Process process = processBuilder.start();
            System.out.println("--- Python AI Service started successfully. ---");
            
            Runtime.getRuntime().addShutdownHook(new Thread(process::destroy));

        } catch (IOException e) {
            System.err.println("--- FAILED to start Python AI Service. ---");
            System.err.println("Make sure 'python' is in your system's PATH and the script exists.");
            e.printStackTrace();
        }
    }
}