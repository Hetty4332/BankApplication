package com.konnovaLA.service;

import com.konnovaLA.exeption.ApiException;
import com.konnovaLA.model.Bank;
import com.konnovaLA.model.Client;
import com.konnovaLA.model.Credit;
import com.konnovaLA.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankService {

    private final BankRepository bankRepository;


    public List<Bank> getBanks() {
        return bankRepository.findAll();
    }

    public void saveBank(Bank bank) {
        bankRepository.save(bank);
    }

    public void deleteBankById(Long id) {
        bankRepository.deleteById(id);
    }

    public Bank getBankById(Long id) throws ApiException {
        return bankRepository.findById(id)
                .orElseThrow(()->new ApiException("Ошибка при поиске объекта"));
    }

    public void deleteCreditInBank(Credit credit) {
        Optional<Bank> bankOptional = bankRepository.findBankByCreditsContains(credit);
        bankOptional.ifPresent(bank1 -> {
            bank1.getCredits().remove(credit);
            bankRepository.save(bank1);
        });
    }

    public void deleteClientInBank(Client client) {
        Optional<Bank> bankOptional = bankRepository.findBankByClientsContains(client);
        bankOptional.ifPresent(bank1 -> {
            bank1.getClients().remove(client);
            bankRepository.save(bank1);
        });
    }
}
