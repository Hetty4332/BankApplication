package com.konnovaLA.controller;

import com.konnovaLA.entities.CreditDto;
import com.konnovaLA.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/credits")
public class CreditController {

    private final CreditService creditService;


    @GetMapping
    public List<CreditDto> getCredits() {
        return creditService.getCredits();
    }

    @PostMapping("/save")
    public String addCredit(@Valid CreditDto credit) {
        creditService.saveCredit(credit);
        return "Данные о кредите успешно сохранены";

    }

    @DeleteMapping("/{id}")
    public String deleteCredit(@PathVariable Long id) {
        creditService.deleteCreditById(id);
        return "Кредит с id= +" + id + " успешно удален";
    }

    @GetMapping("/{id}")
    public CreditDto getCredit(@PathVariable Long id) {
        return creditService.getCreditById(id);
    }

}

