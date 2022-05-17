package com.konnovaLA.service;

import com.konnovaLA.dto.CreditDtoRequest;
import com.konnovaLA.model.Bank;
import com.konnovaLA.model.Credit;
import com.konnovaLA.repository.BankRepository;
import com.konnovaLA.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
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


    public List<CreditDtoRequest> getCredits() {
        List<CreditDtoRequest> creditWebs = new ArrayList<>();
        List<Credit> credits = creditRepository.findAll();
        for (Credit value : credits) {
            CreditDtoRequest creditWeb = new CreditDtoRequest();
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

    public void saveCredit(CreditDtoRequest credit) {
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

    public CreditDtoRequest getCreditById(Long id) {
        Credit credit = creditRepository.findById(id).orElse(new Credit());
        CreditDtoRequest creditWeb = new CreditDtoRequest();
        creditWeb.setId(credit.getId());
        creditWeb.setCreditLimit(credit.getCreditLimit());
        creditWeb.setInterestRate(credit.getInterestRate());
        return creditWeb;
    }
}
