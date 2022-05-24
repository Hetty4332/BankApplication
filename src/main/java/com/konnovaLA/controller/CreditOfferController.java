package com.konnovaLA.controller;

import com.konnovaLA.entities.CreditOfferDto;
import com.konnovaLA.exeption.NoEntityException;
import com.konnovaLA.model.Payment;
import com.konnovaLA.service.CreditOfferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class CreditOfferController {

    private final CreditOfferService creditOfferService;


    @GetMapping("/getPaymentsByCreditOffer/{id}")
    public List<Payment> getPayments(@PathVariable("id") Long id) {
        return creditOfferService.getPaymentsByCreditOfferId(id);
    }

    @GetMapping("/creditOffer")
    public String getCreditOffer() {

/*        model.addAttribute("creditOffer", new CreditOfferRequest());
        model.addAttribute("payment", new Payment());
        model.addAttribute("clients", clientService.getClients());
        model.addAttribute("banks", bankService.getBanks());*/
        return "";
    }

    @PostMapping("/saveCreditOffer")
    public String addCreditOffer(@RequestBody @Valid CreditOfferDto creditOffer, BindingResult bindingResult) throws NoEntityException {

        if (bindingResult.hasErrors()) {
            return "Некорректные данные";
        } else {
            creditOfferService.validate(creditOffer, bindingResult);

        }try{
            creditOfferService.saveCreditOffer(creditOffer);}
        catch (Exception e)
        {
            throw new NoEntityException("Credit Offer");
        }

        return "Кредитное предложение успешно сохранено";
    }

}
