package com.medicalcenter.medicalcentersystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotBlank(message = "Patient name cannot be blank")
    @Size(min = 2, message = "Name must be at least 2 characters long")
    private String name;
    
    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age cannot be negative")
    @Max(value = 120, message = "Age seems unrealistic")
    private int age;
    
    @NotBlank(message = "Gender cannot be blank")
    private String gender;

    @NotBlank(message = "Contact number cannot be blank")
    @Pattern(regexp = "^[0-9]{10}$", message = "Contact must be a 10-digit number")
    private String contact;

    @Lob
    private String medicalHistory;

    // --- ADD THIS NEW RELATIONSHIP ---
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}