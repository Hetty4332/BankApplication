package com.haulmont.testtask.service;

import com.haulmont.testtask.dto.CreditWeb;
import com.haulmont.testtask.model.Bank;
import com.haulmont.testtask.model.Credit;
import com.haulmont.testtask.repository.BankRepository;
import com.haulmont.testtask.repository.CreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CreditService {
    @Autowired
    CreditRepository creditRepository;
    @Autowired
    BankService bankService;
    @Autowired
    BankRepository bankRepository;
    @Autowired
    CreditOfferService creditOfferService;

    public List<CreditWeb> getCredits() {
        List<CreditWeb> creditWebs = new ArrayList<>();
        List<Credit> credits = creditRepository.findAll();
        for (int i = 0; i < credits.size(); i++) {
            CreditWeb creditWeb = new CreditWeb();
            Credit credit = credits.get(i);
            creditWeb.setId(credit.getId());
            creditWeb.setInterestRate(credit.getInterestRate());
            creditWeb.setCreditLimit(credit.getCreditLimit());
            Optional<Bank> bankOptional = bankRepository.findBankByCreditsContains(credit);
            creditWeb.setBankName(bankOptional.get().getName());
            creditWebs.add(creditWeb);
        }
        return creditWebs;
    }

    public void saveCredit(CreditWeb credit) {
        Credit saveCredit = new Credit();
        saveCredit.setId(credit.getId());
        saveCredit.setCreditLimit(credit.getCreditLimit());
        saveCredit.setInterestRate(credit.getInterestRate());
        saveCredit = creditRepository.save(saveCredit);
        Bank bank = bankService.getBankById(credit.getIdBank());
        if (!bank.getCredits().contains(saveCredit)) {
            bank.getCredits().add(saveCredit);
            bankService.saveBank(bank);
        }
    }

    public void deleteCreditById(Long id) {
        Optional<Credit> creditOptional = creditRepository.findById(id);
        if (creditOptional.isEmpty()) {
            return;
        }
        bankService.deleteCreditInBank(creditOptional.get());
        creditOfferService.deleteByCreditId(id);
        creditRepository.deleteById(id);
    }

    public CreditWeb getCreditById(Long id) {
        Credit credit = creditRepository.findById(id).orElse(new Credit());
        CreditWeb creditWeb = new CreditWeb();
        creditWeb.setId(credit.getId());
        creditWeb.setCreditLimit(credit.getCreditLimit());
        creditWeb.setInterestRate(credit.getInterestRate());
        return creditWeb;
    }
}
