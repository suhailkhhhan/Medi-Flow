package com.medicalcenter.medicalcentersystem.dao;

import com.medicalcenter.medicalcentersystem.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.domain.Page; // Import Page
import org.springframework.data.domain.Pageable; 

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    List<Patient> findByNameContainingIgnoreCase(String keyword);

    Page<Patient> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
}
