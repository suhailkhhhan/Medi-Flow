@echo off
ECHO "--- Starting Python AI Service... ---"
start "AI Service" cmd /k "cd ai_service && venv\Scripts\activate && python app.py"

ECHO "--- Starting Spring Boot Application... ---"
call mvnw spring-boot:run