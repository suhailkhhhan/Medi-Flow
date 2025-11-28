package com.medicalcenter.medicalcentersystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.medicalcenter.medicalcentersystem.dao.MedicineRepository;
import com.medicalcenter.medicalcentersystem.model.Medicine;

import java.util.List;

@Controller
public class PharmacyController {

    @Autowired
    private MedicineRepository medicineRepo;

    @GetMapping("/medicines-list")
    public ModelAndView showMedicinesList() {
        ModelAndView mav = new ModelAndView("medicines-list");
        List<Medicine> medicines = medicineRepo.findAll();
        mav.addObject("medicines", medicines);
        return mav;
    }

    @GetMapping("/add-medicine-form")
    public ModelAndView addMedicineForm() {
        ModelAndView mav = new ModelAndView("add-medicine-form");
        mav.addObject("medicine", new Medicine());
        return mav;
    }

    @PostMapping("/save-medicine")
    public String saveMedicine(@ModelAttribute Medicine medicine) {
        medicineRepo.save(medicine);
        return "redirect:/medicines-list";
    }
}