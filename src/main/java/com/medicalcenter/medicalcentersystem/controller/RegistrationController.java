package com.medicalcenter.medicalcentersystem.controller;

import com.medicalcenter.medicalcentersystem.dao.PatientRepository;
import com.medicalcenter.medicalcentersystem.dao.UserRepository; // <-- This is the missing import
import com.medicalcenter.medicalcentersystem.model.Patient;
import com.medicalcenter.medicalcentersystem.model.Role;
import com.medicalcenter.medicalcentersystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public ModelAndView showRegistrationForm() {
        ModelAndView mav = new ModelAndView("register");
        mav.addObject("patient", new Patient());
        return mav;
    }

    @PostMapping("/save-patient-user")
    public String registerPatient(@ModelAttribute Patient patient, @RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
        
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRoles(Set.of(Role.ROLE_PATIENT));

        patient.setUser(newUser);
        newUser.setPatient(patient);
        
        patientRepository.save(patient);

        redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please log in.");
        return "redirect:/login";
    }
}