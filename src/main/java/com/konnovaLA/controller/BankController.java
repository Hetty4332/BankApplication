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
@RequestMapping("/banks")
public class BankController {

    final private BankService bankService;

    @GetMapping
    public List<Bank> getBanks() {
        List<Bank> banks = new ArrayList<>();
        banks.addAll(bankService.getBanks());
        return banks;
    }

    @PostMapping("/save")
    public String addBank(@Valid @RequestBody Bank bank) {
        bankService.saveBank(bank);
        return "Данные банка успешно сохранены";
    }

    @DeleteMapping("/{id}")
    public String deleteBank(@PathVariable Long id) {
        bankService.deleteBankById(id);
        return "Банк с id= "+id+" успешно удален";
    }

    @GetMapping("/{id}")
    public Bank getBank(@PathVariable Long id) {
        return bankService.getBankById(id);
    }
}
