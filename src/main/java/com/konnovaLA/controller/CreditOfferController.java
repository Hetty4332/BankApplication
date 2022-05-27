package com.konnovaLA.controller;

import com.konnovaLA.entities.request.CreditOfferRequest;
import com.konnovaLA.exeption.ApiException;
import com.konnovaLA.model.Payment;
import com.konnovaLA.service.CreditOfferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        //TODO: доделать метод
        return "";
    }

    @PostMapping("/saveCreditOffer")
    public ResponseEntity<String> addCreditOffer(@RequestBody @Valid CreditOfferRequest creditOffer) throws ApiException {

        String message;
        if (!creditOfferService.validate(creditOffer).isEmpty()) {
            message = creditOfferService.validate(creditOffer);
        } else {
            try {
                creditOfferService.saveCreditOffer(creditOffer);
                message = "Кредитное предложение успешно сохранено";

            } catch (Exception e) {
                throw new ApiException("Ошибка при сохранении объекта");
            }
        }

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
