package com.medicalcenter.medicalcentersystem.controller;

import com.medicalcenter.medicalcentersystem.dao.AppointmentRepository;
import com.medicalcenter.medicalcentersystem.dao.DoctorRepository;
import com.medicalcenter.medicalcentersystem.dao.PatientRepository;
import com.medicalcenter.medicalcentersystem.model.Appointment;
import com.medicalcenter.medicalcentersystem.model.Doctor;
import com.medicalcenter.medicalcentersystem.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepo;
    @Autowired
    private DoctorRepository doctorRepo;
    @Autowired
    private PatientRepository patientRepo;

    @GetMapping("/appointments-list")
    public ModelAndView showAppointments() {
        ModelAndView mav = new ModelAndView("appointments-list");
        List<Appointment> appointments = appointmentRepo.findAllWithPatientAndDoctor();
        mav.addObject("appointments", appointments);
        return mav;
    }

    @GetMapping("/add-appointment-form")
    public ModelAndView addAppointmentForm() {
        ModelAndView mav = new ModelAndView("add-appointment-form");
        List<Patient> patients = patientRepo.findAll();
        List<Doctor> doctors = doctorRepo.findAll();
        mav.addObject("appointment", new Appointment());
        mav.addObject("patients", patients);
        mav.addObject("doctors", doctors);
        return mav;
    }

    // *** THIS IS THE UPDATED METHOD ***
    @PostMapping("/save-appointment")
    public String saveAppointment(@ModelAttribute Appointment appointment,
                                  @RequestParam("patientId") Integer patientId,
                                  @RequestParam("doctorId") Integer doctorId) {

        // 1. Fetch the full Patient object from the database using the ID from the form
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Patient ID:" + patientId));

        // 2. Fetch the full Doctor object from the database using the ID from the form
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Doctor ID:" + doctorId));

        // 3. Set the complete objects on the appointment
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        // 4. Now, save the appointment with the correct associations
        appointmentRepo.save(appointment);

        return "redirect:/appointments-list";
    }

    @GetMapping("/delete-appointment/{id}")
    public String deleteAppointment(@PathVariable("id") int id) {
        appointmentRepo.deleteById(id);
        return "redirect:/appointments-list";
    }
}