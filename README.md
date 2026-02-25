# MediFlow

MediFlow is a full-stack medical clinic management system built using Spring Boot and MySQL, featuring secure role-based authentication and AI-powered symptom analysis using LLM (ChatGPT) API integration.

---

## Features

- Role-based authentication (Admin / Staff / Patient)
- Doctor management (CRUD + search + pagination)
- Patient registration and management
- Appointment scheduling system
- Pharmacy inventory tracking
- Billing management
- AI-based symptom guidance via LLM API

---

## Tech Stack

Backend:
- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA (Hibernate)
- Maven

Frontend:
- Thymeleaf
- Bootstrap

Database:
- MySQL 8

AI Integration:
- LLM API (ChatGPT)
- REST-based communication

---

## Architecture

Layered architecture:

Controller → Service → Repository → Database

AI Flow:

Spring Boot (8080)
        ↓
External LLM API
        ↓
Structured Response
        ↓
UI Rendering

Key Principles:
- Separation of concerns
- Modular service design
- Secure password hashing (BCrypt)
- Relational schema with foreign keys

---

## Database Schema

Tables:
- users
- user_roles
- patients
- doctors
- appointments
- medicines
- bills

Relationships:
- users → user_roles (1:N)
- users → patients (1:1 optional)
- patients → appointments (1:N)
- doctors → appointments (1:N)
- appointments → bills (1:1)

---

## Setup Instructions

1. Create Database

CREATE DATABASE mediflow;

2. Configure application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/mediflow
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

3. Add AI API Key

ai.api.key=YOUR_API_KEY

4. Run Application

mvn clean install
mvn spring-boot:run

Application URL:
http://localhost:8080

---

## What This Project Demonstrates

- Backend system design
- Role-based security implementation
- Relational database modeling
- Third-party API integration
- Clean layered architecture

---

## Author

Suhail Khan
Backend Developer | Java | Spring Boot
