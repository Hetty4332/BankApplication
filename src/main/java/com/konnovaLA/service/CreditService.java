package com.konnovaLA.service;

import com.konnovaLA.entities.CreditDto;
import com.konnovaLA.mappers.mapperInterface.CreditMapper;
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

    private final CreditOfferService creditOfferService;

    private final CreditMapper creditMapper;

    private final BankRepository bankRepository;



    public List<CreditDto> getCredits() {
        List<CreditDto> creditWebs = new ArrayList<>();
        List<Credit> credits = creditRepository.findAll();
        for (Credit value : credits) {
           String bankName= bankRepository.findBankByCreditsContains(value)
                    .map(Bank::getName)
                    .orElse("банк не найден!");
            creditWebs.add(creditMapper.creditToDto(value,bankName));
        }
        return creditWebs;
    }

    public void saveCredit(CreditDto credit) {
        Credit saveCredit = creditRepository.save(creditMapper.creditFromDto(credit));
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

    public CreditDto getCreditById(Long id) {
        Credit credit = creditRepository.findById(id).orElse(new Credit());
        String bankName= bankRepository.findBankByCreditsContains(credit)
                .map(Bank::getName)
                .orElse("банк не найден!");
        return creditMapper.creditToDto(credit,bankName);
    }
}
