package com.konnovaLA.controller;

import com.konnovaLA.dto.CreditWeb;
import com.konnovaLA.service.BankService;
import com.konnovaLA.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class CreditController {

    private CreditService creditService;

    private BankService bankService;
    @Autowired
    public void setCreditService(CreditService creditService) {
        this.creditService = creditService;
    }

    @Autowired
    public void setBankService(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/credits")
    public String getCredits(Model model) {

        model.addAttribute("credits", creditService.getCredits());
        return "credits";
    }

    @PostMapping("/editCredit")
    public String addCredit(@ModelAttribute("credit") @Valid CreditWeb credit, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/editCredit";
        }
        creditService.saveCredit(credit);
        return "redirect:/credits";
    }

    @GetMapping("/deleteCredit/{id}")
    public String deleteCredit(@PathVariable Long id) {
        creditService.deleteCreditById(id);
        return "redirect:/credits";
    }

    @GetMapping("/editCredit/{id}")
    public String getCredit(@PathVariable Long id, Model model) {
        model.addAttribute("banks", bankService.getBanks());
        model.addAttribute("credit", creditService.getCreditById(id));
        return "editCredit";
    }

    @GetMapping("/editCredit")
    public String addCredit(Model model) {
        model.addAttribute("banks", bankService.getBanks());
        model.addAttribute("credit", new CreditWeb());
        return "editCredit";
    }

}

