package com.konnovaLA.controller;

import com.konnovaLA.dto.CreditOfferWeb;
import com.konnovaLA.exeption.NoEntityException;
import com.konnovaLA.model.CreditOffer;
import com.konnovaLA.model.Payment;
import com.konnovaLA.service.BankService;
import com.konnovaLA.service.ClientService;
import com.konnovaLA.service.CreditOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class CreditOfferController {

    private CreditOfferService creditOfferService;

    private ClientService clientService;

    private BankService bankService;

    @Autowired
    public void setCreditOfferService(CreditOfferService creditOfferService) {
        this.creditOfferService = creditOfferService;
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }


    @Autowired
    public void setBankService(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/payments/{id}")
    public String getPayments(Model model, @PathVariable("id") Long id) {
        model.addAttribute("payments", creditOfferService.getPaymentsByCreditOfferId(id));
        return "payments";
    }

    @GetMapping("/creditOffer")
    public String getCreditOffer(Model model) {
        model.addAttribute("creditOffer", new CreditOfferWeb());
        model.addAttribute("payment", new Payment());
        model.addAttribute("clients", clientService.getClients());
        model.addAttribute("banks", bankService.getBanks());
        return "creditOffer";
    }

    @PostMapping("/creditOffer")
    public String addCreditOffer(Model model, @ModelAttribute("creditOffer") @Valid CreditOfferWeb creditOffer, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
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
        }
        CreditOffer save;
        try {
            save = creditOfferService.saveCreditOffer(creditOffer);
        } catch (NoEntityException e) {
            bindingResult.addError(new FieldError("creditOffer", e.getEntityName() + "Id", "некорректное значение поля"));
            model.addAttribute("clients", clientService.getClients());
            model.addAttribute("banks", bankService.getBanks());
            return "creditOffer";

        }

        return "redirect:/payments/" + save.getId();
    }

/*    @PostMapping("/creditOffers")
    public String addCreditOffers(Model model, @PathVariable("id") Long id) {
        model.addAttribute("creditOffers", creditOfferService.getCreditOffers());
        model.addAttribute("payments", creditOfferService.getPaymentsByCreditOfferId(id));
        return "/creditOffers";
    }*/
}
