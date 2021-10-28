package com.haulmont.testtask.controller;

import com.haulmont.testtask.dto.CreditOfferRequest;
import com.haulmont.testtask.model.Credit;
import com.haulmont.testtask.model.CreditOffer;
import com.haulmont.testtask.model.Payment;
import com.haulmont.testtask.repository.CreditRepository;
import com.haulmont.testtask.service.BankService;
import com.haulmont.testtask.service.ClientService;
import com.haulmont.testtask.service.CreditOfferService;
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

    private CreditRepository creditService;

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
    public void setCreditService(CreditRepository creditService) {
        this.creditService = creditService;
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
        model.addAttribute("creditOffer", new CreditOfferRequest());
        model.addAttribute("payment", new Payment());
        model.addAttribute("clients", clientService.getClients());
        model.addAttribute("banks", bankService.getBanks());
        return "creditOffer";
    }

    @PostMapping("/creditOffer")
    public String addCreditOffer(Model model, @ModelAttribute("creditOffer") @Valid CreditOfferRequest creditOffer, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("clients", clientService.getClients());
            model.addAttribute("banks", bankService.getBanks());
            return "creditOffer";
        }
        else {
            validate(creditOffer, bindingResult);
            if (bindingResult.hasErrors())
            {
                model.addAttribute("clients", clientService.getClients());
                model.addAttribute("banks", bankService.getBanks());
                return "creditOffer";
            }
        }
        CreditOffer save = creditOfferService.saveCreditOffer(creditOffer);
        return "redirect:/payments/" + save.getId();
    }

    private void validate(CreditOfferRequest creditOffer, BindingResult bindingResult) {
        Credit credit = creditService.getById(creditOffer.getCreditId());
        if (creditOffer.getSumCredit() > credit.getCreditLimit()) {
            bindingResult.addError(new FieldError("creditOffer", "sumCredit", "требуемая сумма выше предлагаемого кредитного лимита"));
        }
    }
}
