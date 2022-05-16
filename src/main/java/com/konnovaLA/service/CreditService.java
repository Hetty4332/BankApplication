package com.konnovaLA.service;

import com.konnovaLA.dto.CreditWeb;
import com.konnovaLA.model.Bank;
import com.konnovaLA.model.Credit;
import com.konnovaLA.repository.BankRepository;
import com.konnovaLA.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final CreditRepository creditRepository;

    private final BankService bankService;

    private final BankRepository bankRepository;

    private final CreditOfferService creditOfferService;


    public List<CreditWeb> getCredits() {
        List<CreditWeb> creditWebs = new ArrayList<>();
        List<Credit> credits = creditRepository.findAll();
        for (Credit value : credits) {
            CreditWeb creditWeb = new CreditWeb();
            Credit credit = value;
            creditWeb.setId(credit.getId());
            creditWeb.setInterestRate(credit.getInterestRate());
            creditWeb.setCreditLimit(credit.getCreditLimit());
            String bankName = bankRepository.findBankByCreditsContains(credit)
                    .map(Bank::getName)
                    .orElse("банк не найден!");
            creditWeb.setBankName(bankName);
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
