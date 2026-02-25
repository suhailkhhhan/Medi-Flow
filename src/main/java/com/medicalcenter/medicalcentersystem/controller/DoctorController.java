package com.medicalcenter.medicalcentersystem.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.medicalcenter.medicalcentersystem.dao.DoctorRepository;
import com.medicalcenter.medicalcentersystem.model.Doctor;

import java.util.List;

@Controller
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepo;

    @GetMapping("/doctors-list")
    public ModelAndView showDoctorsList() {
        ModelAndView mav = new ModelAndView("doctors-list");
        List<Doctor> doctors = doctorRepo.findAll();
        mav.addObject("doctors", doctors);
        return mav;
    }

    @GetMapping("/add-doctor-form")
    public ModelAndView addDoctorForm() {
        ModelAndView mav = new ModelAndView("add-doctor-form");
        mav.addObject("doctor", new Doctor());
        return mav;
    }

    @PostMapping("/save-doctor")
    public String saveDoctor(@ModelAttribute Doctor doctor) {
        doctorRepo.save(doctor);
        return "redirect:/doctors-list";
    }


    @GetMapping("/edit-doctor/{id}")
public ModelAndView showEditDoctorForm(@PathVariable("id") int id) {
    ModelAndView mav = new ModelAndView("edit-doctor-form");
    // Find the doctor by ID from the repository
    Doctor doctor = doctorRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid doctor Id:" + id));
    mav.addObject("doctor", doctor);
    return mav;
}

@PostMapping("/update-doctor/{id}")
public String updateDoctor(@PathVariable("id") int id, @ModelAttribute Doctor doctor) {
    // Ensure the ID from the path is set in the doctor object before saving
    doctor.setId(id);
    doctorRepo.save(doctor);
    return "redirect:/doctors-list";
}

@GetMapping("/delete-doctor/{id}")
public String deleteDoctor(@PathVariable("id") int id) {
    doctorRepo.deleteById(id);
    return "redirect:/doctors-list";
}
}