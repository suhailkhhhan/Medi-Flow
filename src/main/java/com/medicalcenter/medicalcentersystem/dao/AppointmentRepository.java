package com.medicalcenter.medicalcentersystem.dao;

import com.medicalcenter.medicalcentersystem.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    /**
     * Finds all appointments and eagerly fetches the related Patient and Doctor
     * entities in a single query to prevent LazyInitializationException.
     * The results are ordered by the appointment date in descending order.
     * @return A list of all appointments with their patient and doctor data loaded.
     */
    @Query("SELECT a FROM Appointment a JOIN FETCH a.patient JOIN FETCH a.doctor ORDER BY a.appointmentDate DESC")
    List<Appointment> findAllWithPatientAndDoctor();

}