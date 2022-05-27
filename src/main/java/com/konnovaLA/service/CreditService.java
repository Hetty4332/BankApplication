package com.konnovaLA.service;

import com.konnovaLA.entities.request.CreditRequest;
import com.konnovaLA.exeption.ApiException;
import com.konnovaLA.mappers.CreditMapper;
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

    public List<CreditRequest> getCredits() {
        List<CreditRequest> creditWebs = new ArrayList<>();
        List<Credit> credits = creditRepository.findAll();
        credits.forEach(value -> addCreditWeb(creditWebs, value));
        return creditWebs;
    }

    private void addCreditWeb(List<CreditRequest> creditWebs, Credit value) {
        String bankName = getBankName(value);
        creditWebs.add(creditMapper.creditToDto(value, bankName));
    }

    private String getBankName(Credit value) {
        return bankRepository.findBankByCreditsContains(value)
                .map(Bank::getName)
                .orElse("банк не найден!");
    }

    public void saveCredit(CreditRequest credit) throws ApiException {
        Credit saveCredit = creditRepository.save(creditMapper.creditFromDto(credit));
        Bank bank = bankService.getBankById(credit.getIdBank());
        if (!bank.getCredits().isEmpty() && bank.getCredits() != null) {
            if (!bank.getCredits().contains(saveCredit)) {
                bank.getCredits().add(saveCredit);
                bankService.saveBank(bank);
            }
        }
    }

    public void deleteCreditById(Long id) {
        Optional<Credit> creditOptional = creditRepository.findById(id);
        if (creditOptional.isPresent()) {
            bankService.deleteCreditInBank(creditOptional.get());
            creditOfferService.deleteByCreditId(id);
            creditRepository.deleteById(id);
        }
    }

    public CreditRequest getCreditById(Long id) throws ApiException {
        Credit credit = creditRepository.findById(id).orElseThrow(() -> new ApiException("Ошибка при поиске объекта"));
        String bankName = getBankName(credit);
        return creditMapper.creditToDto(credit, bankName);
    }
}
