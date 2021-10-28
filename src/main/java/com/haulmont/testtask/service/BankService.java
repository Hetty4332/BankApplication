package com.haulmont.testtask.service;

import com.haulmont.testtask.model.Bank;
import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.model.Credit;
import com.haulmont.testtask.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankService {

   private BankRepository bankRepository;

    @Autowired
    public void setBankRepository(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

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
        if (bankOptional.isEmpty()){
            return;
        }
        Bank bank = bankOptional.get();
        bank.getCredits().remove(credit);
        bankRepository.save(bank);
    }

    public void deleteClientInBank(Client client) {
        Optional<Bank> bankOptional = bankRepository.findBankByClientsContains(client);
        if (bankOptional.isEmpty()){
            return;
        }
        Bank bank = bankOptional.get();
        bank.getClients().remove(client);
        bankRepository.save(bank);
    }
}
