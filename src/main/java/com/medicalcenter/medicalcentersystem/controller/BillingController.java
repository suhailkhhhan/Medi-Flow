package com.medicalcenter.medicalcentersystem.controller;

import com.medicalcenter.medicalcentersystem.dao.BillRepository;
import com.medicalcenter.medicalcentersystem.model.Bill;
import com.medicalcenter.medicalcentersystem.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BillingController {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillingService billingService;

    @GetMapping("/bills-list")
    public ModelAndView showBillsList() {
        ModelAndView mav = new ModelAndView("bills-list");
        List<Bill> bills = billRepository.findAll();
        mav.addObject("bills", bills);
        return mav;
    }

    @GetMapping("/generate-bill/{appointmentId}")
    public String generateBill(@PathVariable int appointmentId) {
        billingService.generateBillForAppointment(appointmentId);
        return "redirect:/bills-list";
    }
}