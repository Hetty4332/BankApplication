package com.konnovaLA.controller;

import com.konnovaLA.entities.request.CreditRequest;
import com.konnovaLA.exeption.ApiException;
import com.konnovaLA.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/credits")
public class CreditController {

    private final CreditService creditService;


    @GetMapping
    public List<CreditRequest> getCredits() {
        return creditService.getCredits();
    }

    @PostMapping("/save")
    public ResponseEntity<String> addCredit(@Valid CreditRequest credit) throws ApiException {
        creditService.saveCredit(credit);
        return new ResponseEntity<>("Данные о кредите успешно сохранены", HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCredit(@PathVariable Long id) {
        creditService.deleteCreditById(id);
        return new ResponseEntity<>("Кредит с id= +" + id + " успешно удален",HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public CreditRequest getCredit(@PathVariable Long id) throws ApiException {
        return creditService.getCreditById(id);
    }

}

