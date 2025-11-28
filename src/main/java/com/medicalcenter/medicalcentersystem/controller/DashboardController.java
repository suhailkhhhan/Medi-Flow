package com.medicalcenter.medicalcentersystem.controller;

import com.medicalcenter.medicalcentersystem.dao.AppointmentRepository;
import com.medicalcenter.medicalcentersystem.dao.DoctorRepository;
import com.medicalcenter.medicalcentersystem.dao.PatientRepository;
import com.medicalcenter.medicalcentersystem.dao.MedicineRepository;
import com.medicalcenter.medicalcentersystem.model.Medicine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    // ================== Repositories ==================
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private MedicineRepository medicineRepository; // For pharmacy alerts

    // ================== Dashboard ==================
    @GetMapping("/dashboard")
    public ModelAndView showDashboard() {
        ModelAndView mav = new ModelAndView("dashboard");

        // --- Counts ---
        mav.addObject("doctorCount", doctorRepository.count());
        mav.addObject("patientCount", patientRepository.count());
        mav.addObject("appointmentCount", appointmentRepository.count());

        // --- Pharmacy Alerts ---
        List<Medicine> allMedicines = medicineRepository.findAll();
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysFromNow = today.plusDays(30);

        // 1. Medicines expiring soon (within 30 days)
        List<Medicine> expiringSoon = allMedicines.stream()
                .filter(medicine -> medicine.getExpiryDate() != null
                        && medicine.getExpiryDate().isAfter(today)
                        && medicine.getExpiryDate().isBefore(thirtyDaysFromNow))
                .collect(Collectors.toList());

        // 2. Medicines with low stock (<= 10)
        long lowStockCount = allMedicines.stream()
                .filter(medicine -> medicine.getQuantity() <= 10)
                .count();

        // Add to model
        mav.addObject("expiringSoonMedicines", expiringSoon);
        mav.addObject("lowStockCount", lowStockCount);

        return mav;
    }
}
