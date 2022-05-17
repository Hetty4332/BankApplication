package com.konnovaLA.controller;

import com.konnovaLA.dto.CreditDtoRequest;
import com.konnovaLA.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/credits")
public class CreditController {

    private final CreditService creditService;


    @GetMapping
    public List<CreditDtoRequest> getCredits() {
        return creditService.getCredits();
    }

    @PostMapping("/save")
    public String addCredit(@Valid CreditDtoRequest credit) {
        creditService.saveCredit(credit);
        return "Данные о кредите успешно сохранены";

    }

    @DeleteMapping("/{id}")
    public String deleteCredit(@PathVariable Long id) {
        creditService.deleteCreditById(id);
        return "Кредит с id= +" + id + " успешно удален";
    }

    @GetMapping("/{id}")
    public CreditDtoRequest getCredit(@PathVariable Long id, Model model) {
        return creditService.getCreditById(id);
    }

}

