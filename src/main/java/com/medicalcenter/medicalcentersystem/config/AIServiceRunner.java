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
        // 1. Create the process builder
        // Ensure "python" is in your System PATH. If not, use the full path like "C:\\Python39\\python.exe"
        ProcessBuilder processBuilder = new ProcessBuilder("python", "app.py");

        // 2. Set the working directory
        // FIX: Use "ai_service" directly, not "medical-center-system/ai_service"
        processBuilder.directory(new File("ai_service"));

        // 3. Redirect output so you can see Python logs in your Java console
        processBuilder.redirectErrorStream(true);
        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);

        try {
            System.out.println("--- Spring Boot application is ready. Starting Python AI Service... ---");
            processBuilder.start();
            System.out.println("--- Python AI Service started successfully. ---");
        } catch (IOException e) {
            System.err.println("--- FAILED to start Python AI Service. ---");
            System.err.println("Make sure 'python' is in your system's PATH and the script exists.");
            e.printStackTrace();
        }
    }
}