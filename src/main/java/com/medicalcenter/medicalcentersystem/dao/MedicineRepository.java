package com.medicalcenter.medicalcentersystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medicalcenter.medicalcentersystem.model.Medicine;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
}