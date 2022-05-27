package com.konnovaLA.controller;

import com.konnovaLA.exeption.ApiException;
import com.konnovaLA.model.Bank;
import com.konnovaLA.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return bankService.getBanks();
    }

    @PostMapping("/save")
    public ResponseEntity<String> addBank(@Valid @RequestBody Bank bank) {
        bankService.saveBank(bank);
        return new ResponseEntity<>("Данные банка успешно сохранены", HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBank(@PathVariable Long id) {
        bankService.deleteBankById(id);
        return new ResponseEntity<>("Банк с id= " + id + " успешно удален", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bank> getBank(@PathVariable Long id) throws ApiException {
        return new ResponseEntity<>(bankService.getBankById(id), HttpStatus.OK);
    }
}
