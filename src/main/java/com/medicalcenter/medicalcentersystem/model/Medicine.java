package com.medicalcenter.medicalcentersystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDate; // <-- Make sure to add this import

@Data
@Entity
@Table(name = "medicines")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String manufacturer;
    private int quantity;
    private double price;

    // --- ADD THIS FIELD TO YOUR FILE ---
    private LocalDate expiryDate;
}