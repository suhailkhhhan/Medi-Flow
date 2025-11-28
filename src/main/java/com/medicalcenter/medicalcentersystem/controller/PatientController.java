package com.medicalcenter.medicalcentersystem.controller;

import com.medicalcenter.medicalcentersystem.dao.PatientRepository;
import com.medicalcenter.medicalcentersystem.model.Patient;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PatientController {

    @Autowired
    private PatientRepository patientRepo;

    @GetMapping("/patients-list")
    public ModelAndView showPatientsList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page) {

        ModelAndView mav = new ModelAndView("patients-list"); // Direct template
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Patient> patientPage;
        if (keyword != null && !keyword.isEmpty()) {
            patientPage = patientRepo.findByNameContainingIgnoreCase(keyword, pageable);
        } else {
            patientPage = patientRepo.findAll(pageable);
        }

        mav.addObject("patientPage", patientPage);
        mav.addObject("keyword", keyword);
        return mav;
    }

    @GetMapping("/add-patient-form")
    public ModelAndView addPatientForm() {
        ModelAndView mav = new ModelAndView("add-patient-form"); // Direct template
        mav.addObject("patient", new Patient());
        return mav;
    }

    @PostMapping("/save-patient")
    public ModelAndView savePatient(@Valid @ModelAttribute Patient patient, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("add-patient-form");
            mav.addObject("patient", patient);
            return mav;
        }
        patientRepo.save(patient);
        redirectAttributes.addFlashAttribute("successMessage", "Patient saved successfully.");
        return new ModelAndView("redirect:/patients-list");
    }

    @GetMapping("/edit-patient/{id}")
    public ModelAndView showEditPatientForm(@PathVariable("id") int id) {
        Patient patient = patientRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        ModelAndView mav = new ModelAndView("edit-patient-form"); // Direct template
        mav.addObject("patient", patient);
        return mav;
    }

    @PostMapping("/update-patient/{id}")
    public ModelAndView updatePatient(@PathVariable("id") int id, @Valid @ModelAttribute Patient patient, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("edit-patient-form");
            mav.addObject("patient", patient);
            return mav;
        }
        patient.setId(id);
        patientRepo.save(patient);
        redirectAttributes.addFlashAttribute("successMessage", "Patient updated successfully.");
        return new ModelAndView("redirect:/patients-list");
    }

    @GetMapping("/delete-patient/{id}")
    public ModelAndView deletePatient(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        patientRepo.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Patient deleted successfully.");
        return new ModelAndView("redirect:/patients-list");
    }

    @GetMapping("/patient-details/{id}")
    public ModelAndView showPatientDetails(@PathVariable("id") int id) {
        Patient patient = patientRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        ModelAndView mav = new ModelAndView("patient-details"); // Direct template
        mav.addObject("patient", patient);
        return mav;
    }
}
