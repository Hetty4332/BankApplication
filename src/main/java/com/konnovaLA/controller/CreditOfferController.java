package com.konnovaLA.controller;

import com.konnovaLA.dto.CreditOfferWeb;
import com.konnovaLA.dto.CreditWeb;
import com.konnovaLA.exeption.NoEntityException;
import com.konnovaLA.model.CreditOffer;
import com.konnovaLA.model.Payment;
import com.konnovaLA.service.BankService;
import com.konnovaLA.service.ClientService;
import com.konnovaLA.service.CreditOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CreditOfferController {

    private final CreditOfferService creditOfferService;

    private final ClientService clientService;

    private final BankService bankService;


    @GetMapping("/getPaymentsByCreditOffer")
    public List<Payment> getPayments (@PathVariable("id") Long id) {
      return creditOfferService.getPaymentsByCreditOfferId(id);
    }

    @GetMapping("/creditOffer")
    public String getCreditOffer() {
        return "";
    }

    @PostMapping("/creditOffer")
    public String addCreditOffer(@RequestBody @Valid CreditOfferWeb creditOffer, BindingResult bindingResult) {

/*        if (bindingResult.hasErrors()) {
            model.addAttribute("clients", clientService.getClients());
            model.addAttribute("banks", bankService.getBanks());
            return "creditOffer";
        } else {
            creditOfferService.validate(creditOffer, bindingResult);
            if (bindingResult.hasErrors()) {
                model.addAttribute("clients", clientService.getClients());
                model.addAttribute("banks", bankService.getBanks());
                return "creditOffer";
            }
        }*/
    /*    CreditOffer save;
        try {*/
            creditOfferService.saveCreditOffer(creditOffer);
       /* } catch (NoEntityException e) {
            bindingResult.addError(new FieldError("creditOffer", e.getEntityName() + "Id", "некорректное значение поля"));
            model.addAttribute("clients", clientService.getClients());
            model.addAttribute("banks", bankService.getBanks());
            return "creditOffer";

        }*/

        return "redirect:/payments/" + save.getId();
    }

/*    @PostMapping("/creditOffers")
    public String addCreditOffers(Model model, @PathVariable("id") Long id) {
        model.addAttribute("creditOffers", creditOfferService.getCreditOffers());
        model.addAttribute("payments", creditOfferService.getPaymentsByCreditOfferId(id));
        return "/creditOffers";
    }*/
}
