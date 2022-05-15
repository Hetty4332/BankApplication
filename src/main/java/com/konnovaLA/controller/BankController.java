package com.konnovaLA.controller;

import com.konnovaLA.model.Bank;
import com.konnovaLA.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BankController {

    final private BankService bankService;

    @GetMapping("/banks")
    public List<Bank> getBanks(Model model) {
        List<Bank> banks = new ArrayList<>();
        banks.addAll(bankService.getBanks());
        model.addAttribute("banks", banks);
        model.addAttribute("bank", new Bank());
        return banks;
    }

    @PostMapping("/edit")
    public String addBank(@ModelAttribute("bank") @Valid Bank bank, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/editBank";
        }
        bankService.saveBank(bank);
        return "redirect:/banks";
    }

    @PostMapping("/editBank")
    public String addBank (Model model) {
        model.addAttribute("bank", new Bank());
        return "editBank";
    }

    @PostMapping("/deleteBank/{id}")
    public String deleteBank (@PathVariable Long id) {
        bankService.deleteBankById(id);
        return "redirect:/banks";
    }

    @GetMapping("/editBank/{id}")
    public String getBank (@PathVariable Long id, Model model) {
        model.addAttribute("bank", bankService.getBankById(id));
        return "editBank";
    }
}
