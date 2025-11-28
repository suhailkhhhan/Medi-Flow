package com.medicalcenter.medicalcentersystem.service;

import com.medicalcenter.medicalcentersystem.dao.AppointmentRepository;
import com.medicalcenter.medicalcentersystem.dao.BillRepository;
import com.medicalcenter.medicalcentersystem.model.Appointment;
import com.medicalcenter.medicalcentersystem.model.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional; // Add this import

@Service
public class BillingService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    private static final double CONSULTATION_FEE = 500.00;

    public Bill generateBillForAppointment(int appointmentId) {
        // --- THIS IS THE NEW LOGIC ---
        // 1. Check if a bill for this appointment already exists
        Optional<Bill> existingBill = billRepository.findByAppointmentId(appointmentId);
        if (existingBill.isPresent()) {
            // If it exists, just return the existing bill without creating a new one
            return existingBill.get();
        }
        // --- END OF NEW LOGIC ---

        // If no bill exists, proceed with creating a new one
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Appointment Id:" + appointmentId));

        Bill bill = new Bill();
        bill.setPatient(appointment.getPatient());
        bill.setAppointment(appointment);
        bill.setBillDate(LocalDate.now());
        bill.setStatus("Pending");
        bill.setAmount(CONSULTATION_FEE);

        return billRepository.save(bill);
    }
}