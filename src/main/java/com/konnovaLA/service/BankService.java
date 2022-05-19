package com.konnovaLA.service;

import com.konnovaLA.model.Bank;
import com.konnovaLA.model.Client;
import com.konnovaLA.model.Credit;
import com.konnovaLA.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Bank getBankById(Long id) {
        return bankRepository.findById(id).orElse(new Bank());
    }


    public void deleteCreditInBank(Credit credit) {
        Optional<Bank> bankOptional = bankRepository.findBankByCreditsContains(credit);
        if (bankOptional.isEmpty()) {
            return;
        }
        Bank bank = bankOptional.get();
        bank.getCredits().remove(credit);
        bankRepository.save(bank);
    }

    public void deleteClientInBank(Client client) {
        Optional<Bank> bankOptional = bankRepository.findBankByClientsContains(client);
        if (bankOptional.isEmpty()) {
            return;
        }
        Bank bank = bankOptional.get();
        bank.getClients().remove(client);
        bankRepository.save(bank);
    }
}
